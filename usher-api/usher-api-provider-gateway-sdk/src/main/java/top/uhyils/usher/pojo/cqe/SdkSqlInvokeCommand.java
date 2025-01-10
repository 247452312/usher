package top.uhyils.usher.pojo.cqe;

import com.alibaba.fastjson.JSONObject;
import java.util.Map;
import top.uhyils.usher.pojo.cqe.command.base.AbstractCommand;

/**
 * 对接中心执行方法command
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2022年08月12日 10时19分
 */
public class SdkSqlInvokeCommand extends AbstractCommand {


    /**
     * 入参
     */
    private JSONObject params;

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

    /**
     * 类型
     */
    private String type;


    /**
     * 修改时的字段
     */
    private Map<String, String> updateItems;

    public JSONObject getParams() {
        return params;
    }

    public void setParams(JSONObject params) {
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, String> getUpdateItems() {
        return updateItems;
    }

    public void setUpdateItems(Map<String, String> updateItems) {
        this.updateItems = updateItems;
    }
}
