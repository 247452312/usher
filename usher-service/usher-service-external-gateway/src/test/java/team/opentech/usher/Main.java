package team.opentech.usher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import team.opentech.usher.rpc.annotation.UsherRpc;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2022年03月30日 09时35分
 */
@SpringBootApplication
@UsherRpc
@EnableTransactionManagement
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
