package team.opentech.usher.pojo.DTO.request;

import team.opentech.usher.pojo.cqe.DefaultCQE;

/**
 * 删除请求
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年09月18日 21时29分
 */
public class DelMethodDisableCommand extends DefaultCQE {

    /**
     * 接口名称
     */
    private String className;

    /**
     * 方法名称
     */
    private String methodName;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
}
