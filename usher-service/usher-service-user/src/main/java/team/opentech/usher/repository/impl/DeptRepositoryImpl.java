package team.opentech.usher.repository.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import team.opentech.usher.annotation.Repository;
import team.opentech.usher.assembler.DeptAssembler;
import team.opentech.usher.dao.DeptDao;
import team.opentech.usher.pojo.DO.DeptDO;
import team.opentech.usher.pojo.DO.DeptMenuDO;
import team.opentech.usher.pojo.DO.DeptPowerDO;
import team.opentech.usher.pojo.DTO.DeptDTO;
import team.opentech.usher.pojo.DTO.response.GetAllPowerWithHaveMarkDTO;
import team.opentech.usher.pojo.DTO.response.GetDeptsByMenuIdDTO;
import team.opentech.usher.pojo.entity.Dept;
import team.opentech.usher.pojo.entity.Menu;
import team.opentech.usher.pojo.entity.Power;
import team.opentech.usher.repository.DeptRepository;
import team.opentech.usher.repository.base.AbstractRepository;
import team.opentech.usher.util.Asserts;


/**
 * 仓库
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月24日 17时27分
 */
@Repository
public class DeptRepositoryImpl extends AbstractRepository<Dept, DeptDO, DeptDao, DeptDTO, DeptAssembler> implements DeptRepository {


    protected DeptRepositoryImpl(DeptAssembler assembler, DeptDao dao) {
        super(assembler, dao);
    }

    @Override
    public List<Dept> findByRoleId(Long roleId) {
        List<DeptDO> depts = dao.getByRoleId(roleId);
        return assembler.listToEntity(depts);
    }

    @Override
    public void addPowers(Dept deptId, Power power) {
        DeptPowerDO middle = new DeptPowerDO();
        middle.setDeptId(deptId.getUnique().get());
        middle.setPowerId(power.getUnique().get());
        middle.preInsert();
        dao.insertDeptPower(middle);
    }

    @Override
    public void cleanPower(Dept deptId) {
        dao.deleteDeptPowerMiddleByDeptId(deptId.getUnique().orElseThrow(() -> Asserts.makeException("未找到deptId")));
    }

    @Override
    public void deleteDeptPower(List<Long> ids) {
        dao.deleteDeptPower(ids);
    }

    @Override
    public void cleanMenu(Dept deptEntity) {
        Long deptId = deptEntity.getUnique().orElseThrow(() -> Asserts.makeException("未找到deptId"));
        dao.deleteDeptMenuMiddleByDeptId(deptId);
    }

    @Override
    public void addMenu(Dept deptId, Menu menuId) {
        DeptMenuDO dO = new DeptMenuDO();
        dO.setDeptId(deptId.getUnique().get());
        dO.setMenuId(menuId.getUnique().get());
        dO.preInsert();
        dao.insertDeptMenu(dO);
    }

    @Override
    public List<GetDeptsByMenuIdDTO> findByMenuId(Menu menuId) {
        return dao.getByMenuId(menuId.getUnique().orElseThrow(() -> Asserts.makeException("未找到menuId")));
    }

    @Override
    public List<Dept> findAll() {
        ArrayList<DeptDO> all = dao.getAll();
        return all.stream().map(assembler::toEntity).collect(Collectors.toList());
    }

    @Override
    public List<GetAllPowerWithHaveMarkDTO> getAllPowerWithHaveMark(Dept deptId) {
        return dao.getAllPowerWithHaveMark(deptId.getUnique().orElseThrow(() -> Asserts.makeException("未找到deptId")));
    }

    @Override
    public void cleanRole(Dept dept) {
        dao.deleteRoleDeptMiddleByDeptId(dept.getUnique().orElseThrow(() -> Asserts.makeException("未找到deptId")));
    }


}
