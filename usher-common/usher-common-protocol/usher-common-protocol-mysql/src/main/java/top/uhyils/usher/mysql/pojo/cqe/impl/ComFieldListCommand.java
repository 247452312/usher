package top.uhyils.usher.mysql.pojo.cqe.impl;

import java.util.List;
import top.uhyils.usher.mysql.enums.MysqlCommandTypeEnum;
import top.uhyils.usher.mysql.pojo.cqe.AbstractMysqlCommand;
import top.uhyils.usher.mysql.pojo.response.MysqlResponse;


/**
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年11月03日 18时42分
 */
public class ComFieldListCommand extends AbstractMysqlCommand {

    private String tableName;

    private List<String> fieldList;

    public ComFieldListCommand(byte[] mysqlBytes) {
        super(mysqlBytes);
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
        //        Proto proto = new Proto(findMysqlBytes(), 1);
        //        this.tableName = proto.get_null_str();
        //        String lenencStr = proto.get_lenenc_str();
        // todo 测试传过来的是什么

    }
}
