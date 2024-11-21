package team.opentech.usher.pojo.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import team.opentech.usher.annotation.Default;
import team.opentech.usher.pojo.DO.DeptDO;
import team.opentech.usher.pojo.DO.RoleDO;
import team.opentech.usher.pojo.DO.RoleDeptDO;
import team.opentech.usher.pojo.DTO.response.GetAllDeptWithHaveMarkDTO;
import team.opentech.usher.pojo.entity.base.AbstractDoEntity;
import team.opentech.usher.repository.DeptRepository;
import team.opentech.usher.repository.MenuRepository;
import team.opentech.usher.repository.PowerRepository;
import team.opentech.usher.repository.RoleRepository;
import team.opentech.usher.util.Asserts;

/**
 * 角色
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月24日 17时37分
 */
public class Role extends AbstractDoEntity<RoleDO> {

    private List<Dept> depts;

    @Default
    public Role(RoleDO data) {
        super(data);
    }

    public Role(Long id) {
        super(id, new RoleDO());
    }

    public Role(RoleDeptDO roleDO) {
        super(parseRoleDeptDo(roleDO));
        this.depts = new ArrayList<>(1);
        DeptDO deptDO = new DeptDO();
        deptDO.setId(roleDO.getDeptId());
        depts.add(new Dept(deptDO));
    }

    private static RoleDO parseRoleDeptDo(RoleDeptDO roleDO) {
        RoleDO roleDo = new RoleDO();
        roleDo.setId(roleDO.getRoleId());
        return roleDo;
    }

    public void addDept(List<Dept> depts, RoleRepository repository) {
        List<Dept> newPowerIds = addDeptToEntity(depts);
        repository.addRoleDeptLink(this, newPowerIds);

    }

    /**
     * 填充部门与权限
     *
     * @param deptRepository
     * @param powerRepository
     */
    public void initDept(DeptRepository deptRepository, PowerRepository powerRepository, MenuRepository menuRepository) {
        if (this.depts != null) {
            return;
        }
        Optional<Long> unique = getUnique();
        unique.ifPresent(t -> {
            this.depts = deptRepository.findByRoleId(t);
            for (Dept dept : depts) {
                dept.initMenus(menuRepository);
                dept.initPower(powerRepository);
            }
        });

    }

    public List<Menu> menus() {
        Asserts.assertTrue(depts != null, "没有初始化部门");
        List<Menu> result = new ArrayList<>();
        for (Dept dept : depts) {
            result.addAll(dept.menus());
        }
        return result;
    }

    public void forceInitDeptIds(List<Dept> depts) {
        this.depts = depts;
    }

    public void cleanDeptLink(RoleRepository rep) {
        this.depts = null;
        rep.cleanDeptLink(this);
    }

    public List<Dept> deptIds() {
        return depts;
    }

    /**
     * 落库
     *
     * @param rep
     */
    public void mappingToDB(RoleRepository rep) {
        rep.addRoleDeptLink(this, depts);
    }

    /**
     * 填充deptId
     *
     * @param rep
     */
    public void fillDeptIds(RoleRepository rep) {
        List<Role> roles = rep.findRoleDeptLinkByRoleId(this);
        this.depts = new ArrayList<>(roles.size());
        roles.stream().map(t -> t.depts).forEach(t -> this.depts.addAll(t));
    }

    public List<GetAllDeptWithHaveMarkDTO> toDeptWithHaveMark(RoleRepository rep) {
        return rep.findDeptWithHaveMark(this);
    }

    public void removeSelf(RoleRepository rep) {
        Optional<Long> unique = getUnique();
        Asserts.assertTrue(unique.isPresent(), "唯一标识不存在,不能移除自身");
        rep.remove(unique.get());
    }

    private List<Dept> addDeptToEntity(List<Dept> depts) {
        if (this.depts == null) {
            this.depts = new ArrayList<>(depts.size());
        }
        List<Dept> result = new ArrayList<>();
        for (Dept newDeptId : depts) {
            if (this.depts.contains(newDeptId)) {
                continue;
            }
            this.depts.add(newDeptId);
            result.add(newDeptId);
        }
        return result;
    }

}
