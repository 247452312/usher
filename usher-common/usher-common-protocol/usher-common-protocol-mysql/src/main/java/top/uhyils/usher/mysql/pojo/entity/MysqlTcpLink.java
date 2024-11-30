package top.uhyils.usher.mysql.pojo.entity;

import io.netty.channel.ChannelId;
import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import top.uhyils.usher.mysql.content.MysqlContent;
import top.uhyils.usher.mysql.enums.MysqlHandlerStatusEnum;
import top.uhyils.usher.mysql.pojo.DTO.PrepareInfo;
import top.uhyils.usher.pojo.DTO.UserDTO;
import top.uhyils.usher.pojo.entity.base.AbstractEntity;

/**
 * mysql此次tcp请求的连接信息
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2022年03月22日 19时24分
 */
public class MysqlTcpLink extends AbstractEntity<ChannelId> {

    /**
     * 此次tcp预处理语句的本地缓存
     */
    private Map<Long, PrepareInfo> prepareCache = new ConcurrentHashMap<>();

    /**
     * 客户端连接地址
     */
    private InetSocketAddress localAddress;

    /**
     * 默认clientIndex为-1
     */
    private long index = -1;

    /**
     * 创建完成之后默认是初见状态
     */
    private MysqlHandlerStatusEnum status = MysqlHandlerStatusEnum.UNKNOW;

    /**
     * 此次登录的用户
     */
    private UserDTO userDTO;

    /**
     * 密码 随机挑战数
     */
    private byte[] randomByte;


    /**
     * 错误数量
     */
    private int warnCount;

    /**
     * 预处理语句
     */
    private Map<Long, PrepareInfo> prepareSqlMap = new ConcurrentHashMap<>();

    /**
     * 当前所在数据库
     */
    private String database = "MY_DEFAULT_DB";

    public MysqlTcpLink(ChannelId channelId, InetSocketAddress inetSocketAddress) {
        super(channelId);
        this.localAddress = inetSocketAddress;
    }

    /**
     * 快捷创建
     */
    public static MysqlTcpLink build(ChannelId channelId, InetSocketAddress inetSocketAddress) {
        MysqlTcpLink mysqlTcpInfo = MysqlContent.findMysqlTcpInfo(channelId);
        if (mysqlTcpInfo != null) {
            return mysqlTcpInfo;
        }
        return new MysqlTcpLink(channelId, inetSocketAddress);
    }

    /**
     * 快捷创建
     */
    public static MysqlTcpLink findByCache() {
        return MysqlContent.MYSQL_TCP_INFO.get();
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }


    public InetSocketAddress findLocalAddress() {
        return localAddress;
    }


    public long index() {
        return index;
    }


    /**
     * 每次序列号需要+1
     */
    public void addIndex() {
        index++;
    }

    public UserDTO findUserDTO() {
        return userDTO;
    }

    public void fillUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    /**
     * mysql的登录状态往前走一步,未知->初见->登录->结束
     *
     * @return
     */
    public MysqlHandlerStatusEnum getAndIncrementStatus() {
        MysqlHandlerStatusEnum status = status();
        switch (status) {
            case UNKNOW:
                setStatus(MysqlHandlerStatusEnum.FIRST_SIGHT);
                break;
            case FIRST_SIGHT:
                setStatus(MysqlHandlerStatusEnum.PASSED);
                break;
            case OVER:
            case PASSED:
            default:
                break;
        }
        return status;
    }

    public MysqlHandlerStatusEnum status() {
        return status;
    }

    public void setStatus(MysqlHandlerStatusEnum status) {
        this.status = status;
    }

    public byte[] randomByte() {
        return randomByte;
    }

    public void fillRandomByte(byte[] randomByte) {
        this.randomByte = randomByte;
    }

    public int warnCount() {
        return warnCount;
    }

    public PrepareInfo findPrepareSql(Long id) {
        return prepareSqlMap.get(id);
    }

    public long addPrepareSql(PrepareInfo prepareSql) {
        long key = MysqlContent.getAndIncrementPrepareId();
        fillPrepareSql(key, prepareSql);
        return key;
    }

    public void fillPrepareSql(Long id, PrepareInfo prepareSql) {
        this.prepareSqlMap.put(id, prepareSql);
    }

    /**
     * 将自己加入本地缓存
     */
    public void addToLocalCache() {
        MysqlContent.MYSQL_TCP_INFO.set(this);
        // 缓存TCP信息到系统
        MysqlContent.putMysqlTcpInfo(getUnique().get());
    }

    /**
     * 修改当前index为传过来的index
     */
    public void changeIndex(long index) {
        this.index = index;
    }
}
