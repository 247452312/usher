package top.uhyils.usher.mysql.pojo.cqe.impl;

import java.util.Collections;
import java.util.List;
import top.uhyils.usher.mysql.content.MysqlContent;
import top.uhyils.usher.mysql.enums.ClientPowerEnum;
import top.uhyils.usher.mysql.enums.MysqlCommandTypeEnum;
import top.uhyils.usher.mysql.handler.MysqlServiceHandler;
import top.uhyils.usher.mysql.pojo.cqe.AbstractMysqlCommand;
import top.uhyils.usher.mysql.pojo.entity.MysqlTcpLink;
import top.uhyils.usher.mysql.pojo.response.MysqlResponse;
import top.uhyils.usher.mysql.util.MysqlUtil;
import top.uhyils.usher.mysql.util.Proto;
import top.uhyils.usher.util.Asserts;
import top.uhyils.usher.util.SpringUtil;
import top.uhyils.usher.util.StringUtil;


/**
 * 客户端登录请求
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年11月03日 15时03分
 */
public class MysqlAuthCommand extends AbstractMysqlCommand {


    /**
     * 对接平台服务
     */
    private final MysqlServiceHandler service;

    /**
     * 客户端权能标志
     */
    private long abilityFlags;


    /**
     * 最大消息长度
     */
    private long maxMsgSize;

    /**
     * 字符编码
     */
    private long charset;

    /**
     * 用户名
     */
    private String username;

    /**
     * 挑战认证数据
     */
    private long challengeLength;

    /**
     * 挑战认证数据
     */
    private String challenge;

    /**
     * 数据库名称
     */
    private String dbName;

    /**
     * 序列id
     */
    private long sequenceId;

    /**
     * 插件名称 todo 未有效
     */
    private String pluginName;


    public MysqlAuthCommand(byte[] mysqlBytes) {
        super(mysqlBytes);
        this.service = SpringUtil.getBean(MysqlServiceHandler.class);
    }

    @Override
    public List<MysqlResponse> invoke() {
        return Collections.singletonList(service.mysqlLogin(this));
    }

    @Override
    public MysqlCommandTypeEnum type() {
        return null;
    }

    public long getAbilityFlags() {
        return abilityFlags;
    }

    public void setAbilityFlags(long abilityFlags) {
        this.abilityFlags = abilityFlags;
    }

    public long getMaxMsgSize() {
        return maxMsgSize;
    }

    public void setMaxMsgSize(long maxMsgSize) {
        this.maxMsgSize = maxMsgSize;
    }

    public long getCharset() {
        return charset;
    }

    public void setCharset(long charset) {
        this.charset = charset;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getChallengeLength() {
        return challengeLength;
    }

    public void setChallengeLength(long challengeLength) {
        this.challengeLength = challengeLength;
    }

    public String getChallenge() {
        return challenge;
    }

    public void setChallenge(String challenge) {
        this.challenge = challenge;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public long getSequenceId() {
        return sequenceId;
    }

    public void setSequenceId(long sequenceId) {
        this.sequenceId = sequenceId;
    }

    public String getPluginName() {
        return pluginName;
    }

    public void setPluginName(String pluginName) {
        this.pluginName = pluginName;
    }

    @Override
    protected void load() {
        Proto proto = new Proto(mysqlBytes, 3);
        this.sequenceId = proto.getFixedInt(1);
        this.abilityFlags = proto.getFixedInt(4);
        this.maxMsgSize = proto.getFixedInt(4);
        this.charset = proto.getFixedInt(1);
        // 32位填充值
        proto.get_filler(23);
        Asserts.assertTrue(proto.has_remaining_data(), "暂不支持mysql的SSL连接,请在连接串后添加<useSSL=false>");
        this.username = proto.get_null_str();

        this.challengeLength = proto.get_lenenc_int();
        this.challenge = proto.get_fixed_str(this.challengeLength, true);

        if (MysqlUtil.hasAbility(this.abilityFlags, ClientPowerEnum.CLIENT_CONNECT_WITH_DB.getCode())) {
            this.dbName = proto.get_null_str();
            if (StringUtil.isNotEmpty(dbName)) {
                MysqlTcpLink mysqlTcpLink = MysqlContent.MYSQL_TCP_INFO.get();
                mysqlTcpLink.setDatabase(dbName);
            }
        }

        if (MysqlUtil.hasAbility(this.abilityFlags, ClientPowerEnum.CLIENT_SECURE_CONNECTION.getCode())) {
            this.pluginName = proto.get_null_str();
        }
    }
}
