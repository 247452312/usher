package top.uhyils.usher.protocol.task;

import com.alibaba.fastjson.JSON;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import top.uhyils.usher.pojo.DTO.UserDTO;
import top.uhyils.usher.pojo.cqe.DefaultCQE;
import top.uhyils.usher.util.LogUtil;
import top.uhyils.usher.util.RpcApiUtil;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年06月26日 08时58分
 */
public class JobRunnable implements Callable {

    /**
     * 接口名称
     */
    private String interfaceName;

    /**
     * 方法名称
     */
    private String methodName;

    /**
     * 参数
     */
    private String params;

    /**
     * 参数类型
     */
    private String paramType;

    /**
     * 调用用户
     */
    private UserDTO userEntity;

    public JobRunnable(String interfaceName, String methodName, String params, String paramType, UserDTO userEntity) {
        this.interfaceName = interfaceName;
        this.methodName = methodName;
        this.params = params;
        this.paramType = paramType;
        this.userEntity = userEntity;

    }

    @Override
    public Object call() throws Exception {
        Class<DefaultCQE> aClass = (Class<DefaultCQE>) Class.forName(paramType == null ? "top.uhyils.usher.pojo.cqe.DefaultCQE" : paramType);
        DefaultCQE defaultRequest = JSON.parseObject(params, aClass);
        defaultRequest.setUser(userEntity);
        ArrayList<Object> list = new ArrayList<>(1);
        list.add(defaultRequest);
        try {
            RpcApiUtil.rpcApiToolAsync(interfaceName, methodName, list);
        } catch (Throwable throwable) {
            LogUtil.error(throwable, "定时任务执行失败");
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }
}
