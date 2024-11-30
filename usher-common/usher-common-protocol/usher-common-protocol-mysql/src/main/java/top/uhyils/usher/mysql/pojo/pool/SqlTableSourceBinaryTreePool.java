package top.uhyils.usher.mysql.pojo.pool;

import com.alibaba.druid.sql.ast.SQLName;
import com.alibaba.druid.sql.ast.expr.SQLBinaryOpExpr;
import com.alibaba.druid.sql.ast.expr.SQLIdentifierExpr;
import com.alibaba.druid.sql.ast.expr.SQLPropertyExpr;
import com.alibaba.druid.sql.ast.statement.SQLExprTableSource;
import com.alibaba.druid.sql.ast.statement.SQLJoinTableSource.JoinType;
import java.util.List;
import top.uhyils.usher.mysql.content.MysqlContent;
import top.uhyils.usher.mysql.pojo.SqlTableSourceBinaryTreeInfo;
import top.uhyils.usher.mysql.pojo.entity.MysqlTcpLink;
import top.uhyils.usher.pool.AbstractObjectPool;

/**
 * sql实例池
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2022年03月31日 13时57分
 */
public class SqlTableSourceBinaryTreePool extends AbstractObjectPool<SqlTableSourceBinaryTreeInfo> {

    /**
     * 默认实例长度
     */
    private static final Integer DEFAULT_SIZE = 1000;


    public SqlTableSourceBinaryTreePool(Integer size) {
        super(size, SqlTableSourceBinaryTreeInfo.class);
    }


    public SqlTableSourceBinaryTreeInfo getOrCreateObject(SQLExprTableSource tableSource, List<SQLBinaryOpExpr> where) {
        SqlTableSourceBinaryTreeInfo orCreateObject = super.getOrCreateObject();
        MysqlTcpLink mysqlTcpLink = MysqlContent.MYSQL_TCP_INFO.get();
        SQLName name = tableSource.getName();

        String owner = null;
        if (name instanceof SQLPropertyExpr) {
            SQLPropertyExpr sqlPropertyExpr = (SQLPropertyExpr) name;
            owner = sqlPropertyExpr.getOwner() != null ? sqlPropertyExpr.getOwnernName() : mysqlTcpLink.getDatabase();
        } else if (name instanceof SQLIdentifierExpr) {
            owner = mysqlTcpLink.getDatabase();
        }

        orCreateObject.setTableSource(new SQLExprTableSource(new SQLPropertyExpr(owner, name.getSimpleName()), tableSource.getAlias()));
        orCreateObject.setWhere(where);
        return orCreateObject;
    }

    public SqlTableSourceBinaryTreeInfo getOrCreateObject(SqlTableSourceBinaryTreeInfo leftTree, SqlTableSourceBinaryTreeInfo rightTree, SQLBinaryOpExpr condition, JoinType joinType) {
        SqlTableSourceBinaryTreeInfo orCreateObject = super.getOrCreateObject();
        orCreateObject.setLeftTree(leftTree);
        orCreateObject.setRightTree(rightTree);
        orCreateObject.setJoinType(joinType);
        orCreateObject.setCondition(condition);
        return orCreateObject;
    }

    @Override
    protected void emptyObj(SqlTableSourceBinaryTreeInfo sqlTableSourceBinaryTreeInfo) {
        sqlTableSourceBinaryTreeInfo.setLeftTree(null);
        sqlTableSourceBinaryTreeInfo.setRightTree(null);
        sqlTableSourceBinaryTreeInfo.setJoinType(null);
        sqlTableSourceBinaryTreeInfo.setTableSource(null);
    }
}
