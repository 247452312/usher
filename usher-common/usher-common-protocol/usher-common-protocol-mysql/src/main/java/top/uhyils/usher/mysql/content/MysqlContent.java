package top.uhyils.usher.mysql.content;

import io.netty.channel.ChannelId;
import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;
import java.util.concurrent.atomic.AtomicLong;
import top.uhyils.usher.UsherThreadLocal;
import top.uhyils.usher.mysql.pojo.entity.MysqlTcpLink;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2022年03月22日 19时25分
 */
public class MysqlContent {


    /**
     * mysql版本
     */
    public static final String VERSION = "5.7.36";

    /**
     * 协议结尾
     */
    public static final String END_OF_PROTO = "mysql_native_password";

    /**
     * path中的分隔符
     */
    public static final String PATH_SEPARATOR = "/";

    /**
     * 默认库名称
     */
    public static final String CATALOG_NAME = "def";

    public static final String MYSQL_YES = "Y";

    public static final String MYSQL_NO = "N";

    /**
     * 当前连接的mysql信息
     */
    public static final UsherThreadLocal<MysqlTcpLink> MYSQL_TCP_INFO = new UsherThreadLocal<>();


    /**
     * 默认字符集
     */
    public static final String DEFAULT_CHARACTER_SET_NAME = "utf8mb4";

    /**
     * 数据库排序规则
     */
    public static final String DEFAULT_COLLATION_NAME = "utf8mb4_general_ci";

    /**
     * 系统默认的数据库,不允许创建,删除,修改
     */
    public static final List<String> SYS_DATABASE = new ArrayList<>();

    /**
     * 入参构造的表的表名默认值
     */
    public static final String DEFAULT_PARAM_TABLE = "param_table";

    /**
     * 方法结果的表的表名默认值
     */
    public static final String DEFAULT_METHOD_CALL_TABLE = "method_call_table";

    /**
     * dual表对应的db
     */
    public static final String DUAL_DATABASES = "mysql";

    /**
     * 通用中间字段名称
     */
    public static final String DEFAULT_RESULT_NAME = "result";

    /**
     * 全局预处理语句id
     */
    private static final AtomicLong PREPARE_ID = new AtomicLong(0);

    /**
     * mysql的tcp缓存
     */
    private static final WeakHashMap<ChannelId, MysqlTcpLink> mysqlTcpInfoWeakHashMap = new WeakHashMap<>();

    static {
        SYS_DATABASE.add("information_schema");
        SYS_DATABASE.add("mysql");
        SYS_DATABASE.add("performance_schema");
    }

    public static long getAndIncrementPrepareId() {
        return PREPARE_ID.getAndIncrement();
    }

    /**
     * 提交新的tcp连接信息
     *
     * @param channelId
     *
     * @return
     */
    public static MysqlTcpLink putMysqlTcpInfo(ChannelId channelId) {
        return mysqlTcpInfoWeakHashMap.put(channelId, MYSQL_TCP_INFO.get());
    }

    /**
     * 查询新的tcp连接信息
     *
     * @param channelId
     *
     * @return
     */
    public static MysqlTcpLink findMysqlTcpInfo(ChannelId channelId) {
        return mysqlTcpInfoWeakHashMap.put(channelId, MYSQL_TCP_INFO.get());
    }

}
