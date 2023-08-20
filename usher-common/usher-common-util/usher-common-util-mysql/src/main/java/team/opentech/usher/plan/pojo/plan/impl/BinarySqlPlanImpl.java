package team.opentech.usher.plan.pojo.plan.impl;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.expr.SQLBinaryOpExpr;
import com.alibaba.druid.sql.ast.expr.SQLBinaryOperator;
import com.alibaba.druid.sql.ast.expr.SQLCharExpr;
import com.alibaba.druid.sql.ast.expr.SQLNumberExpr;
import com.alibaba.druid.sql.dialect.mysql.ast.expr.MySqlCharExpr;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import team.opentech.usher.mysql.enums.FieldTypeEnum;
import team.opentech.usher.mysql.pojo.DTO.FieldInfo;
import team.opentech.usher.mysql.pojo.DTO.NodeInvokeResult;
import team.opentech.usher.plan.pojo.plan.BinarySqlPlan;

/**
 * 表达式执行计划
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年08月20日 13时13分
 */
public class BinarySqlPlanImpl extends BinarySqlPlan {

    public BinarySqlPlanImpl(Map<String, String> headers, SQLExpr leftExpr, SQLBinaryOperator operator, SQLExpr rightExpr) {
        super(null, headers, leftExpr, operator, rightExpr);
    }

    @Override
    public NodeInvokeResult invoke() {
        NodeInvokeResult nodeInvokeResult = new NodeInvokeResult(this);
        List<FieldInfo> fieldInfos = new ArrayList<>();
        FieldTypeEnum fieldTypeVarchar = FieldTypeEnum.FIELD_TYPE_VARCHAR;

        fieldInfos.add(new FieldInfo("information_schema", "tables", "tables", "result", "result", 0, 1, fieldTypeVarchar, (short) 0, (byte) 0));
        nodeInvokeResult.setFieldInfos(fieldInfos);
        List<Map<String, Object>> result = new ArrayList<>();
        Map<String, Object> e = new HashMap<>();
        e.put("result", "");
        result.add(e);
        nodeInvokeResult.setResult(result);

        return nodeInvokeResult;
    }
}
