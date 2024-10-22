package team.opentech.usher.repository.impl;

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
import team.opentech.usher.pojo.entity.type.Identifier;
import team.opentech.usher.repository.DeptRepository;
import team.opentech.usher.repository.base.AbstractRepository;
import team.opentech.usher.util.Asserts;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


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
    public List<Dept> findByRoleId(Identifier roleId) {
        List<DeptDO> depts = dao.getByRoleId(roleId.getId());
        return assembler.listToEntity(depts);
    }

    @Override
    public void addPowers(Dept deptId, Power power) {
        DeptPowerDO middle = new DeptPowerDO();
        middle.setDeptId(deptId.getUnique().map(Identifier::getId).orElse(null));
        middle.setPowerId(power.getUnique().map(Identifier::getId).orElse(null));
        middle.preInsert();
        dao.insertDeptPower(middle);
    }

    @Override
    public void cleanPower(Dept deptId) {
        dao.deleteDeptPowerMiddleByDeptId(deptId.getUnique().map(Identifier::getId).orElseThrow(() -> Asserts.makeException("未找到deptId")));
    }

    @Override
    public void deleteDeptPower(List<Long> ids) {
        dao.deleteDeptPower(ids);
    }

    @Override
    public void cleanMenu(Dept deptEntity) {
        Long deptId = deptEntity.getUnique().map(Identifier::getId).orElseThrow(() -> Asserts.makeException("未找到deptId"));
        dao.deleteDeptMenuMiddleByDeptId(deptId);
    }

    @Override
    public void addMenu(Dept deptId, Menu menuId) {
        DeptMenuDO dO = new DeptMenuDO();
        dO.setDeptId(deptId.getUnique().map(Identifier::getId).orElse(null));
        dO.setMenuId(menuId.getUnique().map(Identifier::getId).orElse(null));
        dO.preInsert();
        dao.insertDeptMenu(dO);
    }

    @Override
    public List<GetDeptsByMenuIdDTO> findByMenuId(Menu menuId) {
        return dao.getByMenuId(menuId.getUnique().map(Identifier::getId).orElseThrow(() -> Asserts.makeException("未找到menuId")));
    }

    @Override
    public List<Dept> findAll() {
        ArrayList<DeptDO> all = dao.getAll();
        return all.stream().map(assembler::toEntity).collect(Collectors.toList());
    }

    @Override
    public List<GetAllPowerWithHaveMarkDTO> getAllPowerWithHaveMark(Dept deptId) {
        return dao.getAllPowerWithHaveMark(deptId.getUnique().map(Identifier::getId).orElseThrow(() -> Asserts.makeException("未找到deptId")));
    }

    @Override
    public void cleanRole(Dept dept) {
        dao.deleteRoleDeptMiddleByDeptId(dept.getUnique().map(Identifier::getId).orElseThrow(() -> Asserts.makeException("未找到deptId")));
    }


}
