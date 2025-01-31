package team.opentech.usher.dao;

import team.opentech.usher.dao.base.DefaultDao;
import team.opentech.usher.pojo.DO.PowerDO;
import java.util.ArrayList;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年04月25日 13时03分
 */
@Mapper
public interface PowerDao extends DefaultDao<PowerDO> {

    /**
     * 获取所有表权限
     *
     * @return 所有表权限
     */
    ArrayList<PowerDO> getAll();

    /**
     * 查询该用户是否有这个权限
     * 先查出权限,再查出包含此权限的权限集 再查出包含此权限集的角色,再查询出用户的数量
     *
     * @param userId        用户id
     * @param interfaceName 接口名称
     * @param methodName    方法名称
     *
     * @return 查询数量 大于0表示存在
     */
    Integer checkUserHavePower(@Param("userId") Long userId, @Param("interfaceName") String interfaceName, @Param("methodName") String methodName);

    /**
     * 根据权限id删除权限集-权限中间表
     *
     * @param id 权限id
     *
     * @return 删除数量
     */
    Integer deleteDeptPowerMiddleByPowerId(Long id);

    /**
     * 获取所有的interface
     *
     * @return 所有的interface
     */
    ArrayList<String> getInterfaces();

    /**
     * 获取指定接口的方法
     *
     * @param interfaceName 接口
     *
     * @return 对应的方法
     */
    ArrayList<String> getMethodNameByInterfaceName(String interfaceName);

    /**
     * 查询此权限是否存在
     *
     * @param interfaceName 接口名称
     * @param methodName    方法名称
     *
     * @return 数量
     */
    Integer checkPower(@Param("interfaceName") String interfaceName, @Param("methodName") String methodName);

    /**
     * 根据部门获取权限
     *
     * @param deptId
     *
     * @return
     */
    List<PowerDO> getByDept(Long deptId);
}
