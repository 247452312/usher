package team.opentech.usher.pojo.entity;

import team.opentech.usher.BaseTest;
import team.opentech.usher.context.UsherContext;
import team.opentech.usher.enums.ReadWriteTypeEnum;
import team.opentech.usher.pojo.DTO.MethodDisableDTO;
import team.opentech.usher.pojo.DTO.request.DelMethodDisableCommand;
import team.opentech.usher.repository.ServiceControlRepository;
import team.opentech.usher.util.Asserts;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年09月09日 15时14分
 */
public class MethodDisableTest extends BaseTest {

    @Autowired
    private ServiceControlRepository serviceControlRepository;

    @Test
    public void completionClassName() {
        String classSingleName = "UserProvider";
        String method = "method";
        MethodDisableDTO dto = new MethodDisableDTO();
        dto.setClassName(classSingleName);
        dto.setMethodName(method);
        MethodDisable methodDisable = new MethodDisable(dto);
        methodDisable.completionClassName();
        String s = methodDisable.toInterfaceMethodName();
        Asserts.assertTrue(s.equals(UsherContext.SERVICE_PACKAGE_PREFIX + classSingleName + "#" + method));
    }

    @Test
    public void checkInterfaceDisable() {
        MethodDisable methodDisable = new MethodDisable("UserProvider", "method", ReadWriteTypeEnum.READ);
        methodDisable.saveMethodDisable(serviceControlRepository);
        Boolean aBoolean = methodDisable.checkInterfaceDisable(serviceControlRepository);
        Asserts.assertTrue(!aBoolean);

        DelMethodDisableCommand query = new DelMethodDisableCommand();
        query.setClassName("UserProvider");
        query.setMethodName("method");
        MethodDisable methodDisable2 = new MethodDisable(query);
        Boolean aBoolean1 = methodDisable2.checkInterfaceDisable(serviceControlRepository);
        Asserts.assertTrue(!aBoolean1);
        methodDisable2.del(serviceControlRepository);
        Boolean aBoolean2 = methodDisable2.checkInterfaceDisable(serviceControlRepository);
        Asserts.assertTrue(aBoolean2);

    }

    @Test
    public void toDTO() {
        String classNameStr = "UserProvider";
        String methodStr = "method";
        MethodDisable methodDisable = new MethodDisable(classNameStr, methodStr);
        MethodDisableDTO methodDisableDTO = methodDisable.toDTO();
        String methodName = methodDisableDTO.getMethodName();
        String className = methodDisableDTO.getClassName();
        Integer disableType = methodDisableDTO.getDisableType();
        Asserts.assertTrue(disableType == null);
        Asserts.assertTrue(classNameStr.equals(className));
        Asserts.assertTrue(methodStr.equals(methodName));
    }


}
