package team.opentech.usher.repository.impl;

import team.opentech.usher.annotation.Repository;
import team.opentech.usher.assembler.RoleAssembler;
import team.opentech.usher.dao.RoleDao;
import team.opentech.usher.pojo.DO.RoleDO;
import team.opentech.usher.pojo.DO.RoleDeptDO;
import team.opentech.usher.pojo.DTO.RoleDTO;
import team.opentech.usher.pojo.DTO.response.GetAllDeptWithHaveMarkDTO;
import team.opentech.usher.pojo.entity.Dept;
import team.opentech.usher.pojo.entity.Role;
import team.opentech.usher.pojo.entity.type.Identifier;
import team.opentech.usher.repository.RoleRepository;
import team.opentech.usher.repository.base.AbstractRepository;
import team.opentech.usher.util.Asserts;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * 仓库
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月24日 17时27分
 */
@Repository
public class RoleRepositoryImpl extends AbstractRepository<Role, RoleDO, RoleDao, RoleDTO, RoleAssembler> implements RoleRepository {


    protected RoleRepositoryImpl(RoleAssembler assembler, RoleDao dao) {
        super(assembler, dao);
    }

    @Override
    public void cleanDeptLink(Role roleId) {
        dao.deleteRoleDeptMiddleByRoleId(roleId.getUnique().map(t -> t.getId()).orElseThrow(() -> Asserts.makeException("角色id不存在")));
    }

    @Override
    public void addRoleDeptLink(Role roleId, List<Dept> deptIds) {
        RoleDeptDO roleDeptDO = new RoleDeptDO();
        roleDeptDO.setRoleId(roleId.getUnique().map(Identifier::getId).orElseThrow(() -> Asserts.makeException("roleId不存在")));
        for (Dept deptId : deptIds) {
            Optional<Identifier> unique = deptId.getUnique();
            unique.ifPresent(identifier -> {
                roleDeptDO.setDeptId(identifier.getId());
                roleDeptDO.preInsert();
                dao.insertRoleDept(roleDeptDO);
            });

        }
    }

    @Override
    public void removeRoleDeptLink(List<Long> ids) {
        dao.deleteRoleDept(ids);
    }

    @Override
    public List<Role> getAll() {
        List<RoleDO> all = dao.getAll();
        return all.stream().map(assembler::toEntity).collect(Collectors.toList());
    }

    @Override
    public List<Role> findRoleDeptLinkByRoleId(Role roleId) {
        List<RoleDeptDO> roleDeptDOS = dao.getRoleDeptLinkByRoleId(roleId.getUnique().map(Identifier::getId).orElseThrow(() -> Asserts.makeException("未找到roleId")));
        return roleDeptDOS.stream().map(assembler::RoleDeptToEntity).collect(Collectors.toList());
    }

    @Override
    public List<GetAllDeptWithHaveMarkDTO> findDeptWithHaveMark(Role roleId) {
        return dao.getAllDeptWithHaveMark(roleId.getUnique().map(Identifier::getId).orElseThrow(() -> Asserts.makeException("roleId不存在")));
    }


}
