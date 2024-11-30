package top.uhyils.usher.repository.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import top.uhyils.usher.annotation.Repository;
import top.uhyils.usher.assembler.RoleAssembler;
import top.uhyils.usher.dao.RoleDao;
import top.uhyils.usher.pojo.DO.RoleDO;
import top.uhyils.usher.pojo.DO.RoleDeptDO;
import top.uhyils.usher.pojo.DTO.RoleDTO;
import top.uhyils.usher.pojo.DTO.response.GetAllDeptWithHaveMarkDTO;
import top.uhyils.usher.pojo.entity.Dept;
import top.uhyils.usher.pojo.entity.Role;
import top.uhyils.usher.repository.RoleRepository;
import top.uhyils.usher.repository.base.AbstractRepository;
import top.uhyils.usher.util.Asserts;


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
        dao.deleteRoleDeptMiddleByRoleId(roleId.getUnique().orElseThrow(() -> Asserts.makeException("角色id不存在")));
    }

    @Override
    public void addRoleDeptLink(Role roleId, List<Dept> deptIds) {
        RoleDeptDO roleDeptDO = new RoleDeptDO();
        roleDeptDO.setRoleId(roleId.getUnique().orElseThrow(() -> Asserts.makeException("roleId不存在")));
        for (Dept deptId : deptIds) {
            Optional<Long> unique = deptId.getUnique();
            unique.ifPresent(identifier -> {
                roleDeptDO.setDeptId(identifier);
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
        List<RoleDeptDO> roleDeptDOS = dao.getRoleDeptLinkByRoleId(roleId.getUnique().orElseThrow(() -> Asserts.makeException("未找到roleId")));
        return roleDeptDOS.stream().map(assembler::RoleDeptToEntity).collect(Collectors.toList());
    }

    @Override
    public List<GetAllDeptWithHaveMarkDTO> findDeptWithHaveMark(Role roleId) {
        return dao.getAllDeptWithHaveMark(roleId.getUnique().orElseThrow(() -> Asserts.makeException("roleId不存在")));
    }


}
