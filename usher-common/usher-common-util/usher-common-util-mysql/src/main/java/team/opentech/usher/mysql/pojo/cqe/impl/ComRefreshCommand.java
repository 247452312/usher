package team.opentech.usher.mysql.pojo.cqe.impl;

import team.opentech.usher.mysql.enums.MysqlCommandTypeEnum;
import team.opentech.usher.mysql.enums.MysqlRefreshEnum;
import team.opentech.usher.mysql.enums.SqlTypeEnum;
import team.opentech.usher.mysql.handler.MysqlThisRequestInfo;
import team.opentech.usher.mysql.pojo.cqe.AbstractMysqlCommand;
import team.opentech.usher.mysql.pojo.response.MysqlResponse;
import team.opentech.usher.mysql.pojo.response.impl.OkResponse;
import java.util.Arrays;
import java.util.List;


/**
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年11月03日 18时42分
 */
public class ComRefreshCommand extends AbstractMysqlCommand {

    private MysqlRefreshEnum refresh;

    public ComRefreshCommand(MysqlThisRequestInfo mysqlThisRequestInfo) {
        super(mysqlThisRequestInfo);
    }

    @Override
    public List<MysqlResponse> invoke() {
        return Arrays.asList(new OkResponse(SqlTypeEnum.DELETE));
    }

    @Override
    public MysqlCommandTypeEnum type() {
        return MysqlCommandTypeEnum.COM_REFRESH;
    }

    @Override
    protected void load() {
        byte[] mysqlBytes = mysqlThisRequestInfo.getMysqlBytes();
        byte mysqlByte = mysqlBytes[1];
        this.refresh = MysqlRefreshEnum.parse(mysqlByte);
    }
}
