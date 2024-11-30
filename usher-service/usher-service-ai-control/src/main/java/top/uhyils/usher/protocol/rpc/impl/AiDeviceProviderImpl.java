package top.uhyils.usher.protocol.rpc.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import top.uhyils.usher.pojo.DTO.AiDeviceDTO;
import top.uhyils.usher.pojo.cqe.ChangeDeviceCommand;
import top.uhyils.usher.pojo.cqe.ChangePositionCommand;
import top.uhyils.usher.pojo.cqe.CreateDeviceCommand;
import top.uhyils.usher.pojo.cqe.command.IdCommand;
import top.uhyils.usher.pojo.cqe.command.IdsCommand;
import top.uhyils.usher.pojo.cqe.query.IdQuery;
import top.uhyils.usher.protocol.rpc.AiDeviceProvider;
import top.uhyils.usher.protocol.rpc.base.BaseDefaultProvider;
import top.uhyils.usher.rpc.annotation.RpcService;
import top.uhyils.usher.service.AiDeviceService;
import top.uhyils.usher.service.BaseDoService;

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

