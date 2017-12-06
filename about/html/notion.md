## 基础概念
### Task任务
* 任务是由MonitorHandler、SchedulerHandler和ExecutorHandler组成，SchedulerHandler实现Task的切割（切割后为ShardTask），ExecutorHandler负责切割后任务的执行，MonitorHandler实现整个Task的监控跟踪。
### ShardTask分片任务
* 分片任务是由Task被SchedulerHandler切割后获取到，由ExecutorHandler负责执行。
* 使用者需要根据具体的业务扩展此类，并且提供对应的JPA（需要继承自ShardTaskRepository）。
### MonitorHandler监控策略
* 用户自定义的监控策略，需要继承MonitorHandler抽象类。
* _ShardTaskRepository getShardTaskRepository()_ 当前分片任务的JPA。
* _void createReport(Task runningTask)_ 在isDown为true时被触发，生成任务报告。
### SchedulerHandler调度策略
* 用户自定义的分片策略，需要继承Scheduler抽象类。
* _ShardTaskRepository getShardTaskRepository()_ 当前分片任务的JPA。
* _List<ShardTask> scheduler(Task waitingTask)_ 切割下一个待执行的任务获取分片任务，切割后此任务会转移到running节点下。
### ExecutorHandler执行策略
* 用户自定义的分片执行策略，需要继承Executor抽象类。
* _ShardTaskRepository getShardTaskRepository()_ 当前分片任务的JPA。
* _void execute(ShardingContext shardingContext, Task runningTask, ShardTask shardTask)_ 执行分片业务逻辑。