package top.uhyils.usher.handler.impl.run;

import org.springframework.stereotype.Service;
import top.uhyils.usher.enums.ApiCodeEnum;
import top.uhyils.usher.handler.RunApiHandler;
import top.uhyils.usher.pojo.DO.OrderNodeDO;
import top.uhyils.usher.pojo.dto.ApiDealDto;
import top.uhyils.usher.pojo.temp.InitToRunApiTemporary;
import top.uhyils.usher.pojo.temp.RunToSaveApiTemporary;

/**
 * 处理购买自动工单执行逻辑
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年11月24日 07时49分
 */
@Service
public class RunPurchasingHandlerImpl implements RunApiHandler {

    @Override
    public RunToSaveApiTemporary run(InitToRunApiTemporary requestTemporary) {
        OrderNodeDO orderNode = requestTemporary.getOrderNode();
        OrderNodeDO pervOrderNode = requestTemporary.getPervOrderNode();
        ApiDealDto doInitDto = requestTemporary.getApiDealDto();
        ApiDealDto doRunDto = doRun(orderNode, pervOrderNode, doInitDto);
        return RunToSaveApiTemporary.build(orderNode, pervOrderNode, doRunDto);
    }

    private ApiDealDto doRun(OrderNodeDO orderNode, OrderNodeDO pervOrderNode, ApiDealDto apiDealDto) {
        return ApiDealDto.build(ApiCodeEnum.SUCCESS);
    }
}
