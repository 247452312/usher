package team.opentech.usher.pojo.DO;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import team.opentech.usher.pojo.DO.base.BaseDO;

/**
 * 工单节点样例表(OrderNode)表 数据库实体类
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年09月11日 10时46分07秒
 */
@TableName(value = "sys_order_node")
public class OrderNodeDO extends BaseDO {

    private static final long serialVersionUID = -75504697520082551L;


    /**
     * 基础表id
     */
    @TableField
    private Long baseInfoId;

    /**
     * 节点名称
     */
    @TableField
    private String name;

    /**
     * 节点描述
     */
    @TableField
    private String desc;

    /**
     * 处理人id
     */
    @TableField
    private Long runDealUserId;

    /**
     * 抄送人id
     */
    @TableField
    private Long noticeUserId;

    /**
     * 处理类型 0->自动处理 1->人工处理
     */
    @TableField
    private Integer runType;

    /**
     * 节点状态 0->未开始 1->等待开始 2->处理中 3->结束
     */
    @TableField
    private Integer status;

    /**
     * 节点类型 0->开始节点 1->中间节点 2->结束节点
     */
    @TableField
    private Integer type;

    /**
     * 限时(分钟)
     */
    @TableField
    private Integer limitTime;

    /**
     * 节点初始化事件id
     */
    @TableField
    private Long initApiId;

    /**
     * 节点执行事件id(自动处理有效)
     */
    @TableField
    private Long runApiId;

    /**
     * 保存执行事件id(自动处理有效)
     */
    @TableField
    private Long saveApiId;

    /**
     * 流转执行事件id(自动处理有效)
     */
    @TableField
    private Long transApiId;

    /**
     * 处理结果类型 0->处理成功 1->处理失败 2->转交别人处理
     */
    @TableField
    private Integer resultType;

    /**
     * 处理结果id
     */
    @TableField
    private Long resultId;

    /**
     * 是否同步(所有指向此节点的前提事件结束才执行此事件)
     */
    @TableField
    private Integer sync;

    /**
     * 处理人建议
     */
    @TableField
    private String suggest;


    public Long getBaseInfoId() {
        return baseInfoId;
    }

    public void setBaseInfoId(Long baseInfoId) {
        this.baseInfoId = baseInfoId;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


    public Long getRunDealUserId() {
        return runDealUserId;
    }

    public void setRunDealUserId(Long runDealUserId) {
        this.runDealUserId = runDealUserId;
    }


    public Long getNoticeUserId() {
        return noticeUserId;
    }

    public void setNoticeUserId(Long noticeUserId) {
        this.noticeUserId = noticeUserId;
    }


    public Integer getRunType() {
        return runType;
    }

    public void setRunType(Integer runType) {
        this.runType = runType;
    }


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }


    public Integer getLimitTime() {
        return limitTime;
    }

    public void setLimitTime(Integer limitTime) {
        this.limitTime = limitTime;
    }


    public Long getInitApiId() {
        return initApiId;
    }

    public void setInitApiId(Long initApiId) {
        this.initApiId = initApiId;
    }


    public Long getRunApiId() {
        return runApiId;
    }

    public void setRunApiId(Long runApiId) {
        this.runApiId = runApiId;
    }


    public Long getSaveApiId() {
        return saveApiId;
    }

    public void setSaveApiId(Long saveApiId) {
        this.saveApiId = saveApiId;
    }


    public Long getTransApiId() {
        return transApiId;
    }

    public void setTransApiId(Long transApiId) {
        this.transApiId = transApiId;
    }


    public Integer getResultType() {
        return resultType;
    }

    public void setResultType(Integer resultType) {
        this.resultType = resultType;
    }


    public Long getResultId() {
        return resultId;
    }

    public void setResultId(Long resultId) {
        this.resultId = resultId;
    }


    public Integer getSync() {
        return sync;
    }

    public void setSync(Integer sync) {
        this.sync = sync;
    }


    public String getSuggest() {
        return suggest;
    }

    public void setSuggest(String suggest) {
        this.suggest = suggest;
    }

}
