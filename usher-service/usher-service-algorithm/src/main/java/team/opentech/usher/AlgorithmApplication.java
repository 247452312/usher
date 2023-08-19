package team.opentech.usher;

import team.opentech.usher.rpc.annotation.UsherRpc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年06月08日 13时56分
 */


@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@UsherRpc
public class AlgorithmApplication {

    public static void main(String[] args) {
        SpringApplication.run(AlgorithmApplication.class, args);
    }
}
