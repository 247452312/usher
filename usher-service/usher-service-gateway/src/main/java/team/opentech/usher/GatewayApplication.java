package team.opentech.usher;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import team.opentech.usher.rpc.annotation.UsherRpc;


/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年04月20日 12时05分
 */
@SpringBootApplication
@UsherRpc
@EnableTransactionManagement
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }
}
