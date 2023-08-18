package team.opentech.usher.handler.impl.init;

import team.opentech.usher.enums.ApiCodeEnum;
import team.opentech.usher.handler.InitApiHandler;
import team.opentech.usher.pojo.DO.OrderNodeDO;
import team.opentech.usher.pojo.dto.ApiDealDto;
import team.opentech.usher.pojo.temp.InitApiRequestTemporary;
import team.opentech.usher.pojo.temp.InitToRunApiTemporary;
import org.springframework.stereotype.Service;

/**
 * 处理购买自动工单初始化
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年11月24日 07时49分
 */
@Service
public class InitPurchasingHandlerImpl implements InitApiHandler {


    @Override
    public InitToRunApiTemporary init(InitApiRequestTemporary requestTemporary) {
        // 此节点
        OrderNodeDO orderNode = requestTemporary.getOrderNode();
        // 上一个节点
        OrderNodeDO pervOrderNode = requestTemporary.getPervOrderNode();

        ApiDealDto apiDealDto = doInit(orderNode, pervOrderNode);
        return InitToRunApiTemporary.build(orderNode, pervOrderNode, apiDealDto);
    }

    private ApiDealDto doInit(OrderNodeDO orderNode, OrderNodeDO pervOrderNode) {
        return ApiDealDto.build(ApiCodeEnum.SUCCESS);
    }
}
