package top.uhyils.usher.service.impl;

import org.springframework.stereotype.Service;
import top.uhyils.usher.annotation.ReadWriteMark;
import top.uhyils.usher.assembler.OrderApiAssembler;
import top.uhyils.usher.pojo.DO.OrderApiDO;
import top.uhyils.usher.pojo.DTO.OrderApiDTO;
import top.uhyils.usher.pojo.entity.OrderApi;
import top.uhyils.usher.repository.OrderApiRepository;
import top.uhyils.usher.service.OrderApiService;

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
