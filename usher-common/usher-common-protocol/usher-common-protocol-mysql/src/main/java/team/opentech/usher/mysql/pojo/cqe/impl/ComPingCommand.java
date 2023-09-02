package team.opentech.usher.mysql.pojo.cqe.impl;

import java.util.Collections;
import java.util.List;
import team.opentech.usher.mysql.enums.MysqlCommandTypeEnum;
import team.opentech.usher.mysql.enums.SqlTypeEnum;
import team.opentech.usher.mysql.pojo.cqe.AbstractMysqlCommand;
import team.opentech.usher.mysql.pojo.response.MysqlResponse;
import team.opentech.usher.mysql.pojo.response.impl.OkResponse;


/**
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年11月03日 18时42分
 */
public class ComPingCommand extends AbstractMysqlCommand {

    public ComPingCommand(byte[] mysqlBytes) {
        super(mysqlBytes);
    }

    @Override
    public List<MysqlResponse> invoke() {
        return Collections
            .singletonList(new OkResponse(SqlTypeEnum.NULL));
    }

    @Override
    public MysqlCommandTypeEnum type() {
        return MysqlCommandTypeEnum.COM_PING;
    }

    @Override
    protected void load() {
        // ping内容. 无需返回值
    }
}
