package team.opentech.usher.plan.pojo;

import com.alibaba.druid.sql.ast.expr.SQLBinaryOpExpr;
import com.alibaba.druid.sql.ast.statement.SQLExprTableSource;
import com.alibaba.druid.sql.ast.statement.SQLJoinTableSource.JoinType;
import team.opentech.usher.annotation.NotNull;
import java.util.List;

/**
 * sql中 table的连接树
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2022年03月31日 13时41分
 */
public class SqlTableSourceBinaryTree {

    /**
     * 节点数据本身
     */
    private SQLExprTableSource tableSource;

    /**
     * 条件
     */
    private List<SQLBinaryOpExpr> where;

    /**
     * 左树
     */
    private SqlTableSourceBinaryTree leftTree;

    /**
     * 右树
     */
    private SqlTableSourceBinaryTree rightTree;

    /**
     * 连接方式
     */
    private JoinType joinType;

    /**
     * 连接条件
     */
    private SQLBinaryOpExpr condition;

    public SqlTableSourceBinaryTree(SQLExprTableSource tableSource, List<SQLBinaryOpExpr> where) {
        this.tableSource = tableSource;
        this.where = where;
    }

    public SqlTableSourceBinaryTree(SqlTableSourceBinaryTree leftTree, SqlTableSourceBinaryTree rightTree, JoinType joinType) {
        this.leftTree = leftTree;
        this.rightTree = rightTree;
        this.joinType = joinType;
    }

    public SqlTableSourceBinaryTree() {
    }

    public SqlTableSourceBinaryTree getLeftTree() {
        return leftTree;
    }

    public void setLeftTree(SqlTableSourceBinaryTree leftTree) {
        this.leftTree = leftTree;
    }

    public SqlTableSourceBinaryTree getRightTree() {
        return rightTree;
    }

    public void setRightTree(SqlTableSourceBinaryTree rightTree) {
        this.rightTree = rightTree;
    }

    public JoinType getJoinType() {
        return joinType;
    }

    public void setJoinType(JoinType joinType) {
        this.joinType = joinType;
    }

    public SQLExprTableSource getTableSource() {
        return tableSource;
    }

    public void setTableSource(SQLExprTableSource tableSource) {
        this.tableSource = tableSource;
    }

    public List<SQLBinaryOpExpr> getWhere() {
        return where;
    }

    public void setWhere(List<SQLBinaryOpExpr> where) {
        this.where = where;
    }

    public SQLBinaryOpExpr getCondition() {
        return condition;
    }

    public void setCondition(SQLBinaryOpExpr condition) {
        this.condition = condition;
    }

    @NotNull
    public Boolean isLevel() {
        return leftTree == null && rightTree == null;
    }
}