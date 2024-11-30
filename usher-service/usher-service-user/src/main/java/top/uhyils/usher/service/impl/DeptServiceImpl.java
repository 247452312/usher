package top.uhyils.usher.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import top.uhyils.usher.annotation.ReadWriteMark;
import top.uhyils.usher.assembler.DeptAssembler;
import top.uhyils.usher.enums.ReadWriteTypeEnum;
import top.uhyils.usher.pojo.DO.DeptDO;
import top.uhyils.usher.pojo.DTO.DeptDTO;
import top.uhyils.usher.pojo.DTO.response.GetAllPowerWithHaveMarkDTO;
import top.uhyils.usher.pojo.entity.Dept;
import top.uhyils.usher.pojo.entity.Menu;
import top.uhyils.usher.pojo.entity.Power;
import top.uhyils.usher.repository.DeptRepository;
import top.uhyils.usher.service.DeptService;

/**
 * 部门(Dept)表 内部服务实现类
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月27日 08时32分23秒
 */
@Service
@ReadWriteMark(tables = {"sys_dept"})
public class DeptServiceImpl extends AbstractDoService<DeptDO, Dept, DeptDTO, DeptRepository, DeptAssembler> implements DeptService {


    public DeptServiceImpl(DeptAssembler assembler, DeptRepository repository) {
        super(assembler, repository);
    }

    @Override
    @ReadWriteMark(type = ReadWriteTypeEnum.WRITE, tables = {"sys_dept_power"})
    public Boolean putPowersToDept(Long deptId, List<Long> powerIds) {

        Dept dept = new Dept(deptId);
        // 清空之前这个部门的权限
        dept.cleanPower(rep);
        dept.addPower(powerIds.stream().map(Power::new).collect(Collectors.toList()), rep);
        return true;
    }

    @Override
    @ReadWriteMark(type = ReadWriteTypeEnum.WRITE, tables = {"sys_dept_power"})
    public Boolean deleteDeptPower(List<Long> ids) {
        rep.deleteDeptPower(ids);
        return true;
    }

    @Override
    @ReadWriteMark(type = ReadWriteTypeEnum.WRITE, tables = {"sys_dept_menu"})
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public Boolean putMenusToDept(Long deptId, List<Long> menuIds) {
        Dept dept = new Dept(deptId);
        // 清空之前这个部门的按钮
        dept.cleanMenu(rep);
        dept.addMenu(menuIds.stream().map(Menu::new).collect(Collectors.toList()), rep);
        return true;
    }

    @Override
    public List<DeptDTO> getDepts() {
        List<Dept> depts = rep.findAll();
        return depts.stream().map(assem::toDTO).filter(Objects::nonNull).collect(Collectors.toList());
    }

    @Override
    @ReadWriteMark(tables = {"sys_dept_power", "sys_power"})
    public List<GetAllPowerWithHaveMarkDTO> getAllPowerWithHaveMark(Long deptId) {
        return rep.getAllPowerWithHaveMark(new Dept(deptId));
    }

    @Override
    @ReadWriteMark(type = ReadWriteTypeEnum.WRITE, tables = {"sys_dept", "sys_dept_power", "sys_dept_menu", "sys_role_dept"})
    public Boolean deleteDept(Long deptId) {
        Dept dept = rep.find(deptId);
        dept.removeMenuLink(rep);
        dept.removePowerLink(rep);
        dept.removeRoleLink(rep);
        return true;
    }
}
