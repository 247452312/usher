package team.opentech.usher.mysql.pojo.cqe.impl;

import team.opentech.usher.mysql.enums.MysqlCommandTypeEnum;
import team.opentech.usher.mysql.handler.MysqlThisRequestInfo;
import team.opentech.usher.mysql.pojo.cqe.AbstractMysqlCommand;
import team.opentech.usher.mysql.pojo.response.MysqlResponse;
import team.opentech.usher.mysql.pojo.response.impl.ErrResponse;
import java.util.Collections;
import java.util.List;


/**
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年11月03日 18时42分
 */
public class ComCreateDbCommand extends AbstractMysqlCommand {

    public ComCreateDbCommand(MysqlThisRequestInfo mysqlThisRequestInfo) {
        super(mysqlThisRequestInfo);
    }

    @Override
    public List<MysqlResponse> invoke() {
        return Collections.singletonList(ErrResponse.build("请去对接平台配置页面建表"));
    }

    @Override
    public MysqlCommandTypeEnum type() {
        return MysqlCommandTypeEnum.COM_CREATE_DB;
    }

    @Override
    protected void load() {

    }
}
