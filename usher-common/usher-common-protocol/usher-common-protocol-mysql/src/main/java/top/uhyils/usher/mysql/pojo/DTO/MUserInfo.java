package top.uhyils.usher.mysql.pojo.DTO;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户表
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年08月23日 11时10分
 */
public class MUserInfo implements Serializable {

    /**
     * 主机名
     */
    private String host;

    /**
     * 用户名
     */
    private String user;

    /**
     * 是否有select权限
     */
    private String selectPriv;

    /**
     * 是否有insert权限
     */
    private String insertPriv;

    /**
     * 是否有update权限
     */
    private String updatePriv;

    /**
     * 是否有delete权限
     */
    private String deletePriv;

    /**
     * 是否有create权限
     */
    private String createPriv;

    /**
     * 是否有drop权限
     */
    private String dropPiv;

    /**
     * 是否有reload权限
     */
    private String reloadPriv;

    /**
     * 是否有shutdown权限
     */
    private String shutdownPriv;

    /**
     * 是否有process权限
     */
    private String processPriv;

    /**
     * 是否有file权限
     */
    private String filePriv;

    /**
     * 是否有grant权限
     */
    private String grantPriv;

    /**
     * 是否有references权限
     */
    private String referencesPriv;


    /**
     * 是否有index权限
     */
    private String indexPriv;

    /**
     * 是否有alter权限
     */
    private String alterPriv;

    /**
     * 是否有showDb权限
     */
    private String showDbPriv;

    /**
     * 是否有super权限
     */
    private String superPriv;

    /**
     * 是否有createTmpTable权限
     */
    private String createTmpTablePriv;

    /**
     * 是否有lockTables权限
     */
    private String lockTablesPriv;

    /**
     * 是否有execute权限
     */
    private String executePriv;

    /**
     * 是否有replSlave权限
     */
    private String replSlavePriv;

    /**
     * 是否有replClient权限
     */
    private String replClientPriv;

    /**
     * 是否有createView权限
     */
    private String createViewPriv;

    /**
     * 是否有showView权限
     */
    private String showViewPriv;

    /**
     * 是否有createRoutine权限
     */
    private String createRoutinePriv;

    /**
     * 是否有alterRoutine权限
     */
    private String alterRoutinePriv;

    /**
     * 是否有createUser权限
     */
    private String createUserPriv;

    /**
     * 是否有event权限
     */
    private String eventPriv;

    /**
     * 是否有trigger权限
     */
    private String triggerPriv;

    /**
     * 是否有createTablespace权限
     */
    private String createTablespacePriv;

    /**
     * 支持ssl标准加密安全字段
     */
    private String sslType;

    /**
     * ssl标准加密安全字段
     */
    private String sslCipher;

    /**
     * 支持x509标准字段
     */
    private String x509Issuer;

    /**
     * 支持x509标准字段
     */
    private String x509Subject;

    /**
     * 每小时允许执行多少次查询
     */
    private Integer maxQuestions;

    /**
     * 每小时可以执行多少次更新  ：0表示无限制
     */
    private Integer maxUpdates;

    /**
     * 每小时可以建立的多少次连接：0表示无限制
     */
    private Integer maxConnections;

    /**
     * 单用户可以同时具有的连接数：0表示无限制
     */
    private Integer maxUserConnections;

    /**
     * 5.5.7开始,mysql引入plugins以进行用户连接时的密码验证,plugin创建外部/代理用户(不好意思 我们不支持)
     */
    private String plugin;

    /**
     * 加密后密码
     */
    private String authenticationString;

    /**
     * 密码过期 Y,说明该用户密码已过期 N相反
     */
    private String passwordExpired;

    /**
     * 记录密码最近修改时间
     */
    private LocalDateTime passwordLastChanged;

    /**
     * 密码有效时间 单位为天
     */
    private Integer passwordLifetime;

    /**
     * 用户是否被锁定  Y 被锁定
     */
    private String accountLocked;

    /**
     * 是否有createRole权限
     */
    private String createRolePriv;

    /**
     * 是否有dropRole权限
     */
    private String dropRolePriv;

    /**
     * 密码历史修改次数????
     */
    private Integer passwordReuseHistory;

    /**
     * 密码上次修改时间
     */
    private Integer passwordReuseTime;

    /**
     * 改密码是否需要旧密码
     */
    private String passwordRequireCurrent;

    private String userAttributes;


    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getSelectPriv() {
        return selectPriv;
    }

    public void setSelectPriv(String selectPriv) {
        this.selectPriv = selectPriv;
    }

    public String getInsertPriv() {
        return insertPriv;
    }

    public void setInsertPriv(String insertPriv) {
        this.insertPriv = insertPriv;
    }

    public String getUpdatePriv() {
        return updatePriv;
    }

    public void setUpdatePriv(String updatePriv) {
        this.updatePriv = updatePriv;
    }

    public String getDeletePriv() {
        return deletePriv;
    }

    public void setDeletePriv(String deletePriv) {
        this.deletePriv = deletePriv;
    }

    public String getCreatePriv() {
        return createPriv;
    }

    public void setCreatePriv(String createPriv) {
        this.createPriv = createPriv;
    }

    public String getDropPiv() {
        return dropPiv;
    }

    public void setDropPiv(String dropPiv) {
        this.dropPiv = dropPiv;
    }

    public String getReloadPriv() {
        return reloadPriv;
    }

    public void setReloadPriv(String reloadPriv) {
        this.reloadPriv = reloadPriv;
    }

    public String getShutdownPriv() {
        return shutdownPriv;
    }

    public void setShutdownPriv(String shutdownPriv) {
        this.shutdownPriv = shutdownPriv;
    }

    public String getProcessPriv() {
        return processPriv;
    }

    public void setProcessPriv(String processPriv) {
        this.processPriv = processPriv;
    }

    public String getFilePriv() {
        return filePriv;
    }

    public void setFilePriv(String filePriv) {
        this.filePriv = filePriv;
    }

    public String getGrantPriv() {
        return grantPriv;
    }

    public void setGrantPriv(String grantPriv) {
        this.grantPriv = grantPriv;
    }

    public String getReferencesPriv() {
        return referencesPriv;
    }

    public void setReferencesPriv(String referencesPriv) {
        this.referencesPriv = referencesPriv;
    }

    public String getIndexPriv() {
        return indexPriv;
    }

    public void setIndexPriv(String indexPriv) {
        this.indexPriv = indexPriv;
    }

    public String getAlterPriv() {
        return alterPriv;
    }

    public void setAlterPriv(String alterPriv) {
        this.alterPriv = alterPriv;
    }

    public String getShowDbPriv() {
        return showDbPriv;
    }

    public void setShowDbPriv(String showDbPriv) {
        this.showDbPriv = showDbPriv;
    }

    public String getSuperPriv() {
        return superPriv;
    }

    public void setSuperPriv(String superPriv) {
        this.superPriv = superPriv;
    }

    public String getCreateTmpTablePriv() {
        return createTmpTablePriv;
    }

    public void setCreateTmpTablePriv(String createTmpTablePriv) {
        this.createTmpTablePriv = createTmpTablePriv;
    }

    public String getLockTablesPriv() {
        return lockTablesPriv;
    }

    public void setLockTablesPriv(String lockTablesPriv) {
        this.lockTablesPriv = lockTablesPriv;
    }

    public String getExecutePriv() {
        return executePriv;
    }

    public void setExecutePriv(String executePriv) {
        this.executePriv = executePriv;
    }

    public String getReplSlavePriv() {
        return replSlavePriv;
    }

    public void setReplSlavePriv(String replSlavePriv) {
        this.replSlavePriv = replSlavePriv;
    }

    public String getReplClientPriv() {
        return replClientPriv;
    }

    public void setReplClientPriv(String replClientPriv) {
        this.replClientPriv = replClientPriv;
    }

    public String getCreateViewPriv() {
        return createViewPriv;
    }

    public void setCreateViewPriv(String createViewPriv) {
        this.createViewPriv = createViewPriv;
    }

    public String getShowViewPriv() {
        return showViewPriv;
    }

    public void setShowViewPriv(String showViewPriv) {
        this.showViewPriv = showViewPriv;
    }

    public String getCreateRoutinePriv() {
        return createRoutinePriv;
    }

    public void setCreateRoutinePriv(String createRoutinePriv) {
        this.createRoutinePriv = createRoutinePriv;
    }

    public String getAlterRoutinePriv() {
        return alterRoutinePriv;
    }

    public void setAlterRoutinePriv(String alterRoutinePriv) {
        this.alterRoutinePriv = alterRoutinePriv;
    }

    public String getCreateUserPriv() {
        return createUserPriv;
    }

    public void setCreateUserPriv(String createUserPriv) {
        this.createUserPriv = createUserPriv;
    }

    public String getEventPriv() {
        return eventPriv;
    }

    public void setEventPriv(String eventPriv) {
        this.eventPriv = eventPriv;
    }

    public String getTriggerPriv() {
        return triggerPriv;
    }

    public void setTriggerPriv(String triggerPriv) {
        this.triggerPriv = triggerPriv;
    }

    public String getCreateTablespacePriv() {
        return createTablespacePriv;
    }

    public void setCreateTablespacePriv(String createTablespacePriv) {
        this.createTablespacePriv = createTablespacePriv;
    }

    public String getSslType() {
        return sslType;
    }

    public void setSslType(String sslType) {
        this.sslType = sslType;
    }

    public String getSslCipher() {
        return sslCipher;
    }

    public void setSslCipher(String sslCipher) {
        this.sslCipher = sslCipher;
    }

    public String getX509Issuer() {
        return x509Issuer;
    }

    public void setX509Issuer(String x509Issuer) {
        this.x509Issuer = x509Issuer;
    }

    public String getX509Subject() {
        return x509Subject;
    }

    public void setX509Subject(String x509Subject) {
        this.x509Subject = x509Subject;
    }

    public Integer getMaxQuestions() {
        return maxQuestions;
    }

    public void setMaxQuestions(Integer maxQuestions) {
        this.maxQuestions = maxQuestions;
    }

    public Integer getMaxUpdates() {
        return maxUpdates;
    }

    public void setMaxUpdates(Integer maxUpdates) {
        this.maxUpdates = maxUpdates;
    }

    public Integer getMaxConnections() {
        return maxConnections;
    }

    public void setMaxConnections(Integer maxConnections) {
        this.maxConnections = maxConnections;
    }

    public Integer getMaxUserConnections() {
        return maxUserConnections;
    }

    public void setMaxUserConnections(Integer maxUserConnections) {
        this.maxUserConnections = maxUserConnections;
    }

    public String getPlugin() {
        return plugin;
    }

    public void setPlugin(String plugin) {
        this.plugin = plugin;
    }

    public String getAuthenticationString() {
        return authenticationString;
    }

    public void setAuthenticationString(String authenticationString) {
        this.authenticationString = authenticationString;
    }

    public String getPasswordExpired() {
        return passwordExpired;
    }

    public void setPasswordExpired(String passwordExpired) {
        this.passwordExpired = passwordExpired;
    }

    public LocalDateTime getPasswordLastChanged() {
        return passwordLastChanged;
    }

    public void setPasswordLastChanged(LocalDateTime passwordLastChanged) {
        this.passwordLastChanged = passwordLastChanged;
    }

    public Integer getPasswordLifetime() {
        return passwordLifetime;
    }

    public void setPasswordLifetime(Integer passwordLifetime) {
        this.passwordLifetime = passwordLifetime;
    }

    public String getAccountLocked() {
        return accountLocked;
    }

    public void setAccountLocked(String accountLocked) {
        this.accountLocked = accountLocked;
    }

    public String getCreateRolePriv() {
        return createRolePriv;
    }

    public void setCreateRolePriv(String createRolePriv) {
        this.createRolePriv = createRolePriv;
    }

    public String getDropRolePriv() {
        return dropRolePriv;
    }

    public void setDropRolePriv(String dropRolePriv) {
        this.dropRolePriv = dropRolePriv;
    }

    public Integer getPasswordReuseHistory() {
        return passwordReuseHistory;
    }

    public void setPasswordReuseHistory(Integer passwordReuseHistory) {
        this.passwordReuseHistory = passwordReuseHistory;
    }

    public Integer getPasswordReuseTime() {
        return passwordReuseTime;
    }

    public void setPasswordReuseTime(Integer passwordReuseTime) {
        this.passwordReuseTime = passwordReuseTime;
    }

    public String getPasswordRequireCurrent() {
        return passwordRequireCurrent;
    }

    public void setPasswordRequireCurrent(String passwordRequireCurrent) {
        this.passwordRequireCurrent = passwordRequireCurrent;
    }

    public String getUserAttributes() {
        return userAttributes;
    }

    public void setUserAttributes(String userAttributes) {
        this.userAttributes = userAttributes;
    }
}
