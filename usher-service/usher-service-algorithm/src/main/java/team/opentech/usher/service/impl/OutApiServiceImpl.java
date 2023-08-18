package team.opentech.usher.service.impl;

import team.opentech.usher.annotation.ReadWriteMark;
import team.opentech.usher.assembler.OutApiAssembler;
import team.opentech.usher.pojo.DO.OutApiDO;
import team.opentech.usher.pojo.DTO.OutApiDTO;
import team.opentech.usher.pojo.entity.OutApi;
import team.opentech.usher.repository.OutApiRepository;
import team.opentech.usher.service.OutApiService;
import org.springframework.stereotype.Service;

/**
 * 开放api(OutApi)表 内部服务实现类
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年09月09日 20时58分11秒
 */
@Service
@ReadWriteMark(tables = {"sys_out_api"})
public class OutApiServiceImpl extends AbstractDoService<OutApiDO, OutApi, OutApiDTO, OutApiRepository, OutApiAssembler> implements OutApiService {

    public OutApiServiceImpl(OutApiAssembler assembler, OutApiRepository repository) {
        super(assembler, repository);
    }


}
