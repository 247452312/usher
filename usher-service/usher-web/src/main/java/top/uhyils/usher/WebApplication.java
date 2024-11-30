package top.uhyils.usher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import top.uhyils.usher.rpc.annotation.UsherRpc;


/**
 * 前台项目启动类
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年04月27日 16时46分
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@UsherRpc(baseScanPackage = {"top.uhyils.usher.*.*"})
public class WebApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(WebApplication.class, args);
    }
}
