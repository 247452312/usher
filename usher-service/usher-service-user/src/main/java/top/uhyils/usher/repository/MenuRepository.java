package top.uhyils.usher.repository;

import java.util.List;
import top.uhyils.usher.pojo.DO.MenuDO;
import top.uhyils.usher.pojo.DTO.response.GetAllMenuWithHaveMarkDTO;
import top.uhyils.usher.pojo.entity.Dept;
import top.uhyils.usher.pojo.entity.Menu;
import top.uhyils.usher.pojo.entity.type.MenuIframe;
import top.uhyils.usher.repository.base.BaseEntityRepository;

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
    List<Menu> findByDeptId(Long identifier);

    /**
     * 查询
     *
     * @param menu
     *
     * @return
     */
    List<GetAllMenuWithHaveMarkDTO> findAllMenuWithHaveMark(Long menu);
}
