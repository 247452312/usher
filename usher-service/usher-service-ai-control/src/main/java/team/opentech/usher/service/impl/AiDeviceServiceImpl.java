package team.opentech.usher.service.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import team.opentech.usher.annotation.ReadWriteMark;
import team.opentech.usher.assembler.AiDeviceAssembler;
import team.opentech.usher.pojo.DO.AiDeviceDO;
import team.opentech.usher.pojo.DTO.AiDeviceDTO;
import team.opentech.usher.pojo.cqe.FindDeviceBySubSpaceIdQuery;
import team.opentech.usher.pojo.entity.AiDevice;
import team.opentech.usher.pojo.event.DeviceCleanEvent;
import team.opentech.usher.pojo.event.DeviceInstructionCleanEvent;
import team.opentech.usher.repository.AiDeviceInstructionRepository;
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

    @Resource
    private AiDeviceInstructionRepository instructionRepository;

    public AiDeviceServiceImpl(AiDeviceAssembler assembler, AiDeviceRepository repository) {
        super(assembler, repository);
    }


    @Override
    public void deviceCleanEvent(DeviceCleanEvent event) {
        List<AiDevice> aiDevices = rep.findBySubSpaceId(event.getSubspaceId());
        for (AiDevice aiDevice : aiDevices) {
            aiDevice.removeSelf(rep);
        }

    }

    @Override
    public void deviceInstructionCleanEvent(DeviceInstructionCleanEvent event) {
        instructionRepository.removeByDeviceId(event.getDeviceId());
    }

    @Override
    public List<AiDeviceDTO> findDeviceBySubSpaceId(FindDeviceBySubSpaceIdQuery query) {
        List<AiDevice> bySubSpaceId = rep.findBySubSpaceId(query.getSpaceId());
        return assem.listEntityToDTO(bySubSpaceId);
    }
}
