package team.opentech.usher.service.impl;

import team.opentech.usher.annotation.ReadWriteMark;
import team.opentech.usher.assembler.ApiAssembler;
import team.opentech.usher.pojo.DO.ApiDO;
import team.opentech.usher.pojo.DTO.ApiDTO;
import team.opentech.usher.pojo.DTO.base.Page;
import team.opentech.usher.pojo.cqe.query.demo.Arg;
import team.opentech.usher.pojo.cqe.query.demo.Limit;
import team.opentech.usher.pojo.cqe.query.demo.Order;
import team.opentech.usher.pojo.entity.Api;
import team.opentech.usher.repository.ApiRepository;
import team.opentech.usher.service.ApiService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * api表(Api)表 内部服务实现类
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年09月02日 19时46分47秒
 */
@Service
@ReadWriteMark(tables = {"sys_api"})
public class ApiServiceImpl extends AbstractDoService<ApiDO, Api, ApiDTO, ApiRepository, ApiAssembler> implements ApiService {

    public ApiServiceImpl(ApiAssembler assembler, ApiRepository repository) {
        super(assembler, repository);
    }


    @Override
    public Page<ApiDTO> getByArgsAndGroup(Long groupId, Order order, Limit limit) {
        List<Arg> args = new ArrayList<>();
        Arg e = new Arg();
        e.setName("api_group_id");
        e.setSymbol("=");
        e.setData(groupId);
        args.add(e);
        Page<Api> apiPage = rep.find(args, order, limit);
        return assem.pageToDTO(apiPage);
    }
}
