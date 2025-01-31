package team.opentech.usher.pojo.cqe.command;

import team.opentech.usher.pojo.cqe.command.base.AbstractCommand;


/**
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年09月28日 16时43分
 */
public class RelegationCoverCommand extends AbstractCommand {

    /**
     * 接口名称
     */
    private String serviceName;

    /**
     * 方法名称
     */
    private String methodName;

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
}
