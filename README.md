# PS-JOB 分布式任务管理平台
基于当当的ElasticJob二次开发的分布式任务管理平台。
## [一、原理说明](about/html/summary.md)
## [二、包结构说明](https://github.com/zhoufn/ps/blob/master/about/html/structure.md)
## [三、基础概念](https://github.com/zhoufn/ps/blob/master/about/html/notion.md)
## 四、使用方式
* 自定义监控类：继承基础类MonitorHandler，同时使用@Component将其组件化，使用@IMonitor为其命名，每个Task的Monitor对应的@IMonitor的name禁止冲突。
## 五、细节说明
* Scheduler、Executor、Monitor在启动前，会检查当前zookeeper下的Task结构是否已存在，不存在的话会创建此结构。
## 六、问题列表
* 如果平台非正常终止，启动时任务怎么处理


