package top.uhyils.usher.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.uhyils.usher.annotation.ReadWriteMark;
import top.uhyils.usher.assembler.ApiGroupAssembler;
import top.uhyils.usher.pojo.DO.ApiGroupDO;
import top.uhyils.usher.pojo.DTO.ApiGroupDTO;
import top.uhyils.usher.pojo.cqe.DefaultCQE;
import top.uhyils.usher.pojo.cqe.query.IdQuery;
import top.uhyils.usher.pojo.entity.ApiGroup;
import top.uhyils.usher.repository.ApiGroupRepository;
import top.uhyils.usher.repository.ApiRepository;
import top.uhyils.usher.service.ApiGroupService;

/**
 * api组表(ApiGroup)表 内部服务实现类
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年09月02日 19时46分51秒
 */
@Service
@ReadWriteMark(tables = {"sys_api_group"})
public class ApiGroupServiceImpl extends AbstractDoService<ApiGroupDO, ApiGroup, ApiGroupDTO, ApiGroupRepository, ApiGroupAssembler> implements ApiGroupService {

    @Autowired
    private ApiRepository apiRepository;

    public ApiGroupServiceImpl(ApiGroupAssembler assembler, ApiGroupRepository repository) {
        super(assembler, repository);
    }

    @Override
    public String test(IdQuery request) {
        ApiGroup apiGroup = new ApiGroup(request.getId());
        apiGroup.fillApi(apiRepository);
        return apiGroup.callApi();
    }

    @Override
    public List<ApiGroupDTO> getCanBeSubscribed(DefaultCQE request) {
        List<ApiGroup> apiGroups = rep.getCanBeSubscribed();
        return assem.listEntityToDTO(apiGroups);
    }

    @Override
    public Integer remove(Long id) {
        ApiGroup apiGroup = new ApiGroup(id);
        apiGroup.removeSelf(rep);
        return apiGroup.removeApis(apiRepository);
    }
}
