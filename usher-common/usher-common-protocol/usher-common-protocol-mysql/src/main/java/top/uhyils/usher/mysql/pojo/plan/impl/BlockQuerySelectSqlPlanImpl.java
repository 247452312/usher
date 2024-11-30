package top.uhyils.usher.mysql.pojo.plan.impl;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.expr.SQLBinaryOpExpr;
import com.alibaba.druid.sql.ast.expr.SQLCharExpr;
import com.alibaba.druid.sql.ast.expr.SQLIdentifierExpr;
import com.alibaba.druid.sql.ast.expr.SQLPropertyExpr;
import com.alibaba.druid.sql.ast.statement.SQLExprTableSource;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.stream.Collectors;
import top.uhyils.usher.mysql.content.MysqlContent;
import top.uhyils.usher.mysql.handler.MysqlServiceHandler;
import top.uhyils.usher.mysql.pojo.DTO.FieldInfo;
import top.uhyils.usher.mysql.pojo.DTO.NodeInvokeResult;
import top.uhyils.usher.mysql.pojo.SqlTableSourceBinaryTreeInfo;
import top.uhyils.usher.mysql.pojo.cqe.InvokeCommandBuilder;
import top.uhyils.usher.mysql.pojo.cqe.MysqlInvokeCommand;
import top.uhyils.usher.mysql.pojo.plan.BlockQuerySelectSqlPlan;
import top.uhyils.usher.util.Asserts;
import top.uhyils.usher.util.CollectionUtil;
import top.uhyils.usher.util.MapUtil;
import top.uhyils.usher.util.SpringUtil;
import top.uhyils.usher.util.StringUtil;

/**
 * 简单sql执行计划
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2022年08月26日 16时31分
 */
public class BlockQuerySelectSqlPlanImpl extends BlockQuerySelectSqlPlan {

    /**
     * 节点
     */
    private final MysqlServiceHandler mysqlSdkService;


    public BlockQuerySelectSqlPlanImpl(SqlTableSourceBinaryTreeInfo froms, Map<String, String> headers, Map<String, Object> params) {
        super(froms, headers, params);
        this.mysqlSdkService = SpringUtil.getBean(MysqlServiceHandler.class);
    }

    @Override
    public NodeInvokeResult invoke() {
        InvokeCommandBuilder invokeCommandBuilder = new InvokeCommandBuilder();
        invokeCommandBuilder.addArgs(params);
        invokeCommandBuilder.addHeader(headers);
        SQLExprTableSource tableSource = froms.getTableSource();
        invokeCommandBuilder.addAlias(tableSource.getAlias());
        SQLPropertyExpr expr = (SQLPropertyExpr) tableSource.getExpr();
        String owner = expr.getOwnernName();
        String tableName = expr.getName();
        if (tableName.startsWith("&")) {
            Long resultIndex = Long.parseLong(tableName.substring(1));
            return lastAllPlanResult.get(resultIndex);
        }
        List<SQLBinaryOpExpr> where = froms.getWhere();
        Map<String, Object> whereParams = new HashMap<>();
        boolean haveResult = true;
        if (where != null) {
            for (SQLBinaryOpExpr sqlBinaryOpExpr : where) {
                SQLExpr left = sqlBinaryOpExpr.getLeft();
                SQLExpr right = sqlBinaryOpExpr.getRight();
                // where两边都不是属性的时候直接忽略
                if (!(left instanceof SQLIdentifierExpr) && !(right instanceof SQLIdentifierExpr)) {
                    // 如果两边不一致,则无结果
                    if (!Objects.equals(left.toString(), right.toString())) {
                        haveResult = false;
                    }
                    continue;
                }
                String rightStr = right.toString();
                if (right instanceof SQLCharExpr) {
                    rightStr = ((SQLCharExpr) right).getText();
                }
                whereParams.put(left.toString(), rightStr);
            }
        }
        invokeCommandBuilder.addArgs(whereParams);
        String database = MysqlContent.MYSQL_TCP_INFO.get().getDatabase();
        if (owner != null) {
            invokeCommandBuilder.fillDatabase(owner);
        } else if (StringUtil.isNotEmpty(database)) {
            invokeCommandBuilder.fillDatabase(database);
        } else {
            Asserts.throwException("No database selected");
        }
        invokeCommandBuilder.fillTable(tableName);
        MysqlInvokeCommand build = invokeCommandBuilder.build();
        NodeInvokeResult nodeInvokeResult = mysqlSdkService.invokeSingleQuerySql(build);
        nodeInvokeResult.setSourcePlan(this);
        if (!haveResult) {
            nodeInvokeResult.setResult(new ArrayList<>());
        }

        // 平铺/展开结果中的json
        nodeInvokeResult = tileResultJson(nodeInvokeResult);
        return nodeInvokeResult;
    }

    private NodeInvokeResult tileResultJson(NodeInvokeResult nodeInvokeResult) {
        List<FieldInfo> fieldInfos = nodeInvokeResult.getFieldInfos();
        List<Map<String, Object>> result = nodeInvokeResult.getResult();
        if (CollectionUtil.isEmpty(fieldInfos) || CollectionUtil.isEmpty(result)) {
            return nodeInvokeResult;
        }
        boolean change = false;
        Map<String, FieldInfo> fieldInfoMap = fieldInfos.stream().collect(Collectors.toMap(FieldInfo::getFieldName, t -> t));
        List<Map<String, Object>> newResult = new ArrayList<>();
        // 遍历行
        for (Map<String, Object> resultItem : result) {
            Map<String, Object> newLine = new HashMap<>();
            List<Map<String, Object>> newResultTemp = new ArrayList<>();
            // 初始只有一行
            newResultTemp.add(newLine);

            // 遍历列
            for (Entry<String, Object> resultCell : resultItem.entrySet()) {
                Object value = resultCell.getValue();
                if (value instanceof JSON) {
                    change = true;
                }
                // 一行展开成多行
                if (value instanceof JSONArray) {
                    JSONArray jsonArrayValue = (JSONArray) value;
                    List<Map<String, Object>> newResultTempTemp = new ArrayList<>();
                    for (Object o : jsonArrayValue) {
                        // 每一行都要进行自我复制成多行
                        for (Map<String, Object> item : newResultTemp) {
                            Map<String, Object> copy = MapUtil.copy(item);
                            copy.put(resultCell.getKey(), o);
                            newResultTempTemp.add(copy);
                        }
                    }
                    newResultTemp = newResultTempTemp;
                } else if (value instanceof JSONObject) {
                    List<FieldInfo> fieldInfoTemp = new ArrayList<>(fieldInfos);
                    FieldInfo oldField = fieldInfoMap.remove(resultCell.getKey());
                    fieldInfoTemp.remove(oldField);
                    JSONObject jsonObject = (JSONObject) value;
                    for (Entry<String, Object> objectEntry : jsonObject.entrySet()) {
                        String newFieldName = resultCell.getKey() + "." + objectEntry.getKey();
                        if (!fieldInfoMap.containsKey(newFieldName)) {
                            FieldInfo newFieldInfo = oldField.copyWithNewFieldName(newFieldName);
                            fieldInfoTemp.add(newFieldInfo);
                            fieldInfoMap.put(newFieldName, newFieldInfo);
                        }
                        for (Map<String, Object> stringObjectMap : newResultTemp) {
                            stringObjectMap.remove(resultCell.getKey());
                            stringObjectMap.put(newFieldName, objectEntry.getValue());
                        }
                    }
                    fieldInfos = fieldInfoTemp;
                } else {
                    for (Map<String, Object> item : newResultTemp) {
                        item.put(resultCell.getKey(), resultCell.getValue());
                    }
                }
            }
            newResult.addAll(newResultTemp);
        }
        if (Boolean.FALSE.equals(change)) {
            return nodeInvokeResult;
        }
        NodeInvokeResult nodeInvokeResult1 = new NodeInvokeResult(this);
        nodeInvokeResult1.setFieldInfos(fieldInfos);
        nodeInvokeResult1.setResult(newResult);

        return tileResultJson(nodeInvokeResult1);
    }

}
