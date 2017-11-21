package org.ps.platform.core.zookeeper;

import com.alibaba.fastjson.JSON;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import org.apache.zookeeper.data.Stat;
import org.ps.platform.core.Constant;
import org.ps.platform.core.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * zookeeper操作类
 */
@Component
public class ZookeeperHandler {

    @Autowired
    private ZookeeperRegistryCenter registryCenter;

    private String runningPath = "/" + Constant.NODE_TASK + "/" + Constant.NODE_TASK_RUNNING;

    private String waitingPath = "/" + Constant.NODE_TASK + "/" + Constant.NODE_TASK_WAITING;

    private String downPath = "/" + Constant.NODE_TASK + "/" + Constant.NODE_TASK_DOWN;

    /**
     * 初始化Task节点
     */
    public void initTaskTree() {
        this.createIfNotExist(runningPath);
        this.createIfNotExist(waitingPath);
        this.createIfNotExist(downPath);
    }

    /**
     * 从TASK节点下获取执行的任务
     *
     * @return
     */
    public Task getRunningTask() {
        try {
            List<String> nodes = this.registryCenter.getClient().getChildren().forPath(runningPath);
            if (nodes != null && nodes.size() > 0) {
                byte[] bytes = this.registryCenter.getClient().getData().forPath(runningPath + "/" + nodes.get(0));
                String string = new String(bytes);
                return JSON.parseObject(string, Task.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    /**
     * 根据优先级从等待的任务中获取一个待执行的任务
     */
    public Task getOneWaitingTask() {
        Task task = null;
        try {
            List<String> waitChildren = this.registryCenter.getClient().getChildren().forPath(waitingPath);
            if (waitChildren != null && waitChildren.size() > 0) {
                List<Task> tasks = new ArrayList<>();
                for (String child : waitChildren) {
                    byte[] bytes = this.registryCenter.getClient().getData().forPath(waitingPath + "/" + child);
                    String string = new String(bytes);
                    Task temp = JSON.parseObject(string, Task.class);
                    tasks.add(temp);
                }
                Collections.sort(tasks);
                task = tasks.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return task;
    }

    /**
     * 设置指定Task为Running状态
     *
     * @param task
     */
    public void setWaitingTask2Running(Task task) {
        try {
            this.registryCenter.getClient().inTransaction()
                    .delete().forPath(waitingPath + "/" + task.getId())
                    .and()
                    .create().forPath(runningPath + "/" + task.getId(), JSON.toJSONString(task).getBytes())
                    .and()
                    .commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置任务状态为完成
     *
     * @param task
     */
    public void setRunnintTask2Down(Task task) {
        this.moveTask(runningPath,downPath,task);
    }

    /**
     * 设置等待任务为完成
     *
     * @param task
     */
    public void setWaitingTask2Down(Task task) {
        this.moveTask(waitingPath,downPath,task);
    }


    /**
     * 更新运行中的任务
     * @param task
     */
    public void updateRuningTask(Task task){
        try {
            this.registryCenter.getClient().setData().forPath(runningPath + "/" + task.getId(),JSON.toJSONString(task).getBytes());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     *
     * @param fromPath
     * @param toPath
     * @param task
     */
    private void moveTask(String fromPath, String toPath, Task task) {
        try {
            Stat stat = this.registryCenter.getClient().checkExists().forPath(fromPath + "/" + task.getId());
            if (stat != null) {
                this.registryCenter.getClient().inTransaction()
                        .delete().forPath(fromPath + "/" + task.getId())
                        .and()
                        .create().forPath(toPath + "/" + task.getId(), JSON.toJSONString(task).getBytes())
                        .and()
                        .commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 新增一个等待任务
     *
     * @param task
     */
    public void addOneWaitingTask(Task task) {
        try {
            this.registryCenter.getClient().create().forPath(waitingPath + "/" + task.getId(), JSON.toJSONString(task).getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 路径不存在的话创建
     *
     * @param path
     */
    public void createIfNotExist(String path) {
        try {
            Stat stat = registryCenter.getClient().checkExists().forPath(path);
            if (stat == null) {
                registryCenter.getClient().create().creatingParentsIfNeeded().forPath(path);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
