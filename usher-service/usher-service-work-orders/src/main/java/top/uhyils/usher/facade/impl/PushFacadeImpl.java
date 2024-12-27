package top.uhyils.usher.facade.impl;

import com.alibaba.fastjson.JSON;
import top.uhyils.usher.annotation.Facade;
import top.uhyils.usher.content.OrderContent;
import top.uhyils.usher.enums.OrderStatusEnum;
import top.uhyils.usher.enums.PushTypeEnum;
import top.uhyils.usher.facade.PushFacade;
import top.uhyils.usher.mq.util.MqUtil;
import top.uhyils.usher.pojo.DTO.OrderInfoDTO;
import top.uhyils.usher.pojo.DTO.request.PushMsgToSomeoneRequest;
import top.uhyils.usher.pojo.entity.OrderNode;
import top.uhyils.usher.pojo.temp.InitApiRequestTemporary;
import top.uhyils.usher.protocol.rpc.PushMsgProvider;
import top.uhyils.usher.rpc.annotation.RpcReference;
import top.uhyils.usher.util.Asserts;


/**
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年09月04日 13时09分
 */
@Facade
public class PushFacadeImpl implements PushFacade {

    @RpcReference
    private PushMsgProvider pushMsgProvider;

    @Override
    public Boolean pushMsg(OrderInfoDTO orderInfo, Long userId, OrderStatusEnum targetStatus, PushTypeEnum pushType) {
        PushMsgToSomeoneRequest request = new PushMsgToSomeoneRequest();
        request.setUserId(userId);
        request.setType(pushType.getCode());
        request.setTitle(targetStatus.getTitle());
        request.setMsg(targetStatus.getMsg(orderInfo));
        return pushMsgProvider.pushMsgToSomeone(request);
    }

    @Override
    public void noticeAutoNodeDeal(OrderNode orderNode, OrderNode pervOrder) {
        InitApiRequestTemporary msg = new InitApiRequestTemporary();
        msg.setOrderNode(orderNode.toData().orElseThrow(Asserts::throwOptionalException));
        msg.setPervOrderNode(pervOrder.toData().orElseThrow(Asserts::throwOptionalException));
        MqUtil.sendMsg(OrderContent.ORDER_TOPIC, OrderContent.ORDER_AUTO_NODE_SEND_TAG, JSON.toJSONString(msg));
    }


}
