package top.uhyils.usher.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import top.uhyils.usher.annotation.ReadWriteMark;
import top.uhyils.usher.assembler.ContentAssembler;
import top.uhyils.usher.assembler.MenuAssembler;
import top.uhyils.usher.assembler.UserAssembler;
import top.uhyils.usher.context.LoginInfoHelper;
import top.uhyils.usher.enums.ReadWriteTypeEnum;
import top.uhyils.usher.pojo.DO.MenuDO;
import top.uhyils.usher.pojo.DTO.MenuDTO;
import top.uhyils.usher.pojo.DTO.MenuTreeBuilder;
import top.uhyils.usher.pojo.DTO.response.GetAllMenuWithHaveMarkDTO;
import top.uhyils.usher.pojo.DTO.response.GetDeptsByMenuIdDTO;
import top.uhyils.usher.pojo.DTO.response.IndexMenuTreeDTO;
import top.uhyils.usher.pojo.DTO.response.MenuHtmlTreeDTO;
import top.uhyils.usher.pojo.DTO.response.info.MenuHomeInfo;
import top.uhyils.usher.pojo.DTO.response.info.MenuLogoInfo;
import top.uhyils.usher.pojo.DTO.response.info.MenuTreeDTO;
import top.uhyils.usher.pojo.entity.Content;
import top.uhyils.usher.pojo.entity.Dept;
import top.uhyils.usher.pojo.entity.Menu;
import top.uhyils.usher.pojo.entity.User;
import top.uhyils.usher.pojo.entity.type.Iframe;
import top.uhyils.usher.pojo.entity.type.MenuIframe;
import top.uhyils.usher.repository.ContentRepository;
import top.uhyils.usher.repository.DeptRepository;
import top.uhyils.usher.repository.MenuRepository;
import top.uhyils.usher.repository.PowerRepository;
import top.uhyils.usher.repository.RoleRepository;
import top.uhyils.usher.service.MenuService;

/**
 * 菜单(Menu)表 内部服务实现类
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月27日 08时32分48秒
 */
@Service
@ReadWriteMark(tables = {"sys_menu"})
public class MenuServiceImpl extends AbstractDoService<MenuDO, Menu, MenuDTO, MenuRepository, MenuAssembler> implements MenuService {

    /**
     * 主页显示菜单code
     */
    private static final Integer INDEX_IFRAME = 1;

    @Autowired
    private UserAssembler userAssembler;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private DeptRepository deptRepository;


    @Autowired
    private PowerRepository powerRepository;

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private ContentRepository contentRepository;

    @Autowired
    private ContentAssembler contentAssembler;


    public MenuServiceImpl(MenuAssembler assembler, MenuRepository repository) {
        super(assembler, repository);
    }


    @Override
    @ReadWriteMark(type = ReadWriteTypeEnum.WRITE, tables = {"sys_dept_menu"})
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public Boolean putDeptsToMenu(Long menuId, List<Long> deptIds) {
        Menu menu = new Menu(menuId);
        menu.cleanDept(rep);
        menu.addDepts(deptIds.stream().map(Dept::new).collect(Collectors.toList()), rep);
        return true;
    }

    @Override
    @ReadWriteMark(tables = {"sys_menu", "sys_content", "sys_dept", "sys_role_dept"})
    public IndexMenuTreeDTO getIndexMenu() {
        // 初始化角色的权限
        User user = userAssembler.toEntity(LoginInfoHelper.doGet());
        user.initRole(roleRepository, deptRepository, powerRepository, menuRepository);

        /* 1. 全取出来 */
        List<Menu> menus = rep.findByIframe(new MenuIframe(INDEX_IFRAME));
        /* 2. 只取出来有用的 */
        menus = user.screenMenu(menus);
        /* 4. tree */
        MenuTreeBuilder menuTreeBuilder = new MenuTreeBuilder();
        menuTreeBuilder.addMenu(menus.stream().map(assem::toDTO).collect(Collectors.toList()));
        List<MenuTreeDTO> menuTree = menuTreeBuilder.build();

        Content homeInfo = contentRepository.getByName("honeInfo");
        Content logoInfo = contentRepository.getByName("logoInfo");

        MenuHomeInfo menuHomeInfo = MenuHomeInfo.build(contentAssembler.toDTO(homeInfo));
        MenuLogoInfo menuLogoInfo = MenuLogoInfo.build(contentAssembler.toDTO(logoInfo));

        /* 5. 返回 */
        return IndexMenuTreeDTO.build(menuHomeInfo, menuLogoInfo, menuTree);
    }

    @Override
    @ReadWriteMark(tables = {"sys_menu", "sys_content", "sys_dept", "sys_role_dept"})
    public MenuHtmlTreeDTO getMenuTree(Iframe iframe) {
        // 初始化角色的权限
        User user = userAssembler.toEntity(LoginInfoHelper.doGet());
        user.initRole(roleRepository, deptRepository, powerRepository, menuRepository);

        /* 1. 全取出来 */
        List<Menu> menus = rep.findByIframe(new MenuIframe(iframe.getIframe()));
        /* 2. 只取出来有用的 */
        menus = user.screenMenu(menus);
        List<MenuDTO> menuDTOS = menus.stream().map(assem::toDTO).collect(Collectors.toList());
        return MenuHtmlTreeDTO.build(menuDTOS);
    }

    @Override
    @ReadWriteMark(tables = {"sys_dept_menu", "sys_menu"})
    public List<GetAllMenuWithHaveMarkDTO> getAllMenuWithHaveMark(Long deptId) {
        return rep.findAllMenuWithHaveMark(deptId);
    }

    @Override
    @ReadWriteMark(type = ReadWriteTypeEnum.WRITE, tables = {"sys_dept_menu"})
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public Boolean removeMenu(Long menuId) {
        /* 注:开启了事务 即@Transactional 参数propagation->事务传播类型,其中Propagation.REQUIRED为如果事务不存在,则创建新事物,如果事务存在,则加入
           isolation事务隔离级别 Isolation.DEFAULT默认隔离级别 */

        Menu menu = new Menu(menuId);
        menu.completion(rep);
        // 清空对应的连接
        menu.cleanDept(rep);
        // 删除自己以及子节点
        menu.removeSelf(rep, assem);
        return true;
    }


    @Override
    @ReadWriteMark(type = ReadWriteTypeEnum.WRITE, tables = {"sys_dept_menu", "sys_dept"})
    public List<GetDeptsByMenuIdDTO> getDeptsByMenuId(Long menuId) {
        return deptRepository.findByMenuId(new Menu(menuId));
    }

}
