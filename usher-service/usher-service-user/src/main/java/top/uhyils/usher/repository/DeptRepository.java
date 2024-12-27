package top.uhyils.usher.repository;

import java.util.List;
import top.uhyils.usher.pojo.DO.DeptDO;
import top.uhyils.usher.pojo.DTO.response.GetAllPowerWithHaveMarkDTO;
import top.uhyils.usher.pojo.DTO.response.GetDeptsByMenuIdDTO;
import top.uhyils.usher.pojo.entity.Dept;
import top.uhyils.usher.pojo.entity.Menu;
import top.uhyils.usher.pojo.entity.Power;
import top.uhyils.usher.repository.base.BaseEntityRepository;

/**
 * 角色仓库
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月24日 17时46分
 */
public interface DeptRepository extends BaseEntityRepository<DeptDO, Dept> {

    /**
     * 根据角色id获取全部dept
     *
     * @param roleId
     *
     * @return
     */
    List<Dept> findByRoleId(Long roleId);

    /**
     * 添加新power
     *
     * @param dept
     * @param power
     */
    void addPowers(Dept dept, Power power);

    /**
     * 清空权限
     *
     * @param deptId
     */
    void cleanPower(Dept deptId);

    /**
     * 删除deptPower
     *
     * @param ids
     */
    void deleteDeptPower(List<Long> ids);

    /**
     * 清空按钮
     *
     * @param deptId
     */
    void cleanMenu(Dept deptId);

    /**
     * 添加新按钮
     *
     * @param deptId
     * @param newPowerId
     */
    void addMenu(Dept deptId, Menu newPowerId);

    /**
     * 根据按钮id查询
     *
     * @param menuId
     *
     * @return
     */
    List<GetDeptsByMenuIdDTO> findByMenuId(Menu menuId);

    /**
     * 获取全部部门
     *
     * @return
     */
    List<Dept> findAll();

    /**
     * 根据haveMark获取Power
     *
     * @param deptId
     *
     * @return
     */
    List<GetAllPowerWithHaveMarkDTO> getAllPowerWithHaveMark(Dept deptId);

    /**
     * 清除部门和role的关系
     *
     * @param dept
     */
    void cleanRole(Dept dept);
}
