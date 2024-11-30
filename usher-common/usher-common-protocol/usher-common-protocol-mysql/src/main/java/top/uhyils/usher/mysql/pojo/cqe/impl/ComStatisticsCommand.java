package top.uhyils.usher.mysql.pojo.cqe.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import top.uhyils.usher.mysql.content.MysqlContent;
import top.uhyils.usher.mysql.enums.FieldMarkEnum;
import top.uhyils.usher.mysql.enums.FieldTypeEnum;
import top.uhyils.usher.mysql.enums.MysqlCommandTypeEnum;
import top.uhyils.usher.mysql.enums.MysqlServerStatusEnum;
import top.uhyils.usher.mysql.pojo.DTO.FieldInfo;
import top.uhyils.usher.mysql.pojo.cqe.AbstractMysqlCommand;
import top.uhyils.usher.mysql.pojo.entity.MysqlTcpLink;
import top.uhyils.usher.mysql.pojo.response.MysqlResponse;
import top.uhyils.usher.mysql.pojo.response.impl.ResultSetResponse;
import top.uhyils.usher.util.SpringUtil;


/**
 * 获取服务器某个信息的请求
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年11月03日 18时42分
 */
public class ComStatisticsCommand extends AbstractMysqlCommand {

    /**
     * 静态信息表
     */
    private static final String STATIC_TABLE_NAME = "static_info";

    /**
     * 根节点数据库名称
     */
    private final String root;

    public ComStatisticsCommand(byte[] mysqlBytes) {
        super(mysqlBytes);
        root = SpringUtil.getProperty("mysql.db-name", "root");
    }

    @Override
    public List<MysqlResponse> invoke() {
        ArrayList<FieldInfo> fields = new ArrayList<>();
        MysqlTcpLink mysqlTcpLink = MysqlContent.MYSQL_TCP_INFO.get();
        fields.add(new FieldInfo(root, STATIC_TABLE_NAME, STATIC_TABLE_NAME, "运行时间", "time", 3, (int) mysqlTcpLink.index(), FieldTypeEnum.FIELD_TYPE_LONG, FieldMarkEnum.TIMESTAMP_FLAG
            .getCode(), (byte) 3));

        fields
            .add(new FieldInfo(root, STATIC_TABLE_NAME, STATIC_TABLE_NAME, "每秒执行次数", "executions_per_second", 3, (int) mysqlTcpLink.index(), FieldTypeEnum.FIELD_TYPE_LONG, FieldMarkEnum.ZEROFILL_FLAG
                .getCode(), (byte) 3));

        List<Map<String, Object>> jsonArrayObj = new ArrayList<>(1);
        Map<String, Object> jsonResult = new HashMap<>(2);
        jsonResult.put("time", 0L);
        jsonResult.put("executions_per_second", 0L);
        jsonArrayObj.add(jsonResult);
        return Collections.singletonList(new ResultSetResponse(fields, jsonArrayObj, MysqlServerStatusEnum.SERVER_STATUS_IN_TRANS, mysqlTcpLink.warnCount()));
    }

    @Override
    public MysqlCommandTypeEnum type() {
        return MysqlCommandTypeEnum.COM_STATISTICS;
    }

    @Override
    protected void load() {
    }
}
