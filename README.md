# PS-JOB 分布式任务管理平台
基于当当的ElasticJob二次开发的分布式任务管理平台。
## 原理说明
本平台基于当当的ElasticJob开发，主要分为以下三部分：
* Scheduler：主要职责为从zookeeper节点task/waiting节点下根据Task的sort获取一个待执行的任务，将任务根据Task下的切割策略（SchedulerHandler）进行切割，
             切割后将任务由waiting节点转移到running节点下。
* Executor：主要职责为从zookeeper节点task/running节点下获取正在执行的任务，根据自身的分片Id获取分片任务（ShardTask），依据Task下的执行策略（ExecutorHandler）执行。
* Monitor：主要职责为监控zookeeper节点task/running节点下正在执行的任务，重复的调用Task下指定的监控策略（MonitorHandler）进行监控。
## 包结构说明
* ps-admin：用于管理任务的UI界面。
* ps-core：ps平台。
## 基础概念
### MonitorHandler监控策略
* 用户自定义的监控策略，需要继承MonitorHandler抽象类。
* _boolean isDown(Task runningTask)_ 判断当前执行中的任务是否完成，返回true标识完成。
* _int process(Task runningTask)_ 获取当前执行中的任务的执行进度。
* _void createReport(Task runningTask)_ 在isDown为true时被触发，生成任务报告。
### SchedulerHandler调度策略
* 用户自定义的分片策略，需要继承Scheduler抽象类。
### ExecutorHandler执行策略
* 用户自定义的分片执行策略，需要继承Executor抽象类。
### Task任务
* 任务是由MonitorHandler、SchedulerHandler和ExecutorHandler组成，SchedulerHandler实现Task的切割（切割后为ShardTask），ExecutorHandler负责切割后任务的执行，MonitorHandler实现整个Task的监控跟踪。
### ShardTask分片任务
* 分片任务是由Task被SchedulerHandler切割后获取到，由ExecutorHandler负责执行。
## 使用方式
* 自定义监控类：继承基础类MonitorHandler，同时使用@Component将其组件化，使用@IMonitor为其命名，每个Task的Monitor对应的@IMonitor的name禁止冲突。
## 细节说明
* Scheduler、Executor、Monitor在启动前，会检查当前zookeeper下的Task结构是否已存在，不存在的话会创建此结构。
## 问题列表
* 如果平台非正常终止，启动时任务怎么处理


