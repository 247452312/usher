package team.opentech.usher.service.impl;

import team.opentech.usher.annotation.ReadWriteMark;
import team.opentech.usher.assembler.DeptAssembler;
import team.opentech.usher.assembler.RoleAssembler;
import team.opentech.usher.enums.ReadWriteTypeEnum;
import team.opentech.usher.pojo.DO.RoleDO;
import team.opentech.usher.pojo.DTO.DeptDTO;
import team.opentech.usher.pojo.DTO.RoleDTO;
import team.opentech.usher.pojo.DTO.response.GetAllDeptWithHaveMarkDTO;
import team.opentech.usher.pojo.entity.Dept;
import team.opentech.usher.pojo.entity.Role;
import team.opentech.usher.pojo.entity.base.BaseEntity;
import team.opentech.usher.pojo.entity.type.Identifier;
import team.opentech.usher.repository.DeptRepository;
import team.opentech.usher.repository.RoleRepository;
import team.opentech.usher.service.RoleService;
import team.opentech.usher.util.Asserts;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public RoleDTO getRoleByRoleId(Identifier roleId) {
        Role role = new Role(roleId);
        role.completion(rep);
        Optional<RoleDO> roleDOOpt = role.toData();
        Asserts.assertTrue(roleDOOpt.isPresent(), "查询失败");
        return assem.toDTO(roleDOOpt.get());
    }

    @Override
    @ReadWriteMark(type = ReadWriteTypeEnum.WRITE, tables = {"sys_role_dept"})
    public Boolean putDeptsToRole(Identifier roleId, List<Identifier> deptIds) {
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
    public List<DeptDTO> getUserDeptsByRoleId(Identifier roleId) {
        Role role = new Role(roleId);
        role.fillDeptIds(rep);
        List<Dept> deptIds = role.deptIds();
        List<Dept> list = deptRepository.find(deptIds.stream().map(BaseEntity::getUnique).map(Optional::get).collect(Collectors.toList()));
        return list.stream().map(deptAssembler::toDTO).collect(Collectors.toList());
    }

    @Override
    @ReadWriteMark(type = ReadWriteTypeEnum.READ, tables = {"sys_role_dept", "sys_dept"})
    public List<GetAllDeptWithHaveMarkDTO> getAllDeptWithHaveMark(Identifier roleId) {
        Role role = new Role(roleId);
        return role.toDeptWithHaveMark(rep);
    }

    @Override
    @ReadWriteMark(type = ReadWriteTypeEnum.WRITE, tables = {"sys_role_dept", "sys_user"})
    public Boolean deleteRole(Identifier roleId) {
        Role role = new Role(roleId);
        role.cleanDeptLink(rep);
        role.removeSelf(rep);
        return true;
    }

}
