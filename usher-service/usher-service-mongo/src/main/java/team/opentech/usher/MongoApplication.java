package team.opentech.usher;

import team.opentech.usher.rpc.annotation.UsherRpc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 注解: 取消springBoot自带的mongo自启动
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年06月08日 13时56分
 */
@SpringBootApplication(exclude = MongoAutoConfiguration.class)
@UsherRpc
@EnableTransactionManagement
public class MongoApplication {

    public static void main(String[] args) {
        SpringApplication.run(MongoApplication.class, args);
    }
}
