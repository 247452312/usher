package top.uhyils.usher.protocol.rpc.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import top.uhyils.usher.annotation.Public;
import top.uhyils.usher.pojo.DTO.DeptDTO;
import top.uhyils.usher.pojo.DTO.RoleDTO;
import top.uhyils.usher.pojo.DTO.request.PutDeptsToRoleCommand;
import top.uhyils.usher.pojo.DTO.response.GetAllDeptWithHaveMarkDTO;
import top.uhyils.usher.pojo.cqe.DefaultCQE;
import top.uhyils.usher.pojo.cqe.command.IdCommand;
import top.uhyils.usher.pojo.cqe.command.IdsCommand;
import top.uhyils.usher.pojo.cqe.query.IdQuery;
import top.uhyils.usher.protocol.rpc.RoleProvider;
import top.uhyils.usher.protocol.rpc.base.BaseDefaultProvider;
import top.uhyils.usher.rpc.annotation.RpcService;
import top.uhyils.usher.service.BaseDoService;
import top.uhyils.usher.service.RoleService;

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
