package team.opentech.usher.service;


import team.opentech.usher.pojo.DTO.PowerDTO;
import team.opentech.usher.pojo.entity.type.Identifier;
import team.opentech.usher.pojo.entity.type.InterfaceName;
import team.opentech.usher.pojo.entity.type.MethodName;
import java.util.List;

/**
 * 权限(Power)表 内部服务接口
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月27日 08时32分55秒
 */
public interface PowerService extends BaseDoService<PowerDTO> {

    /**
     * 获取所有的权限
     *
     * @return 所有权限
     */
    List<PowerDTO> getPowers();

    /**
     * 检查用户是否存在此权限
     *
     * @param interfaceName
     * @param methodName
     * @param userId
     *
     * @return 是否存在
     */
    Boolean checkUserHavePower(InterfaceName interfaceName, MethodName methodName, Identifier userId);

    /**
     * 删除权限->包括连接表
     *
     * @return
     */
    Boolean deletePower(Identifier powerId);


    /**
     * 获取所有interfaceName
     *
     * @return 所有interfaceName
     */
    List<String> getInterfaces();


    /**
     * 获取所有指定接口的方法
     *
     * @return 对应方法
     */
    List<String> getMethodNameByInterfaceName(InterfaceName interfaceName);


    /**
     * 初始化权限,如果权限不存在,则添加权限,如果权限已经存在,则不作任何修改
     *
     * @return 添加的权限
     */
    Integer initPowerInProStart();
}
