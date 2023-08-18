package team.opentech.usher.repository.impl;

import team.opentech.usher.annotation.Repository;
import team.opentech.usher.assembler.PowerAssembler;
import team.opentech.usher.dao.PowerDao;
import team.opentech.usher.pojo.DO.PowerDO;
import team.opentech.usher.pojo.DTO.PowerDTO;
import team.opentech.usher.pojo.entity.Power;
import team.opentech.usher.pojo.entity.User;
import team.opentech.usher.pojo.entity.type.Identifier;
import team.opentech.usher.pojo.entity.type.InterfaceName;
import team.opentech.usher.pojo.entity.type.MethodName;
import team.opentech.usher.pojo.entity.type.PowerInfo;
import team.opentech.usher.repository.PowerRepository;
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
public class PowerRepositoryImpl extends AbstractRepository<Power, PowerDO, PowerDao, PowerDTO, PowerAssembler> implements PowerRepository {


    protected PowerRepositoryImpl(PowerAssembler assembler, PowerDao dao) {
        super(assembler, dao);
    }

    @Override
    public List<Power> findByDeptId(Identifier deptId) {
        List<PowerDO> powerDOS = dao.getByDept(deptId.getId());
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
            userId.getUnique().map(Identifier::getId).orElseThrow(() -> Asserts.makeException("未找到用户id")),
            powerInfo.getInterfaceName(),
            powerInfo.getMethodName())
               != 0;
    }

    @Override
    public void removeDeptPowerByPowerId(Power powerId) {
        dao.deleteDeptPowerMiddleByPowerId(powerId.getUnique().map(Identifier::getId).orElseThrow(() -> Asserts.makeException("未找到权限id")));
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
