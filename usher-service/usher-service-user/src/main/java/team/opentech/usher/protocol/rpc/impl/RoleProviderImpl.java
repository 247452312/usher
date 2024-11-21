package team.opentech.usher.protocol.rpc.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import team.opentech.usher.annotation.Public;
import team.opentech.usher.pojo.DTO.DeptDTO;
import team.opentech.usher.pojo.DTO.RoleDTO;
import team.opentech.usher.pojo.DTO.request.PutDeptsToRoleCommand;
import team.opentech.usher.pojo.DTO.response.GetAllDeptWithHaveMarkDTO;
import team.opentech.usher.pojo.cqe.DefaultCQE;
import team.opentech.usher.pojo.cqe.command.IdCommand;
import team.opentech.usher.pojo.cqe.command.IdsCommand;
import team.opentech.usher.pojo.cqe.query.IdQuery;
import team.opentech.usher.protocol.rpc.RoleProvider;
import team.opentech.usher.protocol.rpc.base.BaseDefaultProvider;
import team.opentech.usher.rpc.annotation.RpcService;
import team.opentech.usher.service.BaseDoService;
import team.opentech.usher.service.RoleService;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年05月27日 16时27分
 */
@RpcService
public class RoleProviderImpl extends BaseDefaultProvider<RoleDTO> implements RoleProvider {


    @Autowired
    private RoleService service;


    @Override
    @Public
    public RoleDTO getRoleByRoleId(IdQuery request) {
        return service.getRoleByRoleId(request.getId());
    }

    @Override
    public Boolean putDeptsToRole(PutDeptsToRoleCommand request) {
        return service.putDeptsToRole(request.getRoleId(), request.getDeptIds());
    }

    @Override
    public Boolean deleteRoleDept(IdsCommand idsRequest) {
        List<Long> roleDeptId = idsRequest.getIds();
        return service.deleteRoleDept(roleDeptId);
    }

    @Override
    public List<RoleDTO> getRoles(DefaultCQE request) {
        return service.getRoles();
    }

    @Override
    public List<DeptDTO> getUserDeptsByRoleId(IdQuery request) {
        return service.getUserDeptsByRoleId(request.getId());
    }

    @Override
    public List<GetAllDeptWithHaveMarkDTO> getAllDeptWithHaveMark(IdQuery request) {
        return service.getAllDeptWithHaveMark(request.getId());
    }

    @Override
    public Boolean deleteRole(IdCommand request) {
        return service.deleteRole(request.getId());
    }

    @Override
    protected BaseDoService<RoleDTO> getService() {
        return service;
    }
}
