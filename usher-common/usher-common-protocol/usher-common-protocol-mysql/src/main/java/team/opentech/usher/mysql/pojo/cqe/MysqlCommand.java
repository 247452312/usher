package team.opentech.usher.mysql.pojo.cqe;

import java.util.List;
import team.opentech.usher.mysql.enums.MysqlCommandTypeEnum;
import team.opentech.usher.mysql.pojo.response.MysqlResponse;

/**
 * mysql客户端的请求
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年11月03日 09时38分
 */
public interface MysqlCommand {


    /**
     * 执行
     *
     * @return 执行后的返回
     */
    List<MysqlResponse> invoke() throws Exception;


    /**
     * 类型
     *
     * @return
     */
    MysqlCommandTypeEnum type();

}
