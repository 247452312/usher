package team.opentech.usher.facade.impl;

import com.alibaba.fastjson.JSON;
import javax.annotation.Resource;
import team.opentech.usher.annotation.Facade;
import team.opentech.usher.content.OrderContent;
import team.opentech.usher.enums.OrderStatusEnum;
import team.opentech.usher.enums.PushTypeEnum;
import team.opentech.usher.facade.PushFacade;
import team.opentech.usher.mq.MQMessage;
import team.opentech.usher.mq.client.MQClient;
import team.opentech.usher.pojo.DTO.OrderInfoDTO;
import team.opentech.usher.pojo.DTO.request.PushMsgToSomeoneRequest;
import team.opentech.usher.pojo.entity.OrderNode;
import team.opentech.usher.pojo.temp.InitApiRequestTemporary;
import team.opentech.usher.protocol.rpc.PushMsgProvider;
import team.opentech.usher.rpc.annotation.RpcReference;
import team.opentech.usher.util.Asserts;


/**
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年09月04日 13时09分
 */
@Facade
public class PushFacadeImpl implements PushFacade {

    @RpcReference
    private PushMsgProvider pushMsgProvider;


    @Resource
    private MQClient mqClient;

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
        MQMessage mqMessage = new MQMessage(OrderContent.ORDER_TOPIC, OrderContent.ORDER_AUTO_NODE_SEND_QUEUE, JSON.toJSONString(msg));
        mqClient.send(mqMessage);
    }


}
