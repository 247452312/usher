package team.opentech.usher.mysql.pojo.cqe.impl;

import team.opentech.usher.mysql.content.MysqlContent;
import team.opentech.usher.mysql.decode.Proto;
import team.opentech.usher.mysql.enums.MysqlCommandTypeEnum;
import team.opentech.usher.mysql.enums.MysqlServerStatusEnum;
import team.opentech.usher.mysql.enums.SqlTypeEnum;
import team.opentech.usher.mysql.handler.MysqlThisRequestInfo;
import team.opentech.usher.mysql.handler.PrepareInfo;
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
public class ComStmtPrepareCommand extends AbstractMysqlCommand {


    /**
     * 临时预处理缓存id
     */
    private long prepareId;

    public ComStmtPrepareCommand(MysqlThisRequestInfo mysqlThisRequestInfo) {
        super(mysqlThisRequestInfo);
    }

    @Override
    public List<MysqlResponse> invoke() {
        return Arrays.asList(new OkResponse(SqlTypeEnum.QUERY, 0L, prepareId, MysqlServerStatusEnum.SERVER_STATUS_IN_TRANS, MysqlContent.MYSQL_TCP_INFO.get().warnCount(), "" + prepareId));
    }

    @Override
    public MysqlCommandTypeEnum type() {
        return MysqlCommandTypeEnum.COM_STMT_PREPARE;
    }

    @Override
    protected void load() {
        byte[] mysqlBytes = mysqlThisRequestInfo.getMysqlBytes();
        Proto proto = new Proto(mysqlBytes, 1);
        String sql = proto.get_lenenc_str();
        this.prepareId = MysqlContent.MYSQL_TCP_INFO.get().addPrepareSql(new PrepareInfo(sql));
    }

}