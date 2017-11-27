package org.ps.example.demo02;

import com.alibaba.fastjson.JSON;
import com.dangdang.ddframe.job.api.ShardingContext;
import org.ps.example.demo02.domain.CompressShardTaskRepository;
import org.ps.example.demo02.domain.Param;
import org.ps.example.demo02.handler.UnCompressHandler;
import org.ps.platform.core.ShardTask;
import org.ps.platform.core.Task;
import org.ps.platform.core.annotation.IExecutor;
import org.ps.platform.core.handler.ExecutorHandler;
import org.ps.platform.core.repository.ShardTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 解压执行器
 */
@Component
@IExecutor(name = "unPackageExecutor")
public class UnPackageExecutor extends ExecutorHandler {

    @Autowired
    private CompressShardTaskRepository repository;

    @Autowired
    private UnCompressHandler compressHandler;


    /**
     * @param shardingContext 分片上下文
     * @param runningTask     运行任务
     * @param shardTask       分片任务
     */
    @Override
    public void execute(ShardingContext shardingContext, Task runningTask, ShardTask shardTask) {
        System.out.println("*************扫描到ShardTask：" + JSON.toJSONString(shardTask) + "*******************");
        //待解压文件
        String srcFilePath = shardTask.getParamString();
        Param param = JSON.parseObject(runningTask.getParamString(), Param.class);
        //解压到的路径
        String destDir = param.getDestFilePath();
        try {
            this.compressHandler.unCompress(srcFilePath,destDir);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 获取JPA接口
     *
     * @return
     */
    @Override
    protected ShardTaskRepository getShardTaskRepository() {
        return this.repository;
    }
}
