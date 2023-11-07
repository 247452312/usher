package team.opentech.usher.protocol.rpc.impl;

import team.opentech.usher.annotation.Public;
import team.opentech.usher.pojo.DTO.DeptDTO;
import team.opentech.usher.pojo.DTO.RoleDTO;
import team.opentech.usher.pojo.DTO.request.PutDeptsToRoleCommand;
import team.opentech.usher.pojo.DTO.response.GetAllDeptWithHaveMarkDTO;
import team.opentech.usher.pojo.cqe.DefaultCQE;
import team.opentech.usher.pojo.cqe.command.IdCommand;
import team.opentech.usher.pojo.cqe.command.IdsCommand;
import team.opentech.usher.pojo.cqe.query.IdQuery;
import team.opentech.usher.pojo.entity.type.Identifier;
import team.opentech.usher.protocol.rpc.RoleProvider;
import team.opentech.usher.protocol.rpc.base.BaseDefaultProvider;
import team.opentech.usher.rpc.annotation.RpcService;
import team.opentech.usher.service.BaseDoService;
import team.opentech.usher.service.RoleService;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年05月27日 16时27分
 */
@RpcService
public class RoleProviderImpl extends BaseDefaultProvider<RoleDTO> implements RoleProvider {


    @Resource
    private RoleService service;


    @Override
    @Public
    public RoleDTO getRoleByRoleId(IdQuery request) {
        Identifier roleId = new Identifier(request.getId());
        return service.getRoleByRoleId(roleId);
    }

    @Override
    public Boolean putDeptsToRole(PutDeptsToRoleCommand request) {
        Identifier roleId = new Identifier(request.getRoleId());
        List<Identifier> deptIds = request.getDeptIds().stream().map(Identifier::new).collect(Collectors.toList());
        return service.putDeptsToRole(roleId, deptIds);
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
        Identifier roleId = new Identifier(request.getId());
        return service.getUserDeptsByRoleId(roleId);
    }

    @Override
    public List<GetAllDeptWithHaveMarkDTO> getAllDeptWithHaveMark(IdQuery request) {
        Identifier roleId = new Identifier(request.getId());
        return service.getAllDeptWithHaveMark(roleId);
    }

    @Override
    public Boolean deleteRole(IdCommand request) {
        Identifier roleId = new Identifier(request.getId());
        return service.deleteRole(roleId);
    }

    @Override
    protected BaseDoService<RoleDTO> getService() {
        return service;
    }
}
