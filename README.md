# PS(Parallel Service)-分布式任务管理平台
基于当当的ElasticJob二次开发的分布式任务管理平台。<br>
ps-admin：用于管理任务的UI界面。<br>
ps-core：ps平台。<br>
## 基础概念
### MonitorStrategy监控策略
*用户自定义的监控策略，需要继承Monitor抽象类。
### SchedulerStrategy调度策略
* 用户自定义的分片策略，需要继承Scheduler抽象类。
### ExecutorStrategy执行策略
* 用户自定义的分片执行策略，需要继承Executor抽象类。
### Task任务
* 任务是由MonitorStrategy、SchedulerStrategy和ExecutorStrategy组成，SchedulerStrategy实现Task的切割（切割后为ShardTask），ExecutorStrategy负责切割后任务的执行，MonitorStrategy实现整个Task的监控跟踪。
### ShardTask分片任务
* 分片任务是由Task被SchedulerStrategy切割后获取到，由ExecutorStrategy负责执行。

