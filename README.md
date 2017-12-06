# PS-JOB 分布式任务管理平台
基于当当的ElasticJob二次开发的分布式任务管理平台。
## 一、原理说明
本平台基于当当的ElasticJob开发，主要分为以下三部分：
* Scheduler：主要职责为从zookeeper节点task/waiting节点下根据Task的sort获取一个待执行的任务，将任务根据Task下的切割策略（SchedulerHandler）进行切割，
             切割后将任务由waiting节点转移到running节点下。
* Executor：主要职责为从zookeeper节点task/running节点下获取正在执行的任务，根据自身的分片Id获取分片任务（ShardTask），依据Task下的执行策略（ExecutorHandler）执行。
* Monitor：主要职责为监控zookeeper节点task/running节点下正在执行的任务，重复的调用Task下指定的监控策略（MonitorHandler）进行监控。
## [二、包结构说明](https://github.com/zhoufn/ps/blob/master/about/html/structure.md)
## [三、基础概念](https://github.com/zhoufn/ps/blob/master/about/html/notion.md)
## 四、使用方式
* 自定义监控类：继承基础类MonitorHandler，同时使用@Component将其组件化，使用@IMonitor为其命名，每个Task的Monitor对应的@IMonitor的name禁止冲突。
## 五、细节说明
* Scheduler、Executor、Monitor在启动前，会检查当前zookeeper下的Task结构是否已存在，不存在的话会创建此结构。
## 六、问题列表
* 如果平台非正常终止，启动时任务怎么处理


