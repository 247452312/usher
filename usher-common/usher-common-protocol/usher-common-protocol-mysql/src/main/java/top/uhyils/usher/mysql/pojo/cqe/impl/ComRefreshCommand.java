package top.uhyils.usher.mysql.pojo.cqe.impl;

import java.util.Collections;
import java.util.List;
import top.uhyils.usher.mysql.enums.MysqlCommandTypeEnum;
import top.uhyils.usher.mysql.enums.MysqlRefreshEnum;
import top.uhyils.usher.mysql.enums.SqlTypeEnum;
import top.uhyils.usher.mysql.pojo.cqe.AbstractMysqlCommand;
import top.uhyils.usher.mysql.pojo.response.MysqlResponse;
import top.uhyils.usher.mysql.pojo.response.impl.OkResponse;


/**
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年11月03日 18时42分
 */
public class ComRefreshCommand extends AbstractMysqlCommand {

    private MysqlRefreshEnum refresh;

    public ComRefreshCommand(byte[] mysqlBytes) {
        super(mysqlBytes);
    }

    @Override
    public List<MysqlResponse> invoke() {
        return Collections.singletonList(new OkResponse(SqlTypeEnum.DELETE));
    }

    @Override
    public MysqlCommandTypeEnum type() {
        return MysqlCommandTypeEnum.COM_REFRESH;
    }

    @Override
    protected void load() {
        byte mysqlByte = mysqlBytes[1];
        this.refresh = MysqlRefreshEnum.parse(mysqlByte);
    }
}
