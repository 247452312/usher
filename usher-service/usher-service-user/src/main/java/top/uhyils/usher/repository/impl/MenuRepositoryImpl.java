package top.uhyils.usher.repository.impl;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import top.uhyils.usher.annotation.Repository;
import top.uhyils.usher.assembler.MenuAssembler;
import top.uhyils.usher.dao.DeptDao;
import top.uhyils.usher.dao.MenuDao;
import top.uhyils.usher.pojo.DO.DeptMenuDO;
import top.uhyils.usher.pojo.DO.MenuDO;
import top.uhyils.usher.pojo.DTO.MenuDTO;
import top.uhyils.usher.pojo.DTO.response.GetAllMenuWithHaveMarkDTO;
import top.uhyils.usher.pojo.entity.Dept;
import top.uhyils.usher.pojo.entity.Menu;
import top.uhyils.usher.pojo.entity.type.MenuIframe;
import top.uhyils.usher.repository.MenuRepository;
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
public class MenuRepositoryImpl extends AbstractRepository<Menu, MenuDO, MenuDao, MenuDTO, MenuAssembler> implements MenuRepository {


    @Autowired
    private DeptDao deptDao;

    protected MenuRepositoryImpl(MenuAssembler assembler, MenuDao dao) {
        super(assembler, dao);
    }

    @Override
    public void cleanDept(Menu menuId) {
        dao.deleteDeptMenuByMenuIds(Arrays.asList(menuId.getUnique().orElseThrow(() -> Asserts.makeException("清空dept失败,没有id"))));
    }

    @Override
    public void addDept(Menu menuId, Dept newDeptId) {
        DeptMenuDO t = new DeptMenuDO();
        t.setMenuId(menuId.getUnique().orElse(null));
        t.setDeptId(newDeptId.getUnique().orElse(null));
        t.preInsert();
        deptDao.insertDeptMenu(t);
    }

    @Override
    public List<Menu> findByIframe(MenuIframe menuIframe) {
        List<MenuDO> menus = dao.getByIFrame(menuIframe.getIframe());
        return menus.stream().map(assembler::toEntity).collect(Collectors.toList());
    }

    @Override
    public List<Menu> findByDeptId(Long identifier) {
        List<MenuDO> byDeptIds = dao.getByDeptIds(Collections.singletonList(identifier));
        return assembler.listToEntity(byDeptIds);
    }

    @Override
    public List<GetAllMenuWithHaveMarkDTO> findAllMenuWithHaveMark(Long menu) {
        return dao.getAllMenuWithHaveMark(menu);
    }
}
