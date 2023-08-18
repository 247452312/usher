package team.opentech.usher.repository;

import team.opentech.usher.pojo.DO.MenuDO;
import team.opentech.usher.pojo.DTO.response.GetAllMenuWithHaveMarkDTO;
import team.opentech.usher.pojo.entity.Dept;
import team.opentech.usher.pojo.entity.Menu;
import team.opentech.usher.pojo.entity.type.Identifier;
import team.opentech.usher.pojo.entity.type.MenuIframe;
import team.opentech.usher.repository.base.BaseEntityRepository;
import java.util.List;

/**
 * 权限仓库
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月24日 17时46分
 */
public interface MenuRepository extends BaseEntityRepository<MenuDO, Menu> {

    /**
     * 清空按钮
     *
     * @param menuId
     */
    void cleanDept(Menu menuId);

    /**
     * 给按钮添加部门
     *
     * @param menuId
     * @param newDeptId
     */
    void addDept(Menu menuId, Dept newDeptId);

    /**
     * 根据iframe获取
     *
     * @param menuIframe
     *
     * @return
     */
    List<Menu> findByIframe(MenuIframe menuIframe);

    /**
     * 根据deptId获取
     *
     * @param identifier
     *
     * @return
     */
    List<Menu> findByDeptId(Identifier identifier);

    /**
     * 查询
     *
     * @param menu
     *
     * @return
     */
    List<GetAllMenuWithHaveMarkDTO> findAllMenuWithHaveMark(Identifier menu);
}
