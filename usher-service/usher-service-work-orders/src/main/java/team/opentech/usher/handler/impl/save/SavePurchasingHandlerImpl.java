package team.opentech.usher.handler.impl.save;

import team.opentech.usher.enums.ApiCodeEnum;
import team.opentech.usher.handler.SaveApiHandler;
import team.opentech.usher.pojo.DO.OrderNodeDO;
import team.opentech.usher.pojo.dto.ApiDealDto;
import team.opentech.usher.pojo.temp.RunToSaveApiTemporary;
import team.opentech.usher.pojo.temp.SaveToTransApiTemporary;
import org.springframework.stereotype.Service;

/**
 * 处理购买自动工单保存
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年11月24日 07时49分
 */
@Service
public class SavePurchasingHandlerImpl implements SaveApiHandler {

    @Override
    public SaveToTransApiTemporary save(RunToSaveApiTemporary requestTemporary) {
        OrderNodeDO orderNode = requestTemporary.getOrderNode();
        OrderNodeDO pervOrderNode = requestTemporary.getPervOrderNode();
        ApiDealDto doRunDto = requestTemporary.getApiDealDto();
        ApiDealDto doSaveDto = doSave(orderNode, pervOrderNode, doRunDto);
        return SaveToTransApiTemporary.build(orderNode, pervOrderNode, doSaveDto);
    }

    private ApiDealDto doSave(OrderNodeDO orderNode, OrderNodeDO pervOrderNode, ApiDealDto doSaveDto) {
        return ApiDealDto.build(ApiCodeEnum.SUCCESS);
    }
}
