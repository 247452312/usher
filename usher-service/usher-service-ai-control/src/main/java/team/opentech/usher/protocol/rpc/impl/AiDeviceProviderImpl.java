package team.opentech.usher.protocol.rpc.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import team.opentech.usher.pojo.DTO.AiDeviceDTO;
import team.opentech.usher.pojo.cqe.ChangeDeviceCommand;
import team.opentech.usher.pojo.cqe.ChangePositionCommand;
import team.opentech.usher.pojo.cqe.CreateDeviceCommand;
import team.opentech.usher.pojo.cqe.command.IdCommand;
import team.opentech.usher.pojo.cqe.command.IdsCommand;
import team.opentech.usher.pojo.cqe.query.IdQuery;
import team.opentech.usher.protocol.rpc.AiDeviceProvider;
import team.opentech.usher.protocol.rpc.base.BaseDefaultProvider;
import team.opentech.usher.rpc.annotation.RpcService;
import team.opentech.usher.service.AiDeviceService;
import team.opentech.usher.service.BaseDoService;

/**
 * 设备表(AiDevice)表 RPC对外访问实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2024年11月13日 20时55分
 */
@RpcService
public class AiDeviceProviderImpl extends BaseDefaultProvider<AiDeviceDTO> implements AiDeviceProvider {


    @Autowired
    private AiDeviceService service;

    @Override
    public Boolean createDevice(CreateDeviceCommand command) {
        return service.createDevice(command);
    }

    @Override
    public Boolean removeDevice(IdCommand command) {
        return service.removeDevice(command);
    }

    @Override
    public Boolean removeDevices(IdsCommand command) {
        return service.removeDevices(command);
    }

    @Override
    public Boolean removeDeviceBySubSpaceId(IdCommand command) {
        return service.removeDeviceBySubSpaceId(command);
    }

    @Override
    public Boolean changePosition(ChangePositionCommand command) {
        return service.changePosition(command);
    }

    @Override
    public Boolean changeDevice(ChangeDeviceCommand command) {
        return service.changeDevice(command);
    }

    @Override
    public List<AiDeviceDTO> findDeviceBySubSpaceId(IdQuery query) {
        return service.findDeviceBySubSpaceId(query);
    }

    @Override
    public List<AiDeviceDTO> findDeviceBySpaceId(IdQuery query) {
        return service.findDeviceBySpaceId(query);
    }


    @Override
    protected BaseDoService<AiDeviceDTO> getService() {
        return service;
    }

}

