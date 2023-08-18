package team.opentech.usher.pojo.entity.sys;

import com.alibaba.fastjson.JSONObject;
import team.opentech.usher.context.UserInfoHelper;
import team.opentech.usher.enums.Symbol;
import team.opentech.usher.mysql.enums.FieldTypeEnum;
import team.opentech.usher.mysql.pojo.DTO.ColumnsInfo;
import team.opentech.usher.mysql.pojo.DTO.FieldInfo;
import team.opentech.usher.mysql.pojo.DTO.NodeInvokeResult;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 里面记录了mysql所有库中所有表的字段信息
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2022年09月08日 14时24分
 */
public class IColumns extends AbstractSysTable {


    private final CallNodeService callNodeService;

    public IColumns(Map<String, Object> params) {
        super(params);
        this.params = params.entrySet().stream().collect(Collectors.toMap(t -> t.getKey().toLowerCase(), Entry::getValue));
        this.callNodeService = SpringUtil.getBean(CallNodeService.class);
    }

    @Override
    public NodeInvokeResult doGetResultNoParams() {
        Object schemaName = params.get("schema_name");
        ArrayList<Arg> args = new ArrayList<>();
        Optional<UserDTO> userOptional = UserInfoHelper.get();
        if (!userOptional.isPresent()) {
            throw Asserts.makeException("未登录");
        }
        args.add(Arg.as(CallNodeDO::getCompanyId, Symbol.EQ, userOptional.get().getId()));
        if (schemaName != null) {
            args.add(Arg.as(CallNodeDO::getUrl, Symbol.like, schemaName + "/%"));
        }
        List<CallNodeDTO> callNodeDTOS = callNodeService.queryWithAllNode(args);

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
            ColumnsInfo columnsInfo = new ColumnsInfo();

            newResults.add(JSONObject.parseObject(JSONObject.toJSONString(columnsInfo)));
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
        fieldInfos.add(new FieldInfo("information_schema", "columns", "columns", "TABLE_CATALOG", "TABLE_CATALOG", 0, 1, FieldTypeEnum.FIELD_TYPE_VARCHAR, (short) 0, (byte) 0));
        fieldInfos.add(new FieldInfo("information_schema", "columns", "columns", "TABLE_SCHEMA", "TABLE_SCHEMA", 0, 1, FieldTypeEnum.FIELD_TYPE_VARCHAR, (short) 0, (byte) 0));
        fieldInfos.add(new FieldInfo("information_schema", "columns", "columns", "TABLE_NAME", "TABLE_NAME", 0, 1, FieldTypeEnum.FIELD_TYPE_VARCHAR, (short) 0, (byte) 0));
        fieldInfos.add(new FieldInfo("information_schema", "columns", "columns", "COLUMN_NAME", "COLUMN_NAME", 0, 1, FieldTypeEnum.FIELD_TYPE_VARCHAR, (short) 0, (byte) 0));
        fieldInfos.add(new FieldInfo("information_schema", "columns", "columns", "ORDINAL_POSITION", "ORDINAL_POSITION", 0, 1, FieldTypeEnum.FIELD_TYPE_INT24, (short) 0, (byte) 0));
        fieldInfos.add(new FieldInfo("information_schema", "columns", "columns", "DATA_TYPE", "DATA_TYPE", 0, 1, FieldTypeEnum.FIELD_TYPE_VARCHAR, (short) 0, (byte) 0));
        fieldInfos.add(new FieldInfo("information_schema", "columns", "columns", "DATETIME_PRECISION", "DATETIME_PRECISION", 0, 1, FieldTypeEnum.FIELD_TYPE_INT24, (short) 0, (byte) 0));
        fieldInfos.add(new FieldInfo("information_schema", "columns", "columns", "COLUMN_TYPE", "COLUMN_TYPE", 0, 1, FieldTypeEnum.FIELD_TYPE_VARCHAR, (short) 0, (byte) 0));
        fieldInfos.add(new FieldInfo("information_schema", "columns", "columns", "PRIVILEGES", "PRIVILEGES", 0, 1, FieldTypeEnum.FIELD_TYPE_VARCHAR, (short) 0, (byte) 0));
        fieldInfos.add(new FieldInfo("information_schema", "columns", "columns", "COLUMN_COMMENT", "COLUMN_COMMENT", 0, 1, FieldTypeEnum.FIELD_TYPE_VARCHAR, (short) 0, (byte) 0));
        fieldInfos.add(new FieldInfo("information_schema", "columns", "columns", "COLUMN_DEFAULT", "COLUMN_DEFAULT", 0, 1, FieldTypeEnum.FIELD_TYPE_VARCHAR, (short) 0, (byte) 0));
        fieldInfos.add(new FieldInfo("information_schema", "columns", "columns", "IS_NULLABLE", "IS_NULLABLE", 0, 1, FieldTypeEnum.FIELD_TYPE_VARCHAR, (short) 0, (byte) 0));
        fieldInfos.add(new FieldInfo("information_schema", "columns", "columns", "CHARACTER_MAXIMUM_LENGTH", "CHARACTER_MAXIMUM_LENGTH", 0, 1, FieldTypeEnum.FIELD_TYPE_INT24, (short) 0, (byte) 0));
        fieldInfos.add(new FieldInfo("information_schema", "columns", "columns", "CHARACTER_OCTET_LENGTH", "CHARACTER_OCTET_LENGTH", 0, 1, FieldTypeEnum.FIELD_TYPE_INT24, (short) 0, (byte) 0));
        fieldInfos.add(new FieldInfo("information_schema", "columns", "columns", "NUMERIC_PRECISION", "NUMERIC_PRECISION", 0, 1, FieldTypeEnum.FIELD_TYPE_INT24, (short) 0, (byte) 0));
        fieldInfos.add(new FieldInfo("information_schema", "columns", "columns", "NUMERIC_SCALE", "NUMERIC_SCALE", 0, 1, FieldTypeEnum.FIELD_TYPE_INT24, (short) 0, (byte) 0));
        fieldInfos.add(new FieldInfo("information_schema", "columns", "columns", "CHARACTER_SET_NAME", "CHARACTER_SET_NAME", 0, 1, FieldTypeEnum.FIELD_TYPE_VARCHAR, (short) 0, (byte) 0));
        fieldInfos.add(new FieldInfo("information_schema", "columns", "columns", "COLLATION_NAME", "COLLATION_NAME", 0, 1, FieldTypeEnum.FIELD_TYPE_VARCHAR, (short) 0, (byte) 0));
        fieldInfos.add(new FieldInfo("information_schema", "columns", "columns", "COLUMN_KEY", "COLUMN_KEY", 0, 1, FieldTypeEnum.FIELD_TYPE_VARCHAR, (short) 0, (byte) 0));
        fieldInfos.add(new FieldInfo("information_schema", "columns", "columns", "EXTRA", "EXTRA", 0, 1, FieldTypeEnum.FIELD_TYPE_VARCHAR, (short) 0, (byte) 0));
        fieldInfos.add(new FieldInfo("information_schema", "columns", "columns", "GENERATION_EXPRESSION", "GENERATION_EXPRESSION", 0, 1, FieldTypeEnum.FIELD_TYPE_VARCHAR, (short) 0, (byte) 0));
        fieldInfos.add(new FieldInfo("information_schema", "columns", "columns", "SRS_ID", "SRS_ID", 0, 1, FieldTypeEnum.FIELD_TYPE_VARCHAR, (short) 0, (byte) 0));

        nodeInvokeResult.setFieldInfos(fieldInfos);
        return nodeInvokeResult;
    }
}
