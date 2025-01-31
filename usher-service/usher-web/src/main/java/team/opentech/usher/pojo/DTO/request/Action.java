package team.opentech.usher.pojo.DTO.request;


import java.util.Map;

/**
 * 页面请求标准
 * http://(web服务地址:端口)/action
 * 请求附带参数:
 * token            如果没有,则不传
 * interfaceName    请求指向的接口名
 * methodName       请求指向的方法名
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年04月25日 10时21分
 */
public class Action {

    /**
     * 请求指向的接口名
     */
    private String interfaceName;

    /**
     * 请求指向的方法名
     */
    private String methodName;

    /**
     * 用户信息
     * 如果没有,则不传
     */
    private String token;

    /**
     * 保证请求幂等性, 不会在前一个相同幂等id执行结束前执行方法
     */
    private Long unique;

    /**
     * 携带参数
     */
    private Map<String, Object> args;


    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Map<String, Object> getArgs() {
        return args;
    }

    public void setArgs(Map<String, Object> args) {
        this.args = args;
    }

    public Long getUnique() {
        return unique;
    }

    public void setUnique(Long unique) {
        this.unique = unique;
    }
}
