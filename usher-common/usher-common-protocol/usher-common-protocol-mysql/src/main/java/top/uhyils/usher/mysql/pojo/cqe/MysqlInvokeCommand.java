package top.uhyils.usher.mysql.pojo.cqe;

import java.util.Map;
import top.uhyils.usher.pojo.cqe.command.base.AbstractCommand;

/**
 * 对接中心执行方法command
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2022年08月12日 10时19分
 */
public class MysqlInvokeCommand extends AbstractCommand {


    /**
     * 入参
     */
    private Map<String, Object> params;

    /**
     * 请求头
     */
    private Map<String, String> header;


    /**
     * 库名称
     */
    private String database;

    /**
     * 表名称
     */
    private String table;


    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public Map<String, String> getHeader() {
        return header;
    }

    public void setHeader(Map<String, String> header) {
        this.header = header;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }
}
