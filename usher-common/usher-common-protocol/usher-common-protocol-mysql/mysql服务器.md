# mysql服务器

## 使用方法

+ netty decode使用 [MysqlDecoder](src%2Fmain%2Fjava%2Ftop%2Fuhyils%2Fusher%2Fmysql%2Fdecode%2FMysqlDecoder.java) &
  处理协议使用 [MysqlInfoHandler](src%2Fmain%2Fjava%2Ftop%2Fuhyils%2Fusher%2Fmysql%2Fnetty%2FMysqlInfoHandler.java)
+ 实现 [MysqlServiceHandler](src%2Fmain%2Fjava%2Ftop%2Fuhyils%2Fusher%2Fmysql%2Fhandler%2FMysqlServiceHandler.java)
  并使之加入spring

## 其他功能

+ [PlanUtil](src%2Fmain%2Fjava%2Ftop%2Fuhyils%2Fusher%2Fmysql%2Futil%2FPlanUtil.java)提供了将sql语句转换为执行计划的功能
+ 自定义全局与会话变量
  需要继承[GlobalCustomExtendInfo](src%2Fmain%2Fjava%2Ftop%2Fuhyils%2Fusher%2Fmysql%2Fcontent%2Finfo%2FGlobalCustomExtendInfo.java)
  和 [SessionCustomExtendInfo](src%2Fmain%2Fjava%2Ftop%2Fuhyils%2Fusher%2Fmysql%2Fcontent%2Finfo%2FSessionCustomExtendInfo.java)
```java
@Configuration
@AutoConfigureBefore(MysqlConfig.class)
public class MysqlConfiguration {


    @Bean
    @Primary
    public GlobalCustomExtendInfo globalCustomExtendInfo() {
        return new GlobalCustomExtendInfoImpl();
    }

    @Bean
    @Primary
    public SessionCustomExtendInfo sessionCustomExtendInfo() {
        return new SessionCustomExtendInfoImpl();
    }
}
```

## 启动配置

spring配置
```yaml
mysql-netty: 3300 # 绑定端口号
```
