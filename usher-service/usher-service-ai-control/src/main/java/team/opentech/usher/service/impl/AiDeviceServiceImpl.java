package team.opentech.usher.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import team.opentech.usher.annotation.ReadWriteMark;
import team.opentech.usher.assembler.AiDeviceAssembler;
import team.opentech.usher.pojo.DO.AiDeviceDO;
import team.opentech.usher.pojo.DTO.AiDeviceDTO;
import team.opentech.usher.pojo.cqe.ChangeDeviceCommand;
import team.opentech.usher.pojo.cqe.ChangePositionCommand;
import team.opentech.usher.pojo.cqe.CreateDeviceCommand;
import team.opentech.usher.pojo.cqe.command.IdCommand;
import team.opentech.usher.pojo.cqe.command.IdsCommand;
import team.opentech.usher.pojo.cqe.query.IdQuery;
import team.opentech.usher.pojo.entity.AiDevice;
import team.opentech.usher.pojo.entity.AiSubspace;
import team.opentech.usher.pojo.event.DeviceCleanEvent;
import team.opentech.usher.pojo.event.DeviceInstructionCleanEvent;
import team.opentech.usher.repository.AiDeviceInstructionRepository;
import team.opentech.usher.repository.AiDeviceRepository;
import team.opentech.usher.repository.AiSpaceRepository;
import team.opentech.usher.repository.AiSubspaceRepository;
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

    @Resource
    private AiSubspaceRepository subspaceRepository;

    @Resource
    private AiSpaceRepository spaceRepository;

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
    public List<AiDeviceDTO> findDeviceBySubSpaceId(IdQuery query) {
        List<AiDevice> bySubSpaceId = rep.findBySubSpaceId(query.getId());
        return assem.listEntityToDTO(bySubSpaceId);
    }

    @Override
    public Boolean createDevice(CreateDeviceCommand command) {
        AiDevice entity = assem.toEntity(command);
        entity.saveSelf(rep);
        return Boolean.TRUE;
    }

    @Override
    public Boolean removeDevice(IdCommand command) {
        int remove = rep.remove(command.getId());
        return remove == 1;
    }

    @Override
    public Boolean removeDevices(IdsCommand command) {
        int i = rep.removeByIds(command.getIds());
        return Boolean.TRUE;
    }

    @Override
    public Boolean removeDeviceBySubSpaceId(IdCommand command) {
        rep.removeBySubSpaceId(command.getId());
        return Boolean.TRUE;
    }

    @Override
    public Boolean changePosition(ChangePositionCommand command) {
        AiDevice aiDevice = rep.find(command.getId());
        aiDevice.changePosition(command.getPosition(), command.getAngle(), command.getRotate());
        aiDevice.saveSelf(rep);
        return Boolean.TRUE;
    }

    @Override
    public Boolean changeDevice(ChangeDeviceCommand command) {
        AiDevice aiDevice = rep.find(command.getId());
        aiDevice.name(command.getName());
        aiDevice.type(command.getType(), command.getSubtype());
        aiDevice.subSpace(command.getSubspaceId());
        aiDevice.saveSelf(rep);
        return Boolean.TRUE;
    }

    @Override
    public List<AiDeviceDTO> findDeviceBySpaceId(IdQuery query) {
        List<AiSubspace> bySpaceId = subspaceRepository.findBySpaceId(query.getId());
        List<Long> subSpaceIds = bySpaceId.stream().map(t -> t.unique).collect(Collectors.toList());
        List<AiDevice> devices = rep.findBySubSpaceIds(subSpaceIds);
        return assem.listEntityToDTO(devices);
    }
}
