package org.ps.example.demo02;

import org.ps.platform.core.Task;
import org.ps.platform.core.annotation.IMonitor;
import org.ps.platform.core.exception.MonitorException;
import org.ps.platform.core.exception.ReportException;
import org.ps.platform.core.handler.MonitorHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Component
@IMonitor(name = "unPackageMonitor")
public class UnPackageMonitor extends MonitorHandler{

    @Qualifier("dataSource")
    @Autowired
    private DataSource dataSource;

    private Connection connection;

    private void initConnection() throws Exception{
        if(this.connection == null){
            this.connection = this.dataSource.getConnection();
        }
    }

    /**
     * 获取ShardTask总数
     *
     * @param runningTask
     * @throws MonitorException
     */
    @Override
    public int getTotalShardTaskCount(Task runningTask) throws MonitorException {
        int totalCount = 0;
        try{
            this.initConnection();
            PreparedStatement pstmt = connection.prepareStatement("select count(1) as totalCount from job_shard_task t where t.parentId=?");
            pstmt.setString(1,runningTask.getId());
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                totalCount = rs.getInt("totalCount");
                return totalCount;
            }
            rs.close();
            pstmt.close();
        }catch (Exception e){
            e.printStackTrace();
            MonitorException exception = new MonitorException();
            exception.setMessage("getTotalShardTaskCount异常。");
            throw exception;
        }
        return totalCount;
    }

    /**
     * 获取完成的ShardTask的数量
     *
     * @param runningTask
     * @throws MonitorException
     */
    @Override
    public int getDownShardTaskCount(Task runningTask) throws MonitorException {
        int count = 0;
        try{
            this.initConnection();
            PreparedStatement pstmt = connection.prepareStatement("select count(1) as counts from job_shard_task t where t.parentId=? and t.endTime is not null");
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                count = rs.getInt("totalCount");
                return count;
            }
            rs.close();
            pstmt.close();
        }catch (Exception e){
            e.printStackTrace();
            MonitorException exception = new MonitorException();
            exception.setMessage("getDownShardTaskCount异常。");
            throw exception;
        }
        return count;
    }

    /**
     * 生成任务报告
     *
     * @param runningTask
     */
    @Override
    public void createReport(Task runningTask) throws ReportException {

    }
}
