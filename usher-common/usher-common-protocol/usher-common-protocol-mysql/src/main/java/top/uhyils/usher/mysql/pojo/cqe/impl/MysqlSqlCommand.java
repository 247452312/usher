package top.uhyils.usher.mysql.pojo.cqe.impl;


import top.uhyils.usher.mysql.pojo.cqe.AbstractMysqlCommand;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2022年03月24日 12时38分
 */
public abstract class MysqlSqlCommand extends AbstractMysqlCommand {

    /**
     * 对应的sql语句
     */
    protected String sql;

    protected MysqlSqlCommand(byte[] mysqlBytes) {
        super(mysqlBytes);
    }

    public String getSql() {
        return sql;
    }

}
