package top.uhyils.usher.service.impl;

import org.springframework.stereotype.Service;
import top.uhyils.usher.annotation.ReadWriteMark;
import top.uhyils.usher.assembler.ApiSubscribeAssembler;
import top.uhyils.usher.pojo.DO.ApiSubscribeDO;
import top.uhyils.usher.pojo.DTO.ApiSubscribeDTO;
import top.uhyils.usher.pojo.DTO.request.SubscribeRequest;
import top.uhyils.usher.pojo.entity.ApiSubscribe;
import top.uhyils.usher.repository.ApiSubscribeRepository;
import top.uhyils.usher.service.ApiSubscribeService;

/**
 * api订阅表(ApiSubscribe)表 内部服务实现类
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年09月02日 19时46分55秒
 */
@Service
@ReadWriteMark(tables = {"sys_api_subscribe"})
public class ApiSubscribeServiceImpl extends AbstractDoService<ApiSubscribeDO, ApiSubscribe, ApiSubscribeDTO, ApiSubscribeRepository, ApiSubscribeAssembler> implements ApiSubscribeService {

    public ApiSubscribeServiceImpl(ApiSubscribeAssembler assembler, ApiSubscribeRepository repository) {
        super(assembler, repository);
    }


    @Override
    public Boolean subscribe(SubscribeRequest request) {
        ApiSubscribeDTO dto = assem.toDTO(request);
        ApiSubscribe apiSubscribe = assem.toEntity(dto);
        apiSubscribe.checkRepeat(rep);
        rep.save(apiSubscribe);
        return true;
    }
}
