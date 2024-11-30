package top.uhyils.usher.mysql.pojo.plan.impl;

import com.alibaba.druid.sql.ast.statement.SQLSelectItem;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Objects;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import top.uhyils.usher.annotation.NotNull;
import top.uhyils.usher.mysql.content.MysqlContent;
import top.uhyils.usher.mysql.content.MysqlGlobalVariables;
import top.uhyils.usher.mysql.enums.FieldTypeEnum;
import top.uhyils.usher.mysql.enums.MysqlMethodEnum;
import top.uhyils.usher.mysql.plan.MysqlPlan;
import top.uhyils.usher.mysql.pojo.DTO.FieldInfo;
import top.uhyils.usher.mysql.pojo.DTO.NodeInvokeResult;
import top.uhyils.usher.mysql.pojo.plan.AbstractResultMappingPlan;
import top.uhyils.usher.mysql.pojo.plan.BlockQuerySelectSqlPlan;
import top.uhyils.usher.mysql.pojo.plan.JoinSqlPlan;
import top.uhyils.usher.mysql.pojo.sql.MySQLSelectItem;
import top.uhyils.usher.mysql.util.StringUtil;
import top.uhyils.usher.util.Asserts;
import top.uhyils.usher.util.CollectionUtil;
import top.uhyils.usher.util.JSONUtil;
import top.uhyils.usher.util.SpringUtil;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2022年08月26日 16时33分
 */
public class ResultMappingPlanImpl extends AbstractResultMappingPlan {


    /**
     * 是否是每行一个结果
     */
    private final Boolean mergeable;

    /**
     * mysql系统变量
     */
    private final JSONObject mysqlSystemVariables;

    /**
     * 当前映射之前最后一个查询执行计划的结果
     */
    private NodeInvokeResult lastQueryPlanResult;

    public ResultMappingPlanImpl(Map<String, String> headers, MysqlPlan lastMainPlan, List<MySQLSelectItem> selectList) {
        super(headers, lastMainPlan, selectList);
        MysqlGlobalVariables bean = SpringUtil.getBean(MysqlGlobalVariables.class);
        this.mysqlSystemVariables = JSONObject.parseObject(JSON.toJSONString(bean));
        // 是否是每行一个结果
        List<MysqlMethodEnum> allMethod = selectList.stream().filter(MySQLSelectItem::isMethodItem).map(MySQLSelectItem::method).collect(Collectors.toList());
        // 如果没有method 理论上不需要合并多行数据
        if (CollectionUtil.isEmpty(allMethod)) {
            this.mergeable = false;
        } else {
            // 只要有一个方法需要合并,则需要合并
            this.mergeable = allMethod.stream().anyMatch(MysqlMethodEnum::getMergeable);
        }
    }

    @Override
    public void complete(Map<Long, NodeInvokeResult> planArgs) {
        // 填充占位符
        completePlaceholder(planArgs);
        this.lastAllPlanResult = planArgs;

        List<Long> planIds = planArgs.keySet().stream().sorted(Long::compareTo).collect(Collectors.toList());
        for (int i = planIds.size() - 1; i >= 0; i--) {
            Long key = planIds.get(i);
            NodeInvokeResult nodeInvokeResult = planArgs.get(key);
            MysqlPlan sourcePlan = nodeInvokeResult.getSourcePlan();
            if (sourcePlan instanceof BlockQuerySelectSqlPlan || sourcePlan instanceof JoinSqlPlan) {
                this.lastQueryPlanResult = nodeInvokeResult;
                break;
            }
        }
        Asserts.assertTrue(lastQueryPlanResult != null, "映射执行计划未找到上一个查询执行计划的结果");
    }

    @Override
    public NodeInvokeResult invoke() {
        /**
         * mapping中存在五种fieldName
         *  1.此列源头的name
         *  2.mapping前fieldName
         *  3.mapping后名称
         *  4.别名
         *  5.&开头的名字
         *  最终fieldRealName使用源头name, 实际name使用顺序为: 别名 - mapping后名称 - mapping前名称
         *
         * 整个mapping 应该是分为几种情况
         * 1.带有count,sum等函数的 需要行合并 则结果动态修正
         *  1.1 带有group 需要分组
         *  1.2 不带group 只有一行
         * 2.不需要合并,则可以直接根据上一次结果来直接映射 如果有子查询,则只需要判断size和上一次查询结果的行数来匹配后一一插入即可
         */

        /*如果是需要多行合并成一行的情况*/
        if (mergeable) {
            // 制作存在方法合并时候的结果
            return makeMethodNoSingleLine();
        }

        // 其他情况
        return makeOther();
    }

    /**
     * 除了方法合并的其他情况
     *
     * @return
     */
    private NodeInvokeResult makeOther() {
        // 需要从上一个结果中获取的字段
        /*1.如果结果列只有一个* 则直接返回 (如果除了*还有其他的 则报错)*/
        List<FieldInfo> lastFieldInfos = this.lastQueryPlanResult.getFieldInfos();
        List<Map<String, Object>> lastResult = this.lastQueryPlanResult.getResult();
        // 只允许有一个*
        if (selectList.stream().map(t1 -> t1.getExpr().toString()).collect(Collectors.toList()).contains("*")) {
            if (selectList.size() != 1) {
                Asserts.throwException("*不允许和其他列一起出现,请指定列!");
            }
            return lastQueryPlanResult;
        }

        List<FieldInfo> newFieldInfo = new ArrayList<>();
        List<Map<String, Object>> newResultList = new ArrayList<>();

        for (MySQLSelectItem needField : selectList) {
            String needFieldStr = needField.getExpr().toString();

            String finalName = needField.getAlias();
            SQLSelectItem originalSelectItem = needField.originalSelectItem();
            if (top.uhyils.usher.util.StringUtil.isEmpty(finalName)) {
                finalName = top.uhyils.usher.util.StringUtil.isNotEmpty(originalSelectItem.getAlias()) ? originalSelectItem.getAlias() : originalSelectItem.getExpr().toString();
            }

            /*2.如果结果列存在A.* 或者B.* 则通过tableName回溯寻找表来源,进行一个列表的拼*/
            if (needFieldStr.endsWith("*")) {
                int index = needFieldStr.indexOf('.');
                String needTableName = needFieldStr;
                if (index != -1) {
                    needTableName = needFieldStr.substring(0, index);
                }
                for (FieldInfo lastFieldInfo : lastFieldInfos) {
                    if (!Objects.equal(lastFieldInfo.getTableName(), needTableName)) {
                        continue;
                    }
                    FieldInfo fieldInfo = dealLastFieldInfo(newFieldInfo, lastFieldInfo, finalName);
                    newFieldInfo.add(fieldInfo);
                    for (int i = 0; i < lastResult.size(); i++) {
                        Object o = lastResult.get(i).get(lastFieldInfo.getFieldName());
                        if (newResultList.size() <= i) {
                            newResultList.add(new HashMap<>(16));
                        }
                        Map<String, Object> stringObjectMap = newResultList.get(i);
                        stringObjectMap.put(finalName, o);
                    }
                }
            } else if (needFieldStr.startsWith("&")) {
                /*3.如果结果列存在子查询, 则获取子查询结果并并入*/
                NodeInvokeResult nodeInvokeResult = lastAllPlanResult.get(Long.parseLong(needFieldStr.substring(1)));
                List<FieldInfo> specialLastFieldInfos = nodeInvokeResult.getFieldInfos();
                Asserts.assertTrue(specialLastFieldInfos != null && specialLastFieldInfos.size() == 1, "映射时需要有且仅有一个字段来映射");
                FieldInfo fieldInfo = dealLastFieldInfo(newFieldInfo, specialLastFieldInfos.get(0), finalName);
                newFieldInfo.add(fieldInfo);
                List<Map<String, Object>> result = nodeInvokeResult.getResult();
                Object needFieldResult = null;
                if (CollectionUtil.isNotEmpty(result)) {
                    Map<String, Object> stringObjectMap = result.get(0);
                    needFieldResult = stringObjectMap.get(specialLastFieldInfos.get(0).getFieldName());
                }
                for (Map<String, Object> objectMap : newResultList) {
                    objectMap.put(finalName, needFieldResult);
                }
            } else if (needFieldStr.startsWith("@@") || needField.isGlobal()) {
                // 如果存在查询系统变量,则默认至少有一行
                if (CollectionUtil.isEmpty(newResultList)) {
                    newResultList.add(new HashMap<>());
                }

                /*4.如果列是查询系统配置的,则返回*/
                newFieldInfo.add(new FieldInfo(MysqlContent.DUAL_DATABASES, "dual", "dual", finalName, needFieldStr, 0, 0, FieldTypeEnum.FIELD_TYPE_VARCHAR, (short) 0, (byte) 0));
                String variableName = needFieldStr;
                if (needFieldStr.startsWith("@@")) {
                    variableName = variableName.substring(2);
                }

                if (needField.isGlobal()) {
                    variableName = "global." + variableName;
                }
                Object o = JSONUtil.recursiveMatch(mysqlSystemVariables, variableName);

                for (Map<String, Object> stringObjectMap : newResultList) {
                    stringObjectMap.put(finalName, o);
                }
            } else if (needFieldStr.startsWith("'") && needFieldStr.endsWith("'")) {
                newFieldInfo.add(new FieldInfo(MysqlContent.DUAL_DATABASES, "dual", "dual", finalName, needFieldStr, 0, 0, FieldTypeEnum.FIELD_TYPE_VARCHAR, (short) 0, (byte) 0));
                for (Map<String, Object> stringObjectMap : newResultList) {
                    stringObjectMap.put(finalName, StringUtil.cleanSingleQuotationMark(needFieldStr));
                }
            } else {
                /*4.如果结果列为 A.name A.`name` `A`.`name` 则通过tableName回溯寻找表来源,拼装*/
                needFieldStr = StringUtil.cleanQuotation(needFieldStr);
                FieldInfo lastFieldInfo = queryFieldByKey(needFieldStr, lastFieldInfos);
                if (lastFieldInfo == null) {
                    Asserts.throwException("未找到字段:" + needFieldStr);
                }
                FieldInfo fieldInfo = lastFieldInfo.copyWithNewFieldName(finalName);
                fieldInfo = dealLastFieldInfo(newFieldInfo, fieldInfo, finalName);
                newFieldInfo.add(fieldInfo);

                for (int i = 0; i < lastResult.size(); i++) {
                    Object o = lastResult.get(i).get(lastFieldInfo.getFieldName());
                    if (newResultList.size() <= i) {
                        newResultList.add(new HashMap<>(16));
                    }
                    Map<String, Object> stringObjectMap = newResultList.get(i);
                    stringObjectMap.put(finalName, o);
                }
            }
        }

        NodeInvokeResult nodeInvokeResult = new NodeInvokeResult(this);
        nodeInvokeResult.setFieldInfos(newFieldInfo);
        nodeInvokeResult.setResult(newResultList);
        return nodeInvokeResult;
    }

    /**
     * 制作存在方法合并时候的结果
     * 注: 如果没有group by 则合并只会合并为一条结果, 如果有group by 则遵从group by合并
     * 如果允许这时可以查询单独字段,则默认显示匹配到的第一行
     * 在这里可以默认result就是只有一行, 不存在group by的情况, 如果有group by 应该是多个resultMapping 之后 再进行group之间的合并
     *
     * @return
     */
    @NotNull
    private NodeInvokeResult makeMethodNoSingleLine() {
        Map<String, Object> resultItem = new HashMap<>();
        List<FieldInfo> fieldInfos = new ArrayList<>();
        Map<String, FieldInfo> fieldInfoMap = this.lastQueryPlanResult.getFieldInfos().stream().collect(Collectors.toMap(t -> StringUtil.cleanQuotation(t.getFieldName()), t -> t));


        /*每一个field都需要寻找是否是方法执行, 如果是正常的数据行,则需要判断容错查询参数是否开启*/
        for (MySQLSelectItem needField : selectList) {
            String needFieldStr = needField.getExpr().toString();

            String finalName = needField.getAlias();
            SQLSelectItem originalSelectItem = needField.originalSelectItem();
            if (top.uhyils.usher.util.StringUtil.isEmpty(finalName)) {
                finalName = top.uhyils.usher.util.StringUtil.isNotEmpty(originalSelectItem.getAlias()) ? originalSelectItem.getAlias() : originalSelectItem.getExpr().toString();
            }

            if (needFieldStr.startsWith("&")) {
                /*从之前的执行计划结果中获取实际结果,此时实际结果只能有一列一行, 名称从field的原始列信息中获取*/
                NodeInvokeResult nodeInvokeResult = lastAllPlanResult.get(Long.parseLong(needFieldStr.substring(1)));
                List<FieldInfo> specialLastFieldInfos = nodeInvokeResult.getFieldInfos();
                Asserts.assertTrue(specialLastFieldInfos != null && specialLastFieldInfos.size() == 1, "映射时需要有且仅有一个字段来映射");
                fieldInfos.add(specialLastFieldInfos.get(0));
                List<Map<String, Object>> fieldResults = nodeInvokeResult.getResult();

                Asserts.assertTrue(1 == fieldResults.size() || fieldResults.isEmpty(), "子执行计划的执行结果不唯一");
                if (fieldResults.size() == 1) {
                    Map<String, Object> fieldResultMap = fieldResults.get(0);
                    Object o = fieldResultMap.get(MysqlContent.DEFAULT_RESULT_NAME);
                    resultItem.put(finalName, o);
                } else {
                    resultItem.put(finalName, null);
                }

            } else if (needFieldStr.startsWith("@@") || needField.isGlobal()) {
                fieldInfos.add(new FieldInfo(MysqlContent.DUAL_DATABASES, "dual", "dual", finalName, finalName, 0, 0, FieldTypeEnum.FIELD_TYPE_VARCHAR, (short) 0, (byte) 0));
                String variableName = needFieldStr;
                if (needFieldStr.startsWith("@@")) {
                    variableName = variableName.substring(2);
                }

                if (needField.isGlobal()) {
                    variableName = "global." + variableName;
                }
                Object o = JSONUtil.recursiveMatch(mysqlSystemVariables, variableName);
                resultItem.put(finalName, o);
            } else {
                Boolean allowFault = config.getAllowFault();
                Asserts.assertTrue(allowFault, "不允许错误的sql语句, 在有合并意义的语句中不能出现实际行");

                /*获取对应字段的结果*/
                List<Map<String, Object>> lastResult = this.lastQueryPlanResult.getResult();
                Object newResult = null;
                if (CollectionUtil.isNotEmpty(lastResult)) {
                    Map<String, Object> first = lastResult.get(0);
                    for (Entry<String, Object> entry : first.entrySet()) {
                        if (Objects.equal(StringUtil.cleanQuotation(needFieldStr), StringUtil.cleanQuotation(entry.getKey())) || Objects.equal(needFieldStr, "*")) {
                            newResult = entry.getValue();
                        }
                    }
                }
                resultItem.put(finalName, newResult);
                /*获取对应的字段信息*/
                fieldInfos.add(fieldInfoMap.get(StringUtil.cleanQuotation(needFieldStr)));
            }
        }
        List<Map<String, Object>> result = new ArrayList<>();
        result.add(resultItem);
        NodeInvokeResult nodeInvokeResult = new NodeInvokeResult(this);
        nodeInvokeResult.setFieldInfos(fieldInfos);
        nodeInvokeResult.setResult(result);
        return nodeInvokeResult;
    }


    /**
     * 查询字段
     *
     * @param sourceFieldName 要查询的字段名字
     * @param fieldInfoList   上一个执行计划的结果
     */
    private FieldInfo queryFieldByKey(String sourceFieldName, List<FieldInfo> fieldInfoList) {
        /*查不到的原因: 多个表都有同一个字段名字,但是查询语句中没有指定表*/
        if (sourceFieldName.contains(".")) {
            return fieldInfoList.stream().filter(t -> Objects.equal(t.getTableNameDotFieldName(), sourceFieldName)).findFirst().orElseThrow(() -> Asserts.makeException("未找到字段:{}", sourceFieldName));
        }

        return fieldInfoList.stream().filter(t -> top.uhyils.usher.util.StringUtil.equalsIgnoreCase(t.getFieldName(), sourceFieldName)).findFirst().orElseThrow(() -> Asserts.makeException("未找到字段:{}", sourceFieldName));
    }

    /**
     * 处理新字段 如果有两个相同名称的字段, 则第二个自动变成 xxx(1)
     *
     * @param newFields     新字段们
     * @param lastFieldInfo 上一个字段
     * @param finalName     当前字段需要展示的名字
     */
    private FieldInfo dealLastFieldInfo(List<FieldInfo> newFields, FieldInfo lastFieldInfo, String finalName) {
        List<FieldInfo> collect = newFields.stream().filter(t -> t.getFieldName().equals(finalName)).collect(Collectors.toList());
        // 名称和之前重复了
        if (CollectionUtil.isEmpty(collect)) {
            return lastFieldInfo.copyWithNewFieldName(finalName);
        }
        Integer fieldIndex = StringUtil.subFieldIndex(lastFieldInfo.getFieldName());
        int index;
        if (fieldIndex == null) {
            index = 1;
        } else {
            index = fieldIndex + 1;
        }
        return lastFieldInfo.copyWithNewFieldName(MessageFormat.format("{0}({1})", finalName, index));

    }
}
