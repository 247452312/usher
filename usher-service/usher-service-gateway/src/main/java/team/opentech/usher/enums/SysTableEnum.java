package team.opentech.usher.enums;

import com.google.common.base.Function;
import java.util.Map;
import team.opentech.usher.annotation.NotNull;
import team.opentech.usher.mysql.util.MysqlUtil;
import team.opentech.usher.pojo.entity.sys.ICollatinos;
import team.opentech.usher.pojo.entity.sys.IColumns;
import team.opentech.usher.pojo.entity.sys.IEngines;
import team.opentech.usher.pojo.entity.sys.IParameters;
import team.opentech.usher.pojo.entity.sys.IProfiling;
import team.opentech.usher.pojo.entity.sys.IRoutines;
import team.opentech.usher.pojo.entity.sys.ISchemata;
import team.opentech.usher.pojo.entity.sys.ITables;
import team.opentech.usher.pojo.entity.sys.IView;
import team.opentech.usher.pojo.entity.sys.MDual;
import team.opentech.usher.pojo.entity.sys.MUser;
import team.opentech.usher.pojo.entity.sys.PGlobalVariables;
import team.opentech.usher.pojo.entity.sys.SysTable;
import team.opentech.usher.util.Asserts;
import team.opentech.usher.util.StringUtil;

/**
 * 系统表枚举
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2022年09月01日 13时46分
 */
public enum SysTableEnum {
    /**
     * 库表元数据存储表
     */
    INFORMATION_SCHEMA_SCHEMATA("information_schema", "schemata", ISchemata::new),
    /**
     * 表元数据存储
     */
    INFORMATION_SCHEMA_TABLES("information_schema", "tables", ITables::new),
    /**
     * 列信息
     */
    INFORMATION_SCHEMA_COLUMNS("information_schema", "columns", IColumns::new),
    /**
     * 视图信息
     */
    INFORMATION_SCHEMA_PARAMETERS("information_schema", "parameters", IParameters::new),
    /**
     * 存储子程序（存储程序和函数）的信息
     */
    INFORMATION_SCHEMA_ROUTINES("information_schema", "routines", IRoutines::new),
    /**
     * 存储引擎相关的信息
     */
    INFORMATION_SCHEMA_ENGINES("information_schema", "engines", IEngines::new),
    /**
     * 视图相关的信息
     */
    INFORMATION_SCHEMA_VIEW("information_schema", "views", IView::new),
    /**
     * 可以用来分析每一条SQL在它执行的各个阶段的用时
     */
    INFORMATION_SCHEMA_PROFILING("information_schema", "profiling", IProfiling::new),
    /**
     * 排序规则
     */
    INFORMATION_SCHEMA_COLLATIONS("information_schema", "collations", ICollatinos::new),
    /**
     * 系统参数
     */
    PERFORMANCE_SCHEMA_GLOBAL_VARIABLES("performance_schema", "global_variables", PGlobalVariables::new),
    /**
     * 用户参数表
     */
    MYSQL_USER("mysql", "user", MUser::new),
    /**
     * 系统参数
     */
    MYSQL_DUAL("mysql", "dual", MDual::new),
    ;

    /**
     * 库名称
     */
    private final String database;

    /**
     * 表名
     */
    private final String table;

    /**
     * 创建新SysTable的方法
     */
    private final Function<Map<String, Object>, SysTable> newSysTable;

    SysTableEnum(String database, String table, Function<Map<String, Object>, SysTable> newSysTable) {
        this.database = database;
        this.table = table;
        this.newSysTable = newSysTable;
    }

    /**
     * @param database
     * @param table
     *
     * @return
     */
    @NotNull
    public static SysTableEnum parse(String database, String table) {
        for (SysTableEnum value : values()) {
            if (StringUtil.equalsIgnoreCase(value.database, database)) {
                if (StringUtil.equalsIgnoreCase(value.table, table) || MysqlUtil.equalsIgnoreCaseAndQuotes(value.table, table)) {
                    return value;
                }
            }
        }
        throw Asserts.makeException("未定义此系统表:{},{}", database, table);
    }

    public String getDatabase() {
        return database;
    }

    public String getTable() {
        return table;
    }

    /**
     * 获取系统表查询类
     *
     * @param params 入参
     *
     * @return
     */
    @NotNull
    public SysTable getSysTable(Map<String, Object> params) {
        return newSysTable.apply(params);
    }
}
