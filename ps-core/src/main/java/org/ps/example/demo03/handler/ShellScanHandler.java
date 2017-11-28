package org.ps.example.demo03.handler;

import org.ps.example.demo03.domain.AnalysisShardTask;
import org.ps.platform.config.Configuration;
import org.ps.platform.core.ShardTask;
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
import java.util.Date;
import java.util.List;

/**
 * linux下的实现
 */
@Component
public class ShellScanHandler extends ScanHandler {

    @Autowired
    private Configuration configuration;

    /**
     * 获取指定路径下的指定后缀的文件
     * @param waitingTask
     * @param srcDir
     * @param suffix
     */
    @Override
    public List<ShardTask> scan(Task waitingTask, String srcDir, String suffix) {
        srcDir = srcDir.endsWith("/") ? (srcDir + "*") : (srcDir + "/*");
        String command = "find " + srcDir + " | grep " + suffix + "$";
        List<String> commands = new ArrayList<>();
        commands.add("sh");
        commands.add("-c");
        commands.add(command);
        ProcessBuilder processBuilder = new ProcessBuilder(commands);
        List<ShardTask> shardTasks = new ArrayList<>();
        try {
            Process process = processBuilder.start();
            InputStream is = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(is, "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            String childPath = null;
            long i = 0;
            while ((childPath = br.readLine()) != null) {
                long shardNumber = i % this.configuration.getExecutorTotalCount();
                AnalysisShardTask shardTask = new AnalysisShardTask();
                shardTask.setId(StringUtils.createUUID());
                shardTask.setParentId(waitingTask.getId());
                shardTask.setCreateTime(new Date());
                shardTask.setParamString(childPath);
                shardTask.setShardNumber((int)shardNumber);
                shardTasks.add(shardTask);
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return shardTasks;
    }
}
