package top.uhyils.usher.mysql.pojo.sql;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.expr.SQLVariantRefExpr;
import com.alibaba.druid.sql.ast.statement.SQLSelectItem;
import top.uhyils.usher.mysql.enums.MysqlMethodEnum;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年08月01日 11时50分
 */
public class MySQLSelectItem extends SQLSelectItem {

    /**
     * 原始selectItem
     */
    private final SQLSelectItem selectItem;

    /**
     * 对应方法名称
     */
    private MysqlMethodEnum methodEnum;

    public MySQLSelectItem(SQLExpr expr, String alias, SQLSelectItem selectItem) {
        super(expr, alias);
        this.selectItem = selectItem;
    }

    public MySQLSelectItem(SQLExpr expr, String alias, SQLSelectItem selectItem, MysqlMethodEnum methodEnum) {
        this(expr, alias, selectItem);
        this.methodEnum = methodEnum;
    }

    /**
     * 是否是方法对应的item
     *
     * @return
     */
    public Boolean isMethodItem() {
        return methodEnum != null;
    }

    public SQLSelectItem originalSelectItem() {
        return selectItem;
    }

    /**
     * 获取对应方法类型
     *
     * @return
     */
    public MysqlMethodEnum method() {
        return methodEnum;
    }

    public Boolean isGlobal() {
        SQLExpr selectItemExpr = selectItem.getExpr();
        if (selectItemExpr instanceof SQLVariantRefExpr) {
            return ((SQLVariantRefExpr) selectItemExpr).isGlobal();
        }
        return false;
    }


}
