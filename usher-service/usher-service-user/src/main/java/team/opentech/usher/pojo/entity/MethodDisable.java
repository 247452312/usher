package team.opentech.usher.pojo.entity;

import team.opentech.usher.context.UsherContext;
import team.opentech.usher.enums.ReadWriteTypeEnum;
import team.opentech.usher.pojo.DTO.MethodDisableDTO;
import team.opentech.usher.pojo.DTO.request.DelMethodDisableCommand;
import team.opentech.usher.pojo.entity.base.AbstractEntity;
import team.opentech.usher.repository.ServiceControlRepository;
import team.opentech.usher.util.Asserts;
import org.apache.commons.lang3.StringUtils;

/**
 * 类型
 * 如果是接口: 0->无 1->读接口禁用 2->写接口禁用 3->全部禁用
 * 如果是方法 0->无 1->禁用
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月28日 18时13分
 */
public class MethodDisable extends AbstractEntity {


    /**
     * 接口名称包分隔符
     */
    public static final String INTERFACE_NAME_PACKAGE_SEPARATOR = ".";


    /**
     * 接口连接方法的分隔符
     */
    public static final String METHOD_LINK_CLASS_SYMBOL = "#";

    /**
     * 接口名称
     */
    private String className;


    /**
     * 方法名称
     */
    private String methodName;

    /**
     * 方法类型
     */
    private ReadWriteTypeEnum methodType;

    public MethodDisable(String className, String methodName) {
        this(className, methodName, null);
    }

    public MethodDisable(String className, String methodName, ReadWriteTypeEnum methodType) {
        this.className = className;
        this.methodName = methodName;
        this.methodType = methodType;
    }

    public MethodDisable(DelMethodDisableCommand query) {
        this(query.getClassName(), query.getMethodName(), null);
    }

    public MethodDisable(MethodDisableDTO dto) {
        this(dto.getClassName(), dto.getMethodName(), ReadWriteTypeEnum.parse(dto.getDisableType()).orElseThrow(() -> Asserts.makeException("未找到方法禁用类型:{}", dto.getDisableType())));
    }

    public Boolean checkInterfaceDisable(ServiceControlRepository repository) {
        if (StringUtils.isNotBlank(methodName)) {
            Boolean methodDisable = repository.checkMethodDisable(toInterfaceMethodName());
            // null说明没有配置这一个方法,去找接口级
            if (methodDisable != null) {
                return methodDisable;
            }
        }
        return repository.checkClassDisable(className, methodType);
    }

    public String toInterfaceMethodName() {
        completionClassName();
        return className + METHOD_LINK_CLASS_SYMBOL + methodName;
    }

    public void completionClassName() {
        if (!className.contains(INTERFACE_NAME_PACKAGE_SEPARATOR)) {
            className = UsherContext.SERVICE_PACKAGE_PREFIX + className;
        }
    }

    public void saveMethodDisable(ServiceControlRepository repository) {
        repository.saveMethodDisable(this);
    }

    public MethodDisableDTO toDTO() {
        MethodDisableDTO methodDisableDTO = new MethodDisableDTO();
        methodDisableDTO.setClassName(className);
        methodDisableDTO.setMethodName(methodName);
        if (methodType != null) {
            methodDisableDTO.setDisableType(methodType.getCode());
        }
        return methodDisableDTO;

    }

    public void del(ServiceControlRepository repository) {
        repository.delMethodDisable(this);
    }
}
