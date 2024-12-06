package top.uhyils.usher.service.impl;

import org.springframework.stereotype.Service;
import top.uhyils.usher.annotation.ReadWriteMark;
import top.uhyils.usher.assembler.AiDeviceRealTimeAssembler;
import top.uhyils.usher.pojo.DO.AiDeviceRealTimeDO;
import top.uhyils.usher.pojo.DTO.AiDeviceRealTimeDTO;
import top.uhyils.usher.pojo.entity.AiDeviceRealTime;
import top.uhyils.usher.repository.AiDeviceRealTimeRepository;
import top.uhyils.usher.service.AiDeviceRealTimeService;

/**
 * 设备实时状态表(AiDeviceRealTime)表 内部服务实现类
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2024年12月06日 13时57分
 */
@Service
@ReadWriteMark(tables = {"sys_ai_device_real_time"})
public class AiDeviceRealTimeServiceImpl extends AbstractDoService<AiDeviceRealTimeDO, AiDeviceRealTime, AiDeviceRealTimeDTO, AiDeviceRealTimeRepository, AiDeviceRealTimeAssembler> implements AiDeviceRealTimeService {

    public AiDeviceRealTimeServiceImpl(AiDeviceRealTimeAssembler assembler, AiDeviceRealTimeRepository repository) {
        super(assembler, repository);
    }


}
