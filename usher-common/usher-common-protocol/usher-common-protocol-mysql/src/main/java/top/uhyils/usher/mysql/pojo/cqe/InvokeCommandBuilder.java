package top.uhyils.usher.mysql.pojo.cqe;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import top.uhyils.usher.util.StringUtil;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2022年08月15日 09时19分
 */
public class InvokeCommandBuilder {

    private static final String SPLIT_DOT = ".";

    /**
     * 参数
     */
    private final Map<String, Object> params = new HashMap<>();

    /**
     * header
     */
    private final Map<String, String> header = new HashMap<>();

    /**
     * 库名称
     */
    private String database;

    /**
     * 表名称
     */
    private String table;


    /**
     * 别名
     */
    private String alias;

    public InvokeCommandBuilder() {
    }

    /**
     * 添加post参数
     *
     * @param postMap
     *
     * @return
     */
    public InvokeCommandBuilder addPostMap(Map<String, Object> postMap) {
        params.putAll(postMap);
        return this;
    }

    /**
     * 添加get参数
     *
     * @param getMap
     *
     * @return
     */
    public InvokeCommandBuilder addGetMap(Map<String, String[]> getMap) {
        Map<String, Object> result = new HashMap<>();
        for (Entry<String, String[]> stringEntry : getMap.entrySet()) {
            String key = stringEntry.getKey();
            String[] value = stringEntry.getValue();
            if (value.length == 1) {
                result.put(key, value[0]);
            } else {
                result.put(key, value);
            }
        }
        params.putAll(result);
        return this;
    }

    /**
     * 添加table
     *
     * @param tableName
     *
     * @return
     */
    public InvokeCommandBuilder fillTable(String tableName) {
        this.table = tableName;
        return this;
    }

    /**
     * 添加库
     *
     * @param database
     *
     * @return
     */
    public InvokeCommandBuilder fillDatabase(String database) {
        this.database = database;
        return this;
    }

    /**
     * 添加自定义参数
     *
     * @param path mysql调用过来的形式
     *
     * @return
     */
    public InvokeCommandBuilder addArgs(Map<String, Object> path) {
        this.params.putAll(path);
        return this;
    }

    /**
     * 添加header参数
     *
     * @param header
     *
     * @return
     */
    public InvokeCommandBuilder addHeader(Map<String, String> header) {
        this.header.putAll(header);
        return this;
    }


    /**
     * 构建command
     *
     * @return
     */
    public MysqlInvokeCommand build() {
        delParamAlias();
        MysqlInvokeCommand invokeCommand = new MysqlInvokeCommand();
        invokeCommand.setHeader(this.header);
        invokeCommand.setParams(this.params);
        invokeCommand.setDatabase(this.database);
        invokeCommand.setTable(this.table);
        return invokeCommand;
    }

    public void addAlias(String alias) {
        this.alias = alias;
    }

    /**
     * 删除入参中的alias
     */
    private void delParamAlias() {
        if (StringUtil.isEmpty(this.alias)) {
            return;
        }
        String s = this.alias + SPLIT_DOT;
        Map<String, Object> result = new HashMap<>(8);
        for (Entry<String, Object> entry : params.entrySet()) {
            String key = entry.getKey();
            if (key.startsWith(s)) {
                String newKey = key.substring(s.length());
                result.put(newKey, entry.getValue());
                continue;
            }
            result.put(key, entry.getValue());
        }
        this.params.clear();
        this.params.putAll(result);
    }
}
