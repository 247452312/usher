package team.opentech.usher.repository;

import team.opentech.usher.pojo.DO.PowerDO;
import team.opentech.usher.pojo.entity.Power;
import team.opentech.usher.pojo.entity.User;
import team.opentech.usher.pojo.entity.type.Identifier;
import team.opentech.usher.pojo.entity.type.InterfaceName;
import team.opentech.usher.pojo.entity.type.MethodName;
import team.opentech.usher.pojo.entity.type.PowerInfo;
import team.opentech.usher.repository.base.BaseEntityRepository;
import java.util.List;

/**
 * 权限仓库
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月24日 17时46分
 */
public interface PowerRepository extends BaseEntityRepository<PowerDO, Power> {

    /**
     * 根据部门id获取权限集
     *
     * @param deptId
     *
     * @return
     */
    List<Power> findByDeptId(Identifier deptId);

    /**
     * 获取全部权限
     *
     * @return
     */
    List<Power> getAll();

    /**
     * 检查指定用户是否有power权限
     *
     * @param userId
     * @param powerInfo
     *
     * @return
     */
    Boolean havePower(User userId, PowerInfo powerInfo);

    /**
     * 删除权限
     *
     * @param powerId
     */
    void removeDeptPowerByPowerId(Power powerId);

    /**
     * 获取全部接口
     *
     * @return
     */
    List<String> getInterfaces();

    /**
     * 根据接口获取方法名称
     *
     * @param interfaceName
     *
     * @return
     */
    List<MethodName> getMethodNameByInterfaceName(InterfaceName interfaceName);

    /**
     * 检查是否合法,即是否存在
     *
     * @param powerInfo
     *
     * @return
     */
    Boolean exist(PowerInfo powerInfo);
}
