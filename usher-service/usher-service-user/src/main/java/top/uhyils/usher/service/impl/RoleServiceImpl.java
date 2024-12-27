package top.uhyils.usher.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.uhyils.usher.annotation.ReadWriteMark;
import top.uhyils.usher.assembler.DeptAssembler;
import top.uhyils.usher.assembler.RoleAssembler;
import top.uhyils.usher.enums.ReadWriteTypeEnum;
import top.uhyils.usher.pojo.DO.RoleDO;
import top.uhyils.usher.pojo.DTO.DeptDTO;
import top.uhyils.usher.pojo.DTO.RoleDTO;
import top.uhyils.usher.pojo.DTO.response.GetAllDeptWithHaveMarkDTO;
import top.uhyils.usher.pojo.entity.Dept;
import top.uhyils.usher.pojo.entity.Role;
import top.uhyils.usher.pojo.entity.base.BaseEntity;
import top.uhyils.usher.repository.DeptRepository;
import top.uhyils.usher.repository.RoleRepository;
import top.uhyils.usher.service.RoleService;
import top.uhyils.usher.util.Asserts;

/**
 * 角色(Role)表 内部服务实现类
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月27日 08时32分59秒
 */
@Service
@ReadWriteMark(tables = {"sys_role"})
public class RoleServiceImpl extends AbstractDoService<RoleDO, Role, RoleDTO, RoleRepository, RoleAssembler> implements RoleService {

    @Autowired
    private DeptRepository deptRepository;

    @Autowired
    private DeptAssembler deptAssembler;

    public RoleServiceImpl(RoleAssembler assembler, RoleRepository repository) {
        super(assembler, repository);
    }

    @Override
    @ReadWriteMark(type = ReadWriteTypeEnum.WRITE, tables = {"sys_role_dept", "sys_role_dept"})
    public RoleDTO getRoleByRoleId(Long roleId) {
        Role role = new Role(roleId);
        role.completion(rep);
        Optional<RoleDO> roleDOOpt = role.toData();
        Asserts.assertTrue(roleDOOpt.isPresent(), "查询失败");
        return assem.toDTO(roleDOOpt.get());
    }

    @Override
    @ReadWriteMark(type = ReadWriteTypeEnum.WRITE, tables = {"sys_role_dept"})
    public Boolean putDeptsToRole(Long roleId, List<Long> deptIds) {
        Role role = new Role(roleId);

        role.cleanDeptLink(rep);
        role.forceInitDeptIds(deptIds.stream().map(Dept::new).collect(Collectors.toList()));
        role.mappingToDB(rep);
        return true;
    }

    @Override
    public Boolean deleteRoleDept(List<Long> roleDeptId) {
        rep.removeRoleDeptLink(roleDeptId);
        return true;
    }

    @Override
    public List<RoleDTO> getRoles() {
        List<Role> roles = rep.getAll();
        return roles.stream().map(assem::toDTO).collect(Collectors.toList());
    }

    @Override
    @ReadWriteMark(type = ReadWriteTypeEnum.READ, tables = {"sys_dept", "sys_role_dept"})
    public List<DeptDTO> getUserDeptsByRoleId(Long roleId) {
        Role role = new Role(roleId);
        role.fillDeptIds(rep);
        List<Dept> deptIds = role.deptIds();
        List<Dept> list = deptRepository.find(deptIds.stream().map(BaseEntity::getUnique).map(Optional::get).collect(Collectors.toList()));
        return list.stream().map(deptAssembler::toDTO).collect(Collectors.toList());
    }

    @Override
    @ReadWriteMark(type = ReadWriteTypeEnum.READ, tables = {"sys_role_dept", "sys_dept"})
    public List<GetAllDeptWithHaveMarkDTO> getAllDeptWithHaveMark(Long roleId) {
        Role role = new Role(roleId);
        return role.toDeptWithHaveMark(rep);
    }

    @Override
    @ReadWriteMark(type = ReadWriteTypeEnum.WRITE, tables = {"sys_role_dept", "sys_user"})
    public Boolean deleteRole(Long roleId) {
        Role role = new Role(roleId);
        role.cleanDeptLink(rep);
        role.removeSelf(rep);
        return true;
    }

}
