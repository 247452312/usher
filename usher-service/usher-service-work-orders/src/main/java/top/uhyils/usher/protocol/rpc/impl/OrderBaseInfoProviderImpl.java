package top.uhyils.usher.protocol.rpc.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import top.uhyils.usher.pojo.DTO.OrderBaseInfoDTO;
import top.uhyils.usher.pojo.cqe.DefaultCQE;
import top.uhyils.usher.pojo.cqe.query.IdQuery;
import top.uhyils.usher.protocol.rpc.OrderBaseInfoProvider;
import top.uhyils.usher.protocol.rpc.base.BaseDefaultProvider;
import top.uhyils.usher.rpc.annotation.RpcService;
import top.uhyils.usher.service.BaseDoService;
import top.uhyils.usher.service.OrderBaseInfoService;

/**
 * 工单基础信息样例表(OrderBaseInfo)表 RPC对外访问实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月31日 19时58分55秒
 */
@RpcService
public class OrderBaseInfoProviderImpl extends BaseDefaultProvider<OrderBaseInfoDTO> implements OrderBaseInfoProvider {


    @Autowired
    private OrderBaseInfoService service;

    @Override
    public List<OrderBaseInfoDTO> getAllBaseOrderIdAndName(DefaultCQE request) {
        return service.getAllBaseOrderIdAndName(request);
    }

    @Override
    public OrderBaseInfoDTO getOneOrder(IdQuery request) {
        return service.getOneOrder(request);
    }

    @Override
    protected BaseDoService<OrderBaseInfoDTO> getService() {
        return service;
    }

}

