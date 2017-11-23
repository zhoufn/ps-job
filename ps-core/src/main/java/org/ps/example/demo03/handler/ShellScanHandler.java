package org.ps.example.demo03.handler;

import org.ps.platform.config.Configuration;
import org.ps.platform.core.Task;
import org.ps.platform.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

/**
 * linux下的实现
 */
@Component
public class ShellScanHandler extends ScanHandler {

    @Qualifier("dataSource")
    @Autowired
    private DataSource dataSource;

    @Autowired
    private Configuration configuration;

    /**
     * 获取指定路径下的指定后缀的文件
     * @param waitingTask
     * @param srcDir
     * @param suffix
     */
    @Override
    public void scan(Task waitingTask,String srcDir, String suffix) {
        srcDir = srcDir.endsWith("/") ? (srcDir + "*") : (srcDir + "/*");
        String command = "find " + srcDir + " | grep " + suffix + "$";
        List<String> commands = new ArrayList<>();
        commands.add("sh");
        commands.add("-c");
        commands.add(command);
        ProcessBuilder processBuilder = new ProcessBuilder(commands);
        try {
            Connection connection = this.dataSource.getConnection();
            PreparedStatement pstmt = connection.prepareStatement("insert into job_shard_task(id,parentId,shardNumber,paramString,createTime) values(?,?,?,?,now())");
            Process process = processBuilder.start();
            InputStream is = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(is, "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            String childPath = null;
            long i = 1;
            while ((childPath = br.readLine()) != null) {
                long shardNumber = i % this.configuration.getExecutorTotalCount();
                pstmt.setString(1, StringUtils.createUUID());
                pstmt.setString(2,waitingTask.getId());
                pstmt.setInt(3,(int)shardNumber);
                pstmt.setString(4,childPath);
                pstmt.addBatch();
                if(i % 500 == 0){
                    pstmt.executeBatch();
                }
                i++;
            }
            pstmt.executeBatch();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
