package team.opentech.usher.mysql.handler;

import team.opentech.usher.mysql.content.MysqlContent;
import team.opentech.usher.mysql.enums.MysqlHandlerStatusEnum;
import team.opentech.usher.pojo.DTO.UserDTO;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * mysql此次tcp请求的连接信息
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2022年03月22日 19时24分
 */
public class MysqlTcpInfo implements Serializable {

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

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public PrepareInfo getPrepareCache(long key) {
        return prepareCache.get(key);
    }

    public void setPrepareCache(long key, PrepareInfo prepareInfo) {
        this.prepareCache.put(key, prepareInfo);
    }

    public InetSocketAddress getLocalAddress() {
        return localAddress;
    }

    public void setLocalAddress(InetSocketAddress localAddress) {
        this.localAddress = localAddress;
    }

    public long index() {
        return index;
    }

    /**
     * 修改当前index为传过来的index
     *
     * @param index
     */
    public void changeIndexToClientIndex(long index) {
        this.index = index;

    }

    public void addIndex() {
        index++;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public MysqlHandlerStatusEnum getAndIncrementStatus() {
        MysqlHandlerStatusEnum status = getStatus();
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

    public MysqlHandlerStatusEnum getStatus() {
        return status;
    }

    public void setStatus(MysqlHandlerStatusEnum status) {
        this.status = status;
    }

    public byte[] getRandomByte() {
        return randomByte;
    }

    public void setRandomByte(byte[] randomByte) {
        this.randomByte = randomByte;
    }

    public void addWarnCount() {
        warnCount++;
    }

    public int warnCount() {
        return warnCount;
    }

    public PrepareInfo getPrepareSql(Long id) {
        return prepareSqlMap.get(id);
    }

    public long addPrepareSql(PrepareInfo prepareSql) {
        long key = MysqlContent.getAndIncrementPrepareId();
        setPrepareSql(key, prepareSql);
        return key;
    }

    public void setPrepareSql(Long id, PrepareInfo prepareSql) {
        this.prepareSqlMap.put(id, prepareSql);
    }
}