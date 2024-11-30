package top.uhyils.usher.facade;

import top.uhyils.usher.enums.OrderStatusEnum;
import top.uhyils.usher.enums.PushTypeEnum;
import top.uhyils.usher.pojo.DTO.OrderInfoDTO;
import top.uhyils.usher.pojo.entity.OrderNode;

/**
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年09月04日 13时09分
 */
public interface PushFacade extends BaseFacade {

    /**
     * 推送msg
     *
     * @param orderInfo
     * @param userId
     * @param targetStatus
     * @param pushType
     *
     * @return
     */
    Boolean pushMsg(OrderInfoDTO orderInfo, Long userId, OrderStatusEnum targetStatus, PushTypeEnum pushType);

    /**
     * 通知下一个节点
     *
     * @param orderNode
     * @param pervOrder
     */
    void noticeAutoNodeDeal(OrderNode orderNode, OrderNode pervOrder);
}
