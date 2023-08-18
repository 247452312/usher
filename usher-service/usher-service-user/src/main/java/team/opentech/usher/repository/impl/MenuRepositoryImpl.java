package team.opentech.usher.repository.impl;

import team.opentech.usher.annotation.Repository;
import team.opentech.usher.assembler.MenuAssembler;
import team.opentech.usher.dao.DeptDao;
import team.opentech.usher.dao.MenuDao;
import team.opentech.usher.pojo.DO.DeptMenuDO;
import team.opentech.usher.pojo.DO.MenuDO;
import team.opentech.usher.pojo.DTO.MenuDTO;
import team.opentech.usher.pojo.DTO.response.GetAllMenuWithHaveMarkDTO;
import team.opentech.usher.pojo.entity.Dept;
import team.opentech.usher.pojo.entity.Menu;
import team.opentech.usher.pojo.entity.type.Identifier;
import team.opentech.usher.pojo.entity.type.MenuIframe;
import team.opentech.usher.repository.MenuRepository;
import team.opentech.usher.repository.base.AbstractRepository;
import team.opentech.usher.util.Asserts;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;


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
        dao.deleteDeptMenuByMenuIds(Arrays.asList(menuId.getUnique().map(Identifier::getId).orElseThrow(() -> Asserts.makeException("清空dept失败,没有id"))));
    }

    @Override
    public void addDept(Menu menuId, Dept newDeptId) {
        DeptMenuDO t = new DeptMenuDO();
        t.setMenuId(menuId.getUnique().map(Identifier::getId).orElse(null));
        t.setDeptId(newDeptId.getUnique().map(Identifier::getId).orElse(null));
        t.preInsert();
        deptDao.insertDeptMenu(t);
    }

    @Override
    public List<Menu> findByIframe(MenuIframe menuIframe) {
        List<MenuDO> menus = dao.getByIFrame(menuIframe.getIframe());
        return menus.stream().map(assembler::toEntity).collect(Collectors.toList());
    }

    @Override
    public List<Menu> findByDeptId(Identifier identifier) {
        List<MenuDO> byDeptIds = dao.getByDeptIds(Collections.singletonList(identifier.getId()));
        return assembler.listToEntity(byDeptIds);
    }

    @Override
    public List<GetAllMenuWithHaveMarkDTO> findAllMenuWithHaveMark(Identifier menu) {
        return dao.getAllMenuWithHaveMark(menu.getId());
    }
}
