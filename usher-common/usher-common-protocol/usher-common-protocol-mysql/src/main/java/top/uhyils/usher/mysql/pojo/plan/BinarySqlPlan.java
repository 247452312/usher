package top.uhyils.usher.mysql.pojo.plan;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.expr.SQLBinaryOperator;
import java.util.HashMap;
import java.util.Map;
import top.uhyils.usher.mysql.plan.AbstractMysqlSqlPlan;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年08月20日 13时14分
 */
public abstract class BinarySqlPlan extends AbstractMysqlSqlPlan {

    protected final SQLExpr leftExpr;

    protected final SQLBinaryOperator operator;

    protected final SQLExpr rightExpr;

    protected BinarySqlPlan(String sql, Map<String, String> headers, SQLExpr leftExpr, SQLBinaryOperator operator, SQLExpr rightExpr) {
        super(sql, headers, new HashMap<>());
        this.leftExpr = leftExpr;
        this.operator = operator;
        this.rightExpr = rightExpr;
    }


}
