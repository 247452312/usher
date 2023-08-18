package team.opentech.usher.mysql.pojo.cqe.impl;

import team.opentech.usher.mysql.decode.Proto;
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
public class ComFieldListCommand extends AbstractMysqlCommand {

    private String tableName;

    private List<String> fieldList;

    public ComFieldListCommand(MysqlThisRequestInfo mysqlThisRequestInfo) {
        super(mysqlThisRequestInfo);
    }

    @Override
    public List<MysqlResponse> invoke() {
        /*1.根据tableName去数据库获取*/
        return null;
    }

    @Override
    public MysqlCommandTypeEnum type() {
        return MysqlCommandTypeEnum.COM_FIELD_LIST;
    }

    @Override
    protected void load() {
        Proto proto = new Proto(getMysqlThisRequestInfo().getMysqlBytes(), 1);
        this.tableName = proto.get_null_str();
        String lenencStr = proto.get_lenenc_str();
        // todo 测试传过来的是什么

    }
}
