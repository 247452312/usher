package team.opentech.usher.pojo.DTO;

import team.opentech.usher.pojo.DTO.base.BaseDTO;
import java.util.List;
import java.util.Map;

/**
 * 复制基础工单后返回的信息
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年11月15日 10时05分
 */
public class InitOrderDTO implements BaseDTO {

    /**
     * 工单初始节点需要填写的属性
     */
    private List<OrderNodeFieldDTO> orderNodeField;

    /**
     * 新生成的工单的id
     */
    private Long newOrderId;

    /**
     * 工单监管人
     */
    private Long monitorUserId;


    /**
     * 每一步的处理人
     */
    private Map<Long, Long> dealUserIds;

    /**
     * 每一步的抄送人
     */
    private Map<Long, Long> noticeUserIds;


    public InitOrderDTO() {
    }

    public InitOrderDTO(Long newOrderId, List<OrderNodeFieldDTO> orderNodeField, Long monitorUserId, Map<Long, Long> dealUserIds, Map<Long, Long> noticeUserIds) {
        this.newOrderId = newOrderId;
        this.orderNodeField = orderNodeField;
        this.monitorUserId = monitorUserId;
        this.dealUserIds = dealUserIds;
        this.noticeUserIds = noticeUserIds;
    }

    public static InitOrderDTO build(Long newOrderId, List<OrderNodeFieldDTO> orderNodeField, Long monitorUserId, Map<Long, Long> dealUserIds, Map<Long, Long> noticeUserIds) {
        InitOrderDTO build = new InitOrderDTO();
        build.setNewOrderId(newOrderId);
        build.setOrderNodeField(orderNodeField);
        build.setMonitorUserId(monitorUserId);
        build.setDealUserIds(dealUserIds);
        build.setNoticeUserIds(noticeUserIds);
        return build;

    }

    public List<OrderNodeFieldDTO> getOrderNodeField() {
        return orderNodeField;
    }

    public void setOrderNodeField(List<OrderNodeFieldDTO> orderNodeField) {
        this.orderNodeField = orderNodeField;
    }

    public Long getNewOrderId() {
        return newOrderId;
    }

    public void setNewOrderId(Long newOrderId) {
        this.newOrderId = newOrderId;
    }

    public Long getMonitorUserId() {
        return monitorUserId;
    }

    public void setMonitorUserId(Long monitorUserId) {
        this.monitorUserId = monitorUserId;
    }

    public Map<Long, Long> getDealUserIds() {
        return dealUserIds;
    }

    public void setDealUserIds(Map<Long, Long> dealUserIds) {
        this.dealUserIds = dealUserIds;
    }

    public Map<Long, Long> getNoticeUserIds() {
        return noticeUserIds;
    }

    public void setNoticeUserIds(Map<Long, Long> noticeUserIds) {
        this.noticeUserIds = noticeUserIds;
    }
}
