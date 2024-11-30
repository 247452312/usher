package top.uhyils.usher.mysql.pojo;

import com.alibaba.druid.sql.ast.expr.SQLBinaryOpExpr;
import com.alibaba.druid.sql.ast.statement.SQLExprTableSource;
import com.alibaba.druid.sql.ast.statement.SQLJoinTableSource.JoinType;
import java.util.List;
import top.uhyils.usher.annotation.NotNull;

/**
 * sql中 table的连接树
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2022年03月31日 13时41分
 */
public class SqlTableSourceBinaryTreeInfo {

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
    private SqlTableSourceBinaryTreeInfo leftTree;

    /**
     * 右树
     */
    private SqlTableSourceBinaryTreeInfo rightTree;

    /**
     * 连接方式
     */
    private JoinType joinType;

    /**
     * 连接条件
     */
    private SQLBinaryOpExpr condition;

    public SqlTableSourceBinaryTreeInfo(SQLExprTableSource tableSource, List<SQLBinaryOpExpr> where) {
        this.tableSource = tableSource;
        this.where = where;
    }

    public SqlTableSourceBinaryTreeInfo(SqlTableSourceBinaryTreeInfo leftTree, SqlTableSourceBinaryTreeInfo rightTree, JoinType joinType) {
        this.leftTree = leftTree;
        this.rightTree = rightTree;
        this.joinType = joinType;
    }

    public SqlTableSourceBinaryTreeInfo() {
    }

    public SqlTableSourceBinaryTreeInfo getLeftTree() {
        return leftTree;
    }

    public void setLeftTree(SqlTableSourceBinaryTreeInfo leftTree) {
        this.leftTree = leftTree;
    }

    public SqlTableSourceBinaryTreeInfo getRightTree() {
        return rightTree;
    }

    public void setRightTree(SqlTableSourceBinaryTreeInfo rightTree) {
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
