package team.opentech.usher.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import team.opentech.usher.annotation.ReadWriteMark;
import team.opentech.usher.assembler.ContentAssembler;
import team.opentech.usher.assembler.MenuAssembler;
import team.opentech.usher.assembler.UserAssembler;
import team.opentech.usher.context.UserInfoHelper;
import team.opentech.usher.enums.ReadWriteTypeEnum;
import team.opentech.usher.pojo.DO.MenuDO;
import team.opentech.usher.pojo.DTO.MenuDTO;
import team.opentech.usher.pojo.DTO.MenuTreeBuilder;
import team.opentech.usher.pojo.DTO.response.GetAllMenuWithHaveMarkDTO;
import team.opentech.usher.pojo.DTO.response.GetDeptsByMenuIdDTO;
import team.opentech.usher.pojo.DTO.response.IndexMenuTreeDTO;
import team.opentech.usher.pojo.DTO.response.MenuHtmlTreeDTO;
import team.opentech.usher.pojo.DTO.response.info.MenuHomeInfo;
import team.opentech.usher.pojo.DTO.response.info.MenuLogoInfo;
import team.opentech.usher.pojo.DTO.response.info.MenuTreeDTO;
import team.opentech.usher.pojo.entity.Content;
import team.opentech.usher.pojo.entity.Dept;
import team.opentech.usher.pojo.entity.Menu;
import team.opentech.usher.pojo.entity.User;
import team.opentech.usher.pojo.entity.type.Iframe;
import team.opentech.usher.pojo.entity.type.MenuIframe;
import team.opentech.usher.repository.ContentRepository;
import team.opentech.usher.repository.DeptRepository;
import team.opentech.usher.repository.MenuRepository;
import team.opentech.usher.repository.PowerRepository;
import team.opentech.usher.repository.RoleRepository;
import team.opentech.usher.service.MenuService;

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
        User user = userAssembler.toEntity(UserInfoHelper.doGet());
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
        User user = userAssembler.toEntity(UserInfoHelper.doGet());
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
