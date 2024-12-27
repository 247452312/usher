package top.uhyils.usher.service.impl;

import org.springframework.stereotype.Service;
import top.uhyils.usher.annotation.ReadWriteMark;
import top.uhyils.usher.assembler.OutApiAssembler;
import top.uhyils.usher.pojo.DO.OutApiDO;
import top.uhyils.usher.pojo.DTO.OutApiDTO;
import top.uhyils.usher.pojo.entity.OutApi;
import top.uhyils.usher.repository.OutApiRepository;
import top.uhyils.usher.service.OutApiService;

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
