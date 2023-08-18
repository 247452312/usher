package team.opentech.usher.handler.impl.trans;

import team.opentech.usher.handler.TransApiHandler;
import team.opentech.usher.pojo.DO.OrderNodeDO;
import team.opentech.usher.pojo.dto.ApiDealDto;
import team.opentech.usher.pojo.temp.SaveToTransApiTemporary;
import org.springframework.stereotype.Service;

/**
 * 处理购买自动工单流转下一步行为
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年11月24日 07时49分
 */
@Service
public class TransPurchasingHandlerImpl implements TransApiHandler {

    @Override
    public void trans(SaveToTransApiTemporary requestTemporary) {
        OrderNodeDO orderNode = requestTemporary.getOrderNode();
        OrderNodeDO pervOrderNode = requestTemporary.getPervOrderNode();
        ApiDealDto doSaveDto = requestTemporary.getApiDealDto();
        doTrans(orderNode, pervOrderNode, doSaveDto);
    }

    private void doTrans(OrderNodeDO orderNode, OrderNodeDO pervOrderNode, ApiDealDto doSaveDto) {

    }
}
