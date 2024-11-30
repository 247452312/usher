package top.uhyils.usher.mysql.pojo.cqe.impl;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import top.uhyils.usher.mysql.content.MysqlContent;
import top.uhyils.usher.mysql.enums.MysqlCommandTypeEnum;
import top.uhyils.usher.mysql.enums.SqlTypeEnum;
import top.uhyils.usher.mysql.handler.MysqlServiceHandler;
import top.uhyils.usher.mysql.pojo.DTO.DatabaseInfo;
import top.uhyils.usher.mysql.pojo.cqe.AbstractMysqlCommand;
import top.uhyils.usher.mysql.pojo.entity.MysqlTcpLink;
import top.uhyils.usher.mysql.pojo.response.MysqlResponse;
import top.uhyils.usher.mysql.pojo.response.impl.ErrResponse;
import top.uhyils.usher.mysql.pojo.response.impl.OkResponse;
import top.uhyils.usher.mysql.util.Proto;
import top.uhyils.usher.pojo.cqe.query.BlackQuery;
import top.uhyils.usher.util.SpringUtil;
import top.uhyils.usher.util.StringUtil;


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

    private final MysqlServiceHandler mysqlSdkService;

    public ComInitDbCommand(byte[] mysqlBytes) {
        super(mysqlBytes);
        this.mysqlSdkService = SpringUtil.getBean(MysqlServiceHandler.class);
    }

    @Override
    public List<MysqlResponse> invoke() {
        MysqlTcpLink mysqlTcpLink = MysqlContent.MYSQL_TCP_INFO.get();

        // 数据库名称和标准名称一致
        BlackQuery blackQuery = new BlackQuery();
        if (MysqlContent.SYS_DATABASE.contains(sql)) {
            mysqlTcpLink.setDatabase(sql);
            return Collections.singletonList(new OkResponse(SqlTypeEnum.USE));
        }
        // 获取这个人有权限的数据库列表
        List<DatabaseInfo> databaseInfos = mysqlSdkService.getAllDatabaseInfo(blackQuery);
        Optional<DatabaseInfo> first = databaseInfos.stream().filter(t -> StringUtil.equalsIgnoreCase(t.getSchemaName(), sql)).findFirst();
        if (first.isPresent()) {
            DatabaseInfo databaseInfo = first.get();
            mysqlTcpLink.setDatabase(databaseInfo.getSchemaName());
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
        Proto proto = new Proto(mysqlBytes, 4);
        this.sql = proto.get_lenenc_str().trim().toUpperCase(Locale.ROOT);

    }

}
