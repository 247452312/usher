# usher

usher is a Java project for many technologies.

## 包层次介绍
见 [目录](%E7%9B%AE%E5%BD%95.md)

## 项目简介

* 基于DDD项目架构
* [基于usher-rpc的微服务架构](usher-common/usher-common-rpc)
* [应用内事件发布框架](usher-common/usher-common-event-bus)
* [基于usher-rpc SPI的Filter熔断(Hystrix)](usher-service/usher-service-gateway/src/main/java/top/uhyils/usher/filter/HystrixFilter.java)
* [基于redis的分布式锁 + 热点自动发现技术](usher-common/usher-common-hot-spot)
* 基于rocketMQ的分布式事务
* [基于mq的JVM内存监控 + JVM调优](usher-common/usher-common-hot-spot)
* 基于docker的mysql读写分离
* [集中处理并转发请求的网关服务](usher-service/usher-service-gateway)
* [基于MongoDB的文件系统](usher-service/usher-service-mongo)
* 基于nginx的负载匀衡
* [爬虫流量隔离](usher-service/usher-service-gateway/src/main/java/top/uhyils/usher/aop/IpSpiderTableAspect.java)
* [Aop代理加token的方式实现不同用户的登录](usher-common/usher-common-service/src/main/java/top/uhyils/usher/aop/TokenInjectAop.java)
* 基于Github jenkins docker 的自动化部署
* [雪花算法生成id](usher-common/usher-common-base/src/main/java/top/uhyils/usher/util/IdUtil.java)
* java实现的[遗传算法](usher-service/usher-service-algorithm/src/main/java/top/uhyils/usher/util/genetic)
  与[神经网络](usher-service/usher-service-algorithm/src/main/java/top/uhyils/usher/util/network)
* [traceId日志链路跟踪](usher-common/usher-common-log)
* [自定义异常断言](usher-common/usher-common-base/src/main/java/top/uhyils/usher/util/Asserts.java)
* 基于mybatis-plus的orm映射
* [在线服务降级](usher-common/usher-common-service/src/main/java/top/uhyils/usher/aop/ServiceTemporarilyDisabledAop.java)
* [java调用python模块](usher-common/usher-common-base/src/main/java/top/uhyils/usher/util/PythonCellUtil.java)
* 基于docker的中间件: nacos(rpc注册中心) mysql redis MongoDB rocketMQ
* 后期加了很多很多东西,自己探索吧

## Usage(如何使用项目)

### 运行代码需要的环境
* java1.8环境
* nacos
* mysql
* mongoDB
* redis
* rocketMq
* 本地启动时需要加入 --spring.profiles.active=dev命令

#### 最小化运行:

* java
* nacos(配置中心,注册中心)
* mysql

### 依次运行下列项目
user-log-web 即可最小化运行

## Contributing(贡献)
* Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.  
* 欢迎拉取请求。对于重大更改，请先打开一个问题，讨论您想要更改的内容  
* Please make sure to update tests as appropriate.  
* 请确保根据需要更新测试  
* Now I am the only one maintaining this project. I look forward to new people joining  
* 现在此项目只有我一个人在维护,期待有新人加入  
[贡献者公约](code_of_conduct.md)  
[如何贡献](CONTRIBUTING-template.md)

目前项目主要是在 [gitee](https://gitee.com/opentech_usher/usher) 上维护.

## License
[MIT](https://choosealicense.com/licenses/mit/)  
[本项目许可说明](LICENSE)
