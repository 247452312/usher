package team.opentech.usher.mysql.pojo.cqe.impl;

import team.opentech.usher.mysql.content.MysqlContent;
import team.opentech.usher.mysql.decode.Proto;
import team.opentech.usher.mysql.enums.MysqlCommandTypeEnum;
import team.opentech.usher.mysql.enums.SqlTypeEnum;
import team.opentech.usher.mysql.handler.MysqlTcpInfo;
import team.opentech.usher.mysql.handler.MysqlThisRequestInfo;
import team.opentech.usher.mysql.pojo.DTO.DatabaseInfo;
import team.opentech.usher.mysql.pojo.cqe.AbstractMysqlCommand;
import team.opentech.usher.mysql.pojo.response.MysqlResponse;
import team.opentech.usher.mysql.pojo.response.impl.ErrResponse;
import team.opentech.usher.mysql.pojo.response.impl.OkResponse;
import team.opentech.usher.mysql.service.MysqlSdkService;
import team.opentech.usher.pojo.cqe.query.BlackQuery;
import team.opentech.usher.util.SpringUtil;
import team.opentech.usher.util.StringUtil;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;


/**
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年11月03日 18时42分
 */
public class ComInitDbCommand extends AbstractMysqlCommand {

    /**
     * 使用
     */
    private static final String SQL_START = "USE ";

    private String sql;

    private MysqlSdkService mysqlSdkService;

    public ComInitDbCommand(MysqlThisRequestInfo mysqlThisRequestInfo) {
        super(mysqlThisRequestInfo);
        this.mysqlSdkService = SpringUtil.getBean(MysqlSdkService.class);
    }

    @Override
    public List<MysqlResponse> invoke() {
        MysqlTcpInfo mysqlTcpInfo = MysqlContent.MYSQL_TCP_INFO.get();

        // 数据库名称和标准名称一致
        BlackQuery blackQuery = new BlackQuery();
        if (MysqlContent.SYS_DATABASE.contains(sql)) {
            mysqlTcpInfo.setDatabase(sql);
            return Collections.singletonList(new OkResponse(SqlTypeEnum.USE));
        }
        // 获取这个人有权限的数据库列表
        List<DatabaseInfo> databaseInfos = mysqlSdkService.getAllDatabaseInfo(blackQuery);
        Optional<DatabaseInfo> first = databaseInfos.stream().filter(t -> StringUtil.equalsIgnoreCase(t.getSchemaName(), sql)).findFirst();
        if (first.isPresent()) {
            DatabaseInfo databaseInfo = first.get();
            mysqlTcpInfo.setDatabase(databaseInfo.getSchemaName());
            return Collections.singletonList(new OkResponse(SqlTypeEnum.USE));
        }
        // 不一致就报错
        return Collections.singletonList(ErrResponse.build("没有发现数据库: " + sql));
    }

    @Override
    public MysqlCommandTypeEnum type() {
        return MysqlCommandTypeEnum.COM_INIT_DB;
    }

    @Override
    protected void load() {
        Proto proto = new Proto(mysqlThisRequestInfo.getMysqlBytes(), 4);
        this.sql = proto.get_lenenc_str().trim().toUpperCase(Locale.ROOT);

    }

}
