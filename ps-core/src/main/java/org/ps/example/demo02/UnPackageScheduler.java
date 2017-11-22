package org.ps.example.demo02;

import com.alibaba.fastjson.JSON;
import org.ps.example.demo02.domain.Param;
import org.ps.platform.config.Configuration;
import org.ps.platform.core.ShardTask;
import org.ps.platform.core.Task;
import org.ps.platform.core.annotation.IScheduler;
import org.ps.platform.core.exception.SchedulerException;
import org.ps.platform.core.handler.SchedulerHandler;
import org.ps.platform.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
@IScheduler(name = "unPackageScheduler")
public class UnPackageScheduler extends SchedulerHandler {

    @Autowired
    private Configuration configuration;

    @Resource
    private DataSource dataSource;

    /**
     * 用户自定义分片策略
     *
     * @param waitingTask
     * @return
     */
    @Override
    protected void scheduler(Task waitingTask) throws SchedulerException {
        Param param = JSON.parseObject(waitingTask.getParamString(), Param.class);
        File srcDir = new File(param.getSrcFilePath());
        long i = 1;
        List<ShardTask> shardTasks = new ArrayList<>();
        srcDir.list((File dir, String name) -> {
            if (param.isSrcFile(name)) {
                ShardTask shardTask = new ShardTask();
                shardTask.setId(StringUtils.createUUID());
                shardTask.setParentId(waitingTask.getId());
                shardTask.setParamString(dir + "/" + name);
                shardTask.setShardNumber((int) (i % this.configuration.getExecutorTotalCount()));
                shardTasks.add(shardTask);
            }
            return false;
        });
        if (shardTasks.size() > 0) {
            this.insertDatabase(shardTasks);
        }
    }

    private void insertDatabase(List<ShardTask> shardTasks) throws SchedulerException {
        try {
            Connection connection = this.dataSource.getConnection();
            PreparedStatement pstmt = connection.prepareStatement("insert into job_shard_task(id,parentId,shardNumber,paramString,createTime) values(?,?,?,?,now())");
            for (int i = 0, l = shardTasks.size(); i < l; i++) {
                ShardTask shardTask = shardTasks.get(i);
                pstmt.setString(1, shardTask.getId());
                pstmt.setString(2, shardTask.getParentId());
                pstmt.setInt(3, shardTask.getShardNumber());
                pstmt.setString(4, shardTask.getParamString());
                pstmt.addBatch();
                if (i % 500 == 0) {
                    pstmt.execute();
                    pstmt.clearBatch();
                }
            }
            pstmt.execute();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
