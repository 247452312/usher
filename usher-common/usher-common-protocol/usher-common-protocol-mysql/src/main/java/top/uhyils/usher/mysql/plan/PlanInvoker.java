package top.uhyils.usher.mysql.plan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;
import top.uhyils.usher.mysql.content.MysqlContent;
import top.uhyils.usher.mysql.enums.FieldTypeEnum;
import top.uhyils.usher.mysql.pojo.DTO.FieldInfo;
import top.uhyils.usher.mysql.pojo.DTO.NodeInvokeResult;
import top.uhyils.usher.mysql.pojo.entity.MysqlTcpLink;
import top.uhyils.usher.util.Asserts;
import top.uhyils.usher.util.CollectionUtil;
import top.uhyils.usher.util.MapUtil;

/**
 * 执行计划者,本身带有工具意义,并不是一个完整的领域
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2022年03月29日 08时38分
 */
public class PlanInvoker {


    private final List<MysqlPlan> plan;

    public PlanInvoker(List<MysqlPlan> plan) {
        this.plan = plan;
    }

    /**
     * 执行执行计划
     *
     * @return 每个执行计划的结果 key->执行计划id value->执行计划执行结果
     */
    public NodeInvokeResult execute() {
        return execute(new HashMap<>(16));
    }

    /**
     * 执行 执行计划
     *
     * @param params 入参 正常格式 key为字段名称 value为对应字段值
     *
     * @return 每个执行计划的结果
     */
    public NodeInvokeResult execute(Map<String, Object> params) {
        // 初始化参数
        Map<Long, NodeInvokeResult> planParamMap = makeFirstParam(params);

        NodeInvokeResult lastResult = null;
        // 补全并执行
        for (MysqlPlan mysqlPlan : plan) {
            mysqlPlan.complete(planParamMap);
            NodeInvokeResult invoke = mysqlPlan.invoke();
            lastResult = invoke;
            planParamMap.put(mysqlPlan.getId(), invoke);
        }
        return lastResult;
    }

    /**
     * 制作伪装为id为-1的执行计划执行结果
     *
     * @param params
     *
     * @return
     */
    @NotNull
    private Map<Long, NodeInvokeResult> makeFirstParam(Map<String, Object> params) {
        Map<Long, NodeInvokeResult> planParamMap = new HashMap<>();
        if (MapUtil.isNotEmpty(params)) {
            List<Map<String, Object>> value = new ArrayList<>();
            value.add(params);
            planParamMap.put(-1L, paramsToResult(value));
        }
        return planParamMap;
    }

    /**
     * 由于执行计划的规则为将此执行计划之前的所有执行计划的结果作为入参 所以此处的作用为:
     * <p>
     * 将入参 伪装为 id为-1的执行计划的执行结果来作为执行计划链条的起点入参
     *
     * @return
     */
    private NodeInvokeResult paramsToResult(List<Map<String, Object>> params) {
        if (CollectionUtil.isEmpty(params)) {
            return new NodeInvokeResult(null);
        }
        NodeInvokeResult nodeInvokeResult = new NodeInvokeResult(null);

        LinkedList<FieldInfo> fieldInfos = new LinkedList<>();

        List<String> fields = params.stream().flatMap(t -> t.keySet().stream()).distinct().collect(Collectors.toList());
        Map<String, Object> firstParam = params.get(0);
        MysqlTcpLink mysqlTcpLink = MysqlContent.MYSQL_TCP_INFO.get();
        first:
        for (int i = 0; i < fields.size(); i++) {
            String field = fields.get(i);
            if (firstParam.containsKey(field)) {
                FieldInfo fieldInfo = FieldTypeEnum.makeFieldInfo(mysqlTcpLink.getDatabase(), MysqlContent.DEFAULT_PARAM_TABLE, MysqlContent.DEFAULT_PARAM_TABLE, firstParam.get(i), i, field);
                fieldInfos.add(fieldInfo);
            } else {
                for (Map<String, Object> param : params) {
                    if (param.containsKey(field)) {
                        FieldInfo fieldInfo = FieldTypeEnum.makeFieldInfo(mysqlTcpLink.getDatabase(), MysqlContent.DEFAULT_PARAM_TABLE, MysqlContent.DEFAULT_PARAM_TABLE, firstParam.get(i), i, field);
                        fieldInfos.add(fieldInfo);
                        continue first;
                    }
                }
                Asserts.throwException("未找到指定类的类型:{}", field);
            }
        }
        nodeInvokeResult.setFieldInfos(fieldInfos);
        nodeInvokeResult.setResult(params);
        return nodeInvokeResult;
    }


}
