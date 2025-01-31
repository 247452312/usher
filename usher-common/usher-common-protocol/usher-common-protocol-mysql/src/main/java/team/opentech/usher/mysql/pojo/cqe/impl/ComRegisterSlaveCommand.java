package team.opentech.usher.mysql.pojo.cqe.impl;


import java.util.List;
import team.opentech.usher.mysql.enums.MysqlCommandTypeEnum;
import team.opentech.usher.mysql.pojo.cqe.AbstractMysqlCommand;
import team.opentech.usher.mysql.pojo.response.MysqlResponse;

/**
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年11月03日 18时47分
 */
public class ComRegisterSlaveCommand extends AbstractMysqlCommand {

    public ComRegisterSlaveCommand(byte[] mysqlBytes) {
        super(mysqlBytes);
    }

    @Override
    public List<MysqlResponse> invoke() {
        return null;
    }

    @Override
    public MysqlCommandTypeEnum type() {
        return MysqlCommandTypeEnum.COM_REGISTER_SLAVE;
    }

    @Override
    protected void load() {

    }
}
