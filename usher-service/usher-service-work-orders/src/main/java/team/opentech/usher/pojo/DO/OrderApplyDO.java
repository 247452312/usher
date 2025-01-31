package team.opentech.usher.pojo.DO;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import team.opentech.usher.pojo.DO.base.BaseDO;

/**
 * (OrderApply)表 数据库实体类
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年09月11日 10时46分02秒
 */
@TableName(value = "sys_order_apply")
public class OrderApplyDO extends BaseDO {

    private static final long serialVersionUID = 822118896954524886L;


    /**
     * 申请人,上一个节点的处理人
     */
    @TableField
    private Long applyUserId;

    /**
     * 申请处理的工单id
     */
    @TableField
    private Long orderId;

    /**
     * 工单节点id
     */
    @TableField
    private Long orderNodeId;

    /**
     * 此工单监管人id
     */
    @TableField
    private Long monitorUserId;

    /**
     * 目标人id
     */
    @TableField
    private Long targetUserId;

    /**
     * 申请类型 0->转交申请
     */
    @TableField
    private Integer type;

    /**
     * 申请状态 0->未查看 1->未受理 2->已受理 3->已同意 4->已驳回
     */
    @TableField
    private Integer status;


    public Long getApplyUserId() {
        return applyUserId;
    }

    public void setApplyUserId(Long applyUserId) {
        this.applyUserId = applyUserId;
    }


    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }


    public Long getOrderNodeId() {
        return orderNodeId;
    }

    public void setOrderNodeId(Long orderNodeId) {
        this.orderNodeId = orderNodeId;
    }


    public Long getMonitorUserId() {
        return monitorUserId;
    }

    public void setMonitorUserId(Long monitorUserId) {
        this.monitorUserId = monitorUserId;
    }


    public Long getTargetUserId() {
        return targetUserId;
    }

    public void setTargetUserId(Long targetUserId) {
        this.targetUserId = targetUserId;
    }


    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
