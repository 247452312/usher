package top.uhyils.usher.protocol.rpc.impl;

import java.util.List;
import javax.annotation.Resource;
import top.uhyils.usher.pojo.DTO.DeptDTO;
import top.uhyils.usher.pojo.DTO.request.PutMenusToDeptsCommand;
import top.uhyils.usher.pojo.DTO.request.PutPowersToDeptCommand;
import top.uhyils.usher.pojo.DTO.response.GetAllPowerWithHaveMarkDTO;
import top.uhyils.usher.pojo.cqe.DefaultCQE;
import top.uhyils.usher.pojo.cqe.command.IdCommand;
import top.uhyils.usher.pojo.cqe.command.IdsCommand;
import top.uhyils.usher.pojo.cqe.query.IdQuery;
import top.uhyils.usher.protocol.rpc.DeptProvider;
import top.uhyils.usher.protocol.rpc.base.BaseDefaultProvider;
import top.uhyils.usher.rpc.annotation.RpcService;
import top.uhyils.usher.service.BaseDoService;
import top.uhyils.usher.service.DeptService;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年05月27日 16时28分
 */
@RpcService
public class DeptProviderImpl extends BaseDefaultProvider<DeptDTO> implements DeptProvider {

    @Resource
    private DeptService service;

    @Override
    public Boolean putPowersToDept(PutPowersToDeptCommand request) throws Exception {
        return service.putPowersToDept(request.getDeptId(), request.getPowerIds());
    }

    @Override
    public Boolean deleteDeptPower(IdsCommand request) {
        List<Long> ids = request.getIds();
        return service.deleteDeptPower(ids);
    }

    @Override
    public Boolean putMenusToDept(PutMenusToDeptsCommand request) {
        return service.putMenusToDept(request.getDeptId(), request.getMenuIds());
    }

    @Override
    public List<DeptDTO> getDepts(DefaultCQE request) {
        return service.getDepts();
    }

    @Override
    public List<GetAllPowerWithHaveMarkDTO> getAllPowerWithHaveMark(IdQuery request) {
        return service.getAllPowerWithHaveMark(request.getId());

    }

    @Override
    public Boolean deleteDept(IdCommand request) {
        return service.deleteDept(request.getId());

    }

    @Override
    protected BaseDoService<DeptDTO> getService() {
        return service;
    }

}



