package team.opentech.usher.mysql.pojo.cqe;

import team.opentech.usher.context.UserInfoHelper;
import team.opentech.usher.mysql.content.MysqlContent;
import team.opentech.usher.mysql.pojo.entity.MysqlTcpLink;
import team.opentech.usher.pojo.DTO.UserDTO;
import team.opentech.usher.pojo.cqe.command.base.AbstractCommand;

/**
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年11月03日 15时03分
 */
public abstract class AbstractMysqlCommand extends AbstractCommand implements MysqlCommand {

    /**
     * 要处理的字节数组
     */
    protected byte[] mysqlBytes;

    protected AbstractMysqlCommand(byte[] mysqlBytes) {
        MysqlTcpLink mysqlTcpLink = MysqlContent.MYSQL_TCP_INFO.get();
        UserDTO userInfo = mysqlTcpLink.findUserDTO();
        if (userInfo != null) {
            String token = userInfo.findToken();
            UserInfoHelper.setToken(token);
            UserInfoHelper.setUser(userInfo);
        }
        this.mysqlBytes = mysqlBytes;
        load();
    }

    /**
     * 加载
     */
    protected abstract void load();
}
