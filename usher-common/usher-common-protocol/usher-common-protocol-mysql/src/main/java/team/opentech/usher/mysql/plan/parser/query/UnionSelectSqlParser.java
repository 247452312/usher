package team.opentech.usher.mysql.plan.parser.query;

import com.alibaba.druid.sql.ast.statement.SQLSelectQuery;
import com.alibaba.druid.sql.ast.statement.SQLSelectStatement;
import com.alibaba.druid.sql.ast.statement.SQLUnionQuery;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import team.opentech.usher.mysql.plan.MysqlPlan;
import team.opentech.usher.mysql.plan.PlanFactory;

/**
 * union解释器
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年11月20日 11时17分
 */
@Component
public class UnionSelectSqlParser extends AbstractSelectSqlParser {

    @Resource
    private PlanFactory planFactory;

    @Override
    public List<MysqlPlan> doParse(SQLSelectStatement sql, Map<String, String> headers) {

        SQLUnionQuery query = (SQLUnionQuery) sql.getSelect().getQuery();
        List<SQLSelectQuery> relations = query.getRelations();
        relations = parseToNoUnion(relations);
        List<List<MysqlPlan>> relationResultList = new ArrayList<>();
        /*将union子部分分裂为单个语句,单独查询*/
        for (SQLSelectQuery relation : relations) {
            List<MysqlPlan> relationMysqlPlans = reExecute(relation.toString(), headers, mysqlPlans -> mysqlPlans);
            relationResultList.add(relationMysqlPlans);
        }
        List<Long> planIds = relationResultList.stream().map(t -> t.get(t.size() - 1)).map(MysqlPlan::getId).collect(Collectors.toList());
        List<MysqlPlan> result = relationResultList.stream().flatMap(Collection::stream).collect(Collectors.toList());
        /*添加组合结果的plan*/
        result.add(planFactory.buildUnionSelectSqlPlan(headers, planIds));
        return result;
    }

    @Override
    protected boolean doCanParse(SQLSelectStatement sql) {
        SQLSelectQuery query = sql.getSelect().getQuery();
        if (query instanceof SQLUnionQuery) {
            return true;
        }

        return false;
    }

    /**
     * 解析为没有union的语句
     *
     * @param relations
     *
     * @return
     */
    private List<SQLSelectQuery> parseToNoUnion(List<SQLSelectQuery> relations) {
        List<SQLSelectQuery> results = new ArrayList<>();
        for (SQLSelectQuery relation : relations) {
            if (relation instanceof SQLUnionQuery) {
                SQLUnionQuery sqlUnionQuery = (SQLUnionQuery) relation;
                List<SQLSelectQuery> unionQueryRelations = sqlUnionQuery.getRelations();
                List<SQLSelectQuery> sqlSelectQueries = parseToNoUnion(unionQueryRelations);
                results.addAll(sqlSelectQueries);
            } else {
                results.add(relation);
            }
        }
        return results;
    }


}
