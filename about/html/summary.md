## 原理说明
本平台基于当当的ElasticJob开发，主要分为以下三部分：
* Scheduler：主要职责为从zookeeper节点task/waiting节点下根据Task的sort获取一个待执行的任务，将任务根据Task下的切割策略（SchedulerHandler）进行切割，
             切割后将任务由waiting节点转移到running节点下。
* Executor：主要职责为从zookeeper节点task/running节点下获取正在执行的任务，根据自身的分片Id获取分片任务（ShardTask），依据Task下的执行策略（ExecutorHandler）执行。
* Monitor：主要职责为监控zookeeper节点task/running节点下正在执行的任务，重复的调用Task下指定的监控策略（MonitorHandler）进行监控。