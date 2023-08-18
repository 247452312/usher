package team.opentech.usher.mysql.pojo.cqe.impl;

import team.opentech.usher.mysql.enums.MysqlCommandTypeEnum;
import team.opentech.usher.mysql.handler.MysqlThisRequestInfo;
import team.opentech.usher.mysql.pojo.cqe.AbstractMysqlCommand;
import team.opentech.usher.mysql.pojo.response.MysqlResponse;
import java.util.List;


/**
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年11月03日 18时42分
 */
public class ComProcessInfoCommand extends AbstractMysqlCommand {

    public ComProcessInfoCommand(MysqlThisRequestInfo mysqlThisRequestInfo) {
        super(mysqlThisRequestInfo);
    }

    @Override
    public List<MysqlResponse> invoke() {
        // todo 获取线程信息
        return null;
    }

    @Override
    public MysqlCommandTypeEnum type() {
        return MysqlCommandTypeEnum.COM_PROCESS_INFO;
    }

    @Override
    protected void load() {

    }
}
