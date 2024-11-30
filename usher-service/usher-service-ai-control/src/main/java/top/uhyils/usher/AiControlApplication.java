package top.uhyils.usher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import top.uhyils.usher.rpc.annotation.UsherRpc;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年06月08日 13时56分
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@UsherRpc
public class AiControlApplication {

    public static void main(String[] args) {
        SpringApplication.run(AiControlApplication.class, args);
    }
}
