package team.opentech.usher.pojo.entity;

import com.alibaba.fastjson.JSON;
import team.opentech.usher.BaseTest;
import team.opentech.usher.assembler.MenuAssembler;
import team.opentech.usher.pojo.DO.DeptDO;
import team.opentech.usher.pojo.DO.MenuDO;
import team.opentech.usher.pojo.DTO.MenuDTO;
import team.opentech.usher.pojo.DTO.UserDTO;
import team.opentech.usher.pojo.DTO.response.info.MenuTreeDTO;
import team.opentech.usher.pojo.entity.type.Identifier;
import team.opentech.usher.repository.DeptRepository;
import team.opentech.usher.repository.MenuRepository;
import team.opentech.usher.util.Asserts;
import team.opentech.usher.util.CollectionUtil;
import team.opentech.usher.util.ReflactUtil;
import java.lang.invoke.SerializedLambda;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年09月09日 08时06分
 */
public class MenuTest extends BaseTest {

    @Autowired
    private MenuRepository repository;

    private Long m1Id;

    private Long m2Id;

    @Autowired
    private MenuAssembler assembler;

    private Long deptId;

    @Autowired
    private DeptRepository deptRepository;


    @BeforeEach
    public void setUp() throws Exception {
        super.setUp();
        MenuDO do1 = new MenuDO();
        do1.setFid(0L);
        do1.setIFrame(123);
        do1.setIcon("asd");
        do1.setName("asd");
        do1.setSort(1);
        do1.setTarget("asd");
        do1.setType(Boolean.TRUE);
        do1.setUrl("asd");

        Menu m1 = new Menu(do1);
        repository.save(m1);
        m1Id = m1.getUnique().get().getId();

        MenuDO do2 = new MenuDO();
        do2.setFid(m1.getUnique().get().getId());
        do2.setIFrame(123);
        do2.setIcon("asd");
        do2.setName("asd");
        do2.setSort(1);
        do2.setTarget("asd");
        do2.setType(Boolean.TRUE);
        do2.setUrl("asd");

        Menu m2 = new Menu(do2);
        repository.save(m2);
        m2Id = m2.getUnique().get().getId();

        // dept
        DeptDO deptDO = new DeptDO();
        deptDO.setName("test");
        Dept dept = new Dept(deptDO);
        deptRepository.save(dept);
        deptId = dept.getUnique().get().getId();


    }

    @AfterEach
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @Test
    public void removeSelf() {
        Menu menu = new Menu(m1Id);
        menu.completion(repository);
        menu.removeSelf(repository, assembler);

        Menu menu1 = new Menu(m1Id);
        Asserts.assertException(() -> menu1.completion(repository));

        Menu menu2 = new Menu(m2Id);
        Asserts.assertException(() -> menu2.completion(repository));
    }

    @Test
    public void findSelfNode() {
        Menu menu = new Menu(m1Id);
        menu.completion(repository);
        MenuTreeDTO selfNode = menu.findSelfNode(repository, assembler);
        List<MenuTreeDTO> child = selfNode.getChild();
        MenuDTO menuDTO = selfNode.getMenuDTO();
        Asserts.assertTrue(m1Id.equals(menuDTO.getId()));
        Asserts.assertTrue(CollectionUtil.isNotEmpty(child));
        Asserts.assertTrue(child.size() == 1);
    }

    @Test
    public void findSelfNode2() {
        Menu menu = new Menu(m1Id);
        menu.completion(repository);
        menu.toData().get().setIFrame(123123123);
        MenuTreeDTO selfNode = menu.findSelfNode(repository, assembler);

        Asserts.assertTrue(selfNode == null);
    }

    @Test
    public void cleanDept() {
        Dept dept = new Dept(deptId);
        dept.completion(deptRepository);
        Menu menu = new Menu(m1Id);
        dept.addMenu(Arrays.asList(menu), deptRepository);
        dept.addMenu(Arrays.asList(menu), deptRepository);

        menu.cleanDept(repository);
        Asserts.assertTrue(menu.depts() == null);

        Dept dept1 = new Dept(deptId);
        dept1.initMenus(repository);
        List<Menu> menus = dept1.menus();

        Asserts.assertTrue(menus.size() == 0);
    }

    @Test
    public void cleanDept2() {
        Dept dept = new Dept(deptId);
        dept.completion(deptRepository);
        Menu menu = new Menu(new Identifier(m1Id));
        menu.addDepts(Arrays.asList(dept), repository);
        List<Dept> depts = menu.depts();
        Asserts.assertTrue(depts.size() == 1);

        menu.cleanDept(repository);
        Asserts.assertTrue(menu.depts() == null);

        Dept dept1 = new Dept(deptId);
        dept1.initMenus(repository);
        List<Menu> menus = dept1.menus();

        Asserts.assertTrue(menus.size() == 0);
    }

    @Test
    public void sFunctionTest() throws Exception {
        SerializedLambda serializedLambda = ReflactUtil.doSFunction(UserDTO::getId);
        System.out.println("方法名：" + serializedLambda.getImplMethodName());
        System.out.println("类名：" + serializedLambda.getImplClass());
        System.out.println("serializedLambda：" + JSON.toJSONString(serializedLambda));
    }


}
