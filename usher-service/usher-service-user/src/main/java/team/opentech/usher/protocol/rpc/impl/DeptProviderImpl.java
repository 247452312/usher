package team.opentech.usher.protocol.rpc.impl;

import java.util.List;
import javax.annotation.Resource;
import team.opentech.usher.pojo.DTO.DeptDTO;
import team.opentech.usher.pojo.DTO.request.PutMenusToDeptsCommand;
import team.opentech.usher.pojo.DTO.request.PutPowersToDeptCommand;
import team.opentech.usher.pojo.DTO.response.GetAllPowerWithHaveMarkDTO;
import team.opentech.usher.pojo.cqe.DefaultCQE;
import team.opentech.usher.pojo.cqe.command.IdCommand;
import team.opentech.usher.pojo.cqe.command.IdsCommand;
import team.opentech.usher.pojo.cqe.query.IdQuery;
import team.opentech.usher.protocol.rpc.DeptProvider;
import team.opentech.usher.protocol.rpc.base.BaseDefaultProvider;
import team.opentech.usher.rpc.annotation.RpcService;
import team.opentech.usher.service.BaseDoService;
import team.opentech.usher.service.DeptService;

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



