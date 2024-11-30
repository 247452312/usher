package top.uhyils.usher.mysql.pojo.cqe.impl;

import java.util.Collections;
import java.util.List;
import top.uhyils.usher.mysql.content.MysqlContent;
import top.uhyils.usher.mysql.enums.MysqlCommandTypeEnum;
import top.uhyils.usher.mysql.enums.MysqlServerStatusEnum;
import top.uhyils.usher.mysql.enums.SqlTypeEnum;
import top.uhyils.usher.mysql.pojo.DTO.PrepareInfo;
import top.uhyils.usher.mysql.pojo.cqe.AbstractMysqlCommand;
import top.uhyils.usher.mysql.pojo.response.MysqlResponse;
import top.uhyils.usher.mysql.pojo.response.impl.OkResponse;
import top.uhyils.usher.mysql.util.Proto;


/**
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年11月03日 18时42分
 */
public class ComStmtPrepareCommand extends AbstractMysqlCommand {


    /**
     * 临时预处理缓存id
     */
    private long prepareId;

    public ComStmtPrepareCommand(byte[] mysqlBytes) {
        super(mysqlBytes);
    }

    @Override
    public List<MysqlResponse> invoke() {
        return Collections.singletonList(new OkResponse(SqlTypeEnum.QUERY, 0L, prepareId, MysqlServerStatusEnum.SERVER_STATUS_IN_TRANS, MysqlContent.MYSQL_TCP_INFO.get().warnCount(), "" + prepareId));
    }

    @Override
    public MysqlCommandTypeEnum type() {
        return MysqlCommandTypeEnum.COM_STMT_PREPARE;
    }

    @Override
    protected void load() {
        Proto proto = new Proto(mysqlBytes, 1);
        String sql = proto.get_lenenc_str();
        this.prepareId = MysqlContent.MYSQL_TCP_INFO.get().addPrepareSql(new PrepareInfo(sql));
    }

}
