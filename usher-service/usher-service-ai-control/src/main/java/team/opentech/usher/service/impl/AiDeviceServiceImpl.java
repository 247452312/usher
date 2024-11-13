package team.opentech.usher.service.impl;

import org.springframework.stereotype.Service;
import team.opentech.usher.annotation.ReadWriteMark;
import team.opentech.usher.assembler.AiDeviceAssembler;
import team.opentech.usher.pojo.DO.AiDeviceDO;
import team.opentech.usher.pojo.DTO.AiDeviceDTO;
import team.opentech.usher.pojo.entity.AiDevice;
import team.opentech.usher.repository.AiDeviceRepository;
import team.opentech.usher.service.AiDeviceService;

/**
 * 设备表(AiDevice)表 内部服务实现类
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2024年11月13日 20时55分
 */
@Service
@ReadWriteMark(tables = {"sys_ai_device"})
public class AiDeviceServiceImpl extends AbstractDoService<AiDeviceDO, AiDevice, AiDeviceDTO, AiDeviceRepository, AiDeviceAssembler> implements AiDeviceService {

    public AiDeviceServiceImpl(AiDeviceAssembler assembler, AiDeviceRepository repository) {
        super(assembler, repository);
    }


}
