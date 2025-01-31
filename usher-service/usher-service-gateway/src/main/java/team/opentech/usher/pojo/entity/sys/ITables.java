package team.opentech.usher.pojo.entity.sys;

import com.alibaba.fastjson.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import team.opentech.usher.context.UserInfoHelper;
import team.opentech.usher.enums.Symbol;
import team.opentech.usher.mysql.enums.FieldTypeEnum;
import team.opentech.usher.mysql.enums.TableTypeEnum;
import team.opentech.usher.mysql.pojo.DTO.FieldInfo;
import team.opentech.usher.mysql.pojo.DTO.NodeInvokeResult;
import team.opentech.usher.mysql.pojo.DTO.TableInfo;
import team.opentech.usher.pojo.DO.CallNodeDO;
import team.opentech.usher.pojo.DTO.CallNodeDTO;
import team.opentech.usher.pojo.DTO.UserDTO;
import team.opentech.usher.pojo.cqe.query.demo.Arg;
import team.opentech.usher.service.CallNodeService;
import team.opentech.usher.util.Asserts;
import team.opentech.usher.util.CollectionUtil;
import team.opentech.usher.util.GatewayUtil;
import team.opentech.usher.util.Pair;
import team.opentech.usher.util.SpringUtil;
import team.opentech.usher.util.StringUtil;

/**
 * information_schema.TABLES 表
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2022年09月08日 09时13分
 */
public class ITables extends AbstractSysTable {

    private final CallNodeService callNodeService;

    public ITables(Map<String, Object> params) {
        super(params);
        this.params = params.entrySet().stream().collect(Collectors.toMap(t -> t.getKey().toLowerCase(), Entry::getValue));
        this.callNodeService = SpringUtil.getBean(CallNodeService.class);
    }

    @Override
    public NodeInvokeResult doGetResultNoParams() {
        Object schemaName = params.get("table_schema");
        List<Arg> args = new ArrayList<>();
        Optional<UserDTO> userOptional = UserInfoHelper.get();
        if (!userOptional.isPresent()) {
            throw Asserts.makeException("未登录");
        }
        args.add(Arg.as(CallNodeDO::getCompanyId, Symbol.EQ, userOptional.get().getId()));
        if (schemaName != null) {
            args.add(Arg.as(CallNodeDO::getUrl, Symbol.like, schemaName + "/%"));
        }
        List<CallNodeDTO> callNodeDTOS = callNodeService.queryNoPage(args);

        List<Map<String, Object>> newResults = new ArrayList<>();
        Set<String> dbSet = new HashSet<>();
        callNodeDTOS.stream().filter(t -> {
            String url = t.getUrl();
            Pair<String, String> splitDataBaseUrl = GatewayUtil.splitDataBaseUrl(url);
            String database = splitDataBaseUrl.getKey();
            if (dbSet.contains(database)) {
                return false;
            } else {
                dbSet.add(database);
                return true;
            }
        }).forEach(t -> {
            Pair<String, String> splitDataBaseUrl = GatewayUtil.splitDataBaseUrl(t.getUrl());
            TableInfo tableInfo = new TableInfo();
            tableInfo.setTableSchema(splitDataBaseUrl.getKey());
            tableInfo.setTableName(splitDataBaseUrl.getValue());
            tableInfo.setTableType(TableTypeEnum.BASE_TABLE);
            newResults.add(JSONObject.parseObject(JSONObject.toJSONString(tableInfo)));
        });

        NodeInvokeResult nodeInvokeResult = new NodeInvokeResult(null);
        if (CollectionUtil.isNotEmpty(newResults)) {
            List<Map<String, Object>> tempResults = new ArrayList<>();
            Map<String, Object> first = newResults.get(0);
            Map<String, String> fieldNameMap = first.keySet().stream().collect(Collectors.toMap(t -> t, t -> StringUtil.toUnderline(t).toUpperCase()));
            for (Map<String, Object> newResult : newResults) {
                Map<String, Object> tempNewResultMap = new HashMap<>(newResult.size());
                for (Entry<String, Object> newResultItem : newResult.entrySet()) {
                    String key = newResultItem.getKey();
                    Object value = newResultItem.getValue();
                    tempNewResultMap.put(fieldNameMap.get(key), value);
                }
                tempResults.add(tempNewResultMap);
            }
            newResults.clear();
            newResults.addAll(tempResults);
        }
        nodeInvokeResult.setResult(newResults);
        List<FieldInfo> fieldInfos = new ArrayList<>();
        fieldInfos.add(new FieldInfo("information_schema", "tables", "tables", "TABLE_CATALOG", "TABLE_CATALOG", 0, 1, FieldTypeEnum.FIELD_TYPE_VARCHAR, (short) 0, (byte) 0));
        fieldInfos.add(new FieldInfo("information_schema", "tables", "tables", "TABLE_SCHEMA", "TABLE_SCHEMA", 0, 1, FieldTypeEnum.FIELD_TYPE_VARCHAR, (short) 0, (byte) 0));
        fieldInfos.add(new FieldInfo("information_schema", "tables", "tables", "TABLE_NAME", "TABLE_NAME", 0, 1, FieldTypeEnum.FIELD_TYPE_VARCHAR, (short) 0, (byte) 0));
        fieldInfos.add(new FieldInfo("information_schema", "tables", "tables", "CREATE_OPTIONS", "CREATE_OPTIONS", 0, 1, FieldTypeEnum.FIELD_TYPE_VARCHAR, (short) 0, (byte) 0));
        fieldInfos.add(new FieldInfo("information_schema", "tables", "tables", "TABLE_COMMENT", "TABLE_COMMENT", 0, 1, FieldTypeEnum.FIELD_TYPE_VARCHAR, (short) 0, (byte) 0));
        fieldInfos.add(new FieldInfo("information_schema", "tables", "tables", "TABLE_TYPE", "TABLE_TYPE", 0, 1, FieldTypeEnum.FIELD_TYPE_VARCHAR, (short) 0, (byte) 0));
        fieldInfos.add(new FieldInfo("information_schema", "tables", "tables", "ENGINE", "ENGINE", 0, 1, FieldTypeEnum.FIELD_TYPE_VARCHAR, (short) 0, (byte) 0));
        fieldInfos.add(new FieldInfo("information_schema", "tables", "tables", "VERSION", "VERSION", 0, 1, FieldTypeEnum.FIELD_TYPE_INT24, (short) 0, (byte) 0));
        fieldInfos.add(new FieldInfo("information_schema", "tables", "tables", "ROW_FORMAT", "ROW_FORMAT", 0, 1, FieldTypeEnum.FIELD_TYPE_VARCHAR, (short) 0, (byte) 0));
        fieldInfos.add(new FieldInfo("information_schema", "tables", "tables", "TABLE_ROWS", "TABLE_ROWS", 0, 1, FieldTypeEnum.FIELD_TYPE_INT24, (short) 0, (byte) 0));
        fieldInfos.add(new FieldInfo("information_schema", "tables", "tables", "AVG_ROW_LENGTH", "AVG_ROW_LENGTH", 0, 1, FieldTypeEnum.FIELD_TYPE_INT24, (short) 0, (byte) 0));
        fieldInfos.add(new FieldInfo("information_schema", "tables", "tables", "DATA_LENGTH", "DATA_LENGTH", 0, 1, FieldTypeEnum.FIELD_TYPE_INT24, (short) 0, (byte) 0));
        fieldInfos.add(new FieldInfo("information_schema", "tables", "tables", "MAX_DATA_LENGTH", "MAX_DATA_LENGTH", 0, 1, FieldTypeEnum.FIELD_TYPE_INT24, (short) 0, (byte) 0));
        fieldInfos.add(new FieldInfo("information_schema", "tables", "tables", "INDEX_LENGTH", "INDEX_LENGTH", 0, 1, FieldTypeEnum.FIELD_TYPE_INT24, (short) 0, (byte) 0));
        fieldInfos.add(new FieldInfo("information_schema", "tables", "tables", "DATA_FREE", "DATA_FREE", 0, 1, FieldTypeEnum.FIELD_TYPE_INT24, (short) 0, (byte) 0));
        fieldInfos.add(new FieldInfo("information_schema", "tables", "tables", "AUTO_INCREMENT", "AUTO_INCREMENT", 0, 1, FieldTypeEnum.FIELD_TYPE_INT24, (short) 0, (byte) 0));
        fieldInfos.add(new FieldInfo("information_schema", "tables", "tables", "CREATE_TIME", "CREATE_TIME", 0, 1, FieldTypeEnum.FIELD_TYPE_TIMESTAMP, (short) 0, (byte) 0));
        fieldInfos.add(new FieldInfo("information_schema", "tables", "tables", "UPDATE_TIME", "UPDATE_TIME", 0, 1, FieldTypeEnum.FIELD_TYPE_TIMESTAMP, (short) 0, (byte) 0));
        fieldInfos.add(new FieldInfo("information_schema", "tables", "tables", "CHECK_TIME", "CHECK_TIME", 0, 1, FieldTypeEnum.FIELD_TYPE_TIMESTAMP, (short) 0, (byte) 0));
        fieldInfos.add(new FieldInfo("information_schema", "tables", "tables", "TABLE_COLLATION", "TABLE_COLLATION", 0, 1, FieldTypeEnum.FIELD_TYPE_VARCHAR, (short) 0, (byte) 0));
        fieldInfos.add(new FieldInfo("information_schema", "tables", "tables", "CHECKSUM", "CHECKSUM", 0, 1, FieldTypeEnum.FIELD_TYPE_VARCHAR, (short) 0, (byte) 0));

        nodeInvokeResult.setFieldInfos(fieldInfos);
        return nodeInvokeResult;
    }
}
