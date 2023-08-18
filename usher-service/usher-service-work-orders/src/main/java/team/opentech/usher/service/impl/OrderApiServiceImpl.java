package team.opentech.usher.service.impl;

import team.opentech.usher.annotation.ReadWriteMark;
import team.opentech.usher.assembler.OrderApiAssembler;
import team.opentech.usher.pojo.DO.OrderApiDO;
import team.opentech.usher.pojo.DTO.OrderApiDTO;
import team.opentech.usher.pojo.entity.OrderApi;
import team.opentech.usher.repository.OrderApiRepository;
import team.opentech.usher.service.OrderApiService;
import org.springframework.stereotype.Service;

/**
 * 节点api表(OrderApi)表 内部服务实现类
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月31日 19时58分46秒
 */
@Service
@ReadWriteMark(tables = {"sys_order_api"})
public class OrderApiServiceImpl extends AbstractDoService<OrderApiDO, OrderApi, OrderApiDTO, OrderApiRepository, OrderApiAssembler> implements OrderApiService {

    public OrderApiServiceImpl(OrderApiAssembler assembler, OrderApiRepository repository) {
        super(assembler, repository);
    }


}
