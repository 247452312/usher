package top.uhyils.usher.repository.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import top.uhyils.usher.annotation.Repository;
import top.uhyils.usher.assembler.PowerAssembler;
import top.uhyils.usher.dao.PowerDao;
import top.uhyils.usher.pojo.DO.PowerDO;
import top.uhyils.usher.pojo.DTO.PowerDTO;
import top.uhyils.usher.pojo.entity.Power;
import top.uhyils.usher.pojo.entity.User;
import top.uhyils.usher.pojo.entity.type.InterfaceName;
import top.uhyils.usher.pojo.entity.type.MethodName;
import top.uhyils.usher.pojo.entity.type.PowerInfo;
import top.uhyils.usher.repository.PowerRepository;
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
public class PowerRepositoryImpl extends AbstractRepository<Power, PowerDO, PowerDao, PowerDTO, PowerAssembler> implements PowerRepository {


    protected PowerRepositoryImpl(PowerAssembler assembler, PowerDao dao) {
        super(assembler, dao);
    }

    @Override
    public List<Power> findByDeptId(Long deptId) {
        List<PowerDO> powerDOS = dao.getByDept(deptId);
        return assembler.listToEntity(powerDOS);
    }

    @Override
    public List<Power> getAll() {
        ArrayList<PowerDO> all = dao.getAll();
        return all.stream().map(assembler::toEntity).collect(Collectors.toList());
    }

    @Override
    public Boolean havePower(User userId, PowerInfo powerInfo) {
        return dao.checkUserHavePower(
            userId.getUnique().orElseThrow(() -> Asserts.makeException("未找到用户id")),
            powerInfo.getInterfaceName(),
            powerInfo.getMethodName())
               != 0;
    }

    @Override
    public void removeDeptPowerByPowerId(Power powerId) {
        dao.deleteDeptPowerMiddleByPowerId(powerId.getUnique().orElseThrow(() -> Asserts.makeException("未找到权限id")));
    }

    @Override
    public List<String> getInterfaces() {
        return dao.getInterfaces();
    }

    @Override
    public List<MethodName> getMethodNameByInterfaceName(InterfaceName interfaceName) {
        ArrayList<String> methodNameByInterfaceName = dao.getMethodNameByInterfaceName(interfaceName.getInterfaceName());
        return methodNameByInterfaceName.stream().map(MethodName::new).collect(Collectors.toList());
    }

    @Override
    public Boolean exist(PowerInfo powerInfo) {
        return dao.checkPower(powerInfo.getInterfaceName(), powerInfo.getMethodName()) != 0;
    }
}
