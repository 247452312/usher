package team.opentech.usher.protocol.rpc.impl;

import team.opentech.usher.pojo.DTO.OrderBaseInfoDTO;
import team.opentech.usher.pojo.cqe.DefaultCQE;
import team.opentech.usher.pojo.cqe.query.IdQuery;
import team.opentech.usher.protocol.rpc.OrderBaseInfoProvider;
import team.opentech.usher.protocol.rpc.base.BaseDefaultProvider;
import team.opentech.usher.rpc.annotation.RpcService;
import team.opentech.usher.service.BaseDoService;
import team.opentech.usher.service.OrderBaseInfoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

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

