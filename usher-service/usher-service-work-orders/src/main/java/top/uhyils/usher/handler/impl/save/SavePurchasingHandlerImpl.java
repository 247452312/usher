package top.uhyils.usher.handler.impl.save;

import org.springframework.stereotype.Service;
import top.uhyils.usher.enums.ApiCodeEnum;
import top.uhyils.usher.handler.SaveApiHandler;
import top.uhyils.usher.pojo.DO.OrderNodeDO;
import top.uhyils.usher.pojo.dto.ApiDealDto;
import top.uhyils.usher.pojo.temp.RunToSaveApiTemporary;
import top.uhyils.usher.pojo.temp.SaveToTransApiTemporary;

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
