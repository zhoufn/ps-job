package org.ps.example.demo02;

import com.alibaba.fastjson.JSON;
import com.dangdang.ddframe.job.api.ShardingContext;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.ps.example.demo02.domain.Param;
import org.ps.platform.core.ShardTask;
import org.ps.platform.core.Task;
import org.ps.platform.core.annotation.IExecutor;
import org.ps.platform.core.annotation.IScheduler;
import org.ps.platform.core.exception.ExecutorException;
import org.ps.platform.core.handler.ExecutorHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

/**
 * 解压执行器
 */
@Component
@IExecutor(name = "unPackageExecutor")
public class UnPackageExecutor extends ExecutorHandler {

    @Qualifier("dataSource")
    @Autowired
    private DataSource dataSource;

    private Connection connection;

    private void initConnection() throws Exception {
        if (this.connection == null) {
            this.connection = this.dataSource.getConnection();
        }
    }

    @Override
    public void execute(ShardingContext shardingContext, Task runnigTask){
        ShardTask shardTask = this.getOneWaitingShardTask(runnigTask, shardingContext.getShardingItem());
        //待解压文件
        String srcFilePath = shardTask.getParamString();
        Param param = JSON.parseObject(runnigTask.getParamString(), Param.class);
        //解压到的路径
        String destDir = param.getDestFilePath();
        try {
            this.unPackage(shardTask,srcFilePath,destDir);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 获取一个待执行的ShardTask
     *
     * @param runningTask
     * @param shardNumber
     * @return
     */
    private ShardTask getOneWaitingShardTask(Task runningTask, int shardNumber) {
        ShardTask shardTask = null;
        try {
            this.initConnection();
            PreparedStatement pstmt = connection.prepareStatement("select id,paramString,createTime from job_shard_task t where t.endTime is null and t.parentId=? and t.shardNumber=? order by t.createTime asc limit 0,1");
            pstmt.setString(1, runningTask.getId());
            pstmt.setInt(2, shardNumber);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                shardTask = new ShardTask();
                shardTask.setId(rs.getString("id"));
                shardTask.setParentId(runningTask.getId());
                shardTask.setShardNumber(shardNumber);
                shardTask.setParamString(rs.getString("paramString"));
                shardTask.setCreateTime(new Date(rs.getTimestamp("createTime").getTime()));
                break;
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return shardTask;
    }


    /**
     * 解压文件
     *
     * @param srcFilePath
     * @param destDir
     * @throws Exception
     */
    private void unPackage(ShardTask shardTask, String srcFilePath, String destDir) throws Exception {
        FileInputStream fis = new FileInputStream(new File(srcFilePath));
        ArchiveInputStream ais = null;
        if (srcFilePath.endsWith(".zip")) {
            ais = new ZipArchiveInputStream(fis, "GBK");
        } else if (srcFilePath.endsWith(".gz")) {
            ais = new TarArchiveInputStream(fis, "GBK");
        } else if (srcFilePath.endsWith(".tar")) {
            GzipCompressorInputStream zci = new GzipCompressorInputStream(fis);
            ais = new TarArchiveInputStream(zci);
        }
        ArchiveEntry entry = null;
        while ((entry = ais.getNextEntry()) != null) {
            String name = entry.getName();
            String tempDir = destDir + File.separator + shardTask.getId() + File.separator + name;
            File tempFile = new File(tempDir);
            if (!tempFile.exists()) {
                if (!tempFile.getParentFile().exists())
                    tempFile.getParentFile().mkdirs();
                tempFile.createNewFile();
            }
            OutputStream os = new FileOutputStream(tempFile);
            int b ;
            byte[] bytes = new byte[1024];
            while((b=ais.read(bytes)) != -1){
                os.write(bytes,0,b);
            }
            os.flush();
            os.close();
        }
        ais.close();
        fis.close();
    }
}
