package team.opentech.usher.rpc.netty.spi.filter.invoker;

import com.alibaba.fastjson.JSON;
import team.opentech.usher.rpc.enums.RpcResponseTypeEnum;
import team.opentech.usher.rpc.enums.RpcStatusEnum;
import team.opentech.usher.rpc.enums.RpcTypeEnum;
import team.opentech.usher.rpc.exchange.pojo.content.RpcRequestContent;
import team.opentech.usher.rpc.exchange.pojo.data.RpcData;
import team.opentech.usher.rpc.exchange.pojo.data.factory.RpcFactory;
import team.opentech.usher.rpc.exchange.pojo.data.factory.RpcFactoryProducer;
import team.opentech.usher.rpc.exchange.pojo.head.RpcHeader;
import team.opentech.usher.rpc.netty.core.RpcSelfNetty;
import team.opentech.usher.rpc.netty.spi.filter.FilterContext;
import team.opentech.usher.util.ClassUtil;
import team.opentech.usher.util.LogUtil;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2021年01月21日 21时07分
 */
public class LastSelfInvoker implements RpcInvoker {

    /**
     * netty
     */
    private RpcSelfNetty<?> netty;

    private Object service;

    public LastSelfInvoker(RpcSelfNetty<?> netty) {
        this.netty = netty;
        this.service = netty.service();
    }

    @Override
    public RpcData invoke(FilterContext context) throws InterruptedException {
        RpcData request = context.getRequestData();
        RpcRequestContent content = (RpcRequestContent) request.content();
        LogUtil.info("调用本项目的服务:{},{}", content.getServiceName(), content.getMethodName());
        String methodName = content.getMethodName();
        String[] methodParameterTypes = content.getMethodParameterTypes();
        Class<?> realClass = null;
        Object result = null;
        try {
            realClass = ClassUtil.getRealClass(service);
            Class[] classes = Arrays.stream(methodParameterTypes).map(t -> {
                try {
                    return Class.forName(t);
                } catch (ClassNotFoundException e) {
                    LogUtil.error(this, e);
                    return null;
                }
            }).toArray(Class[]::new);
            Method declaredMethod = realClass.getDeclaredMethod(methodName, classes);
            declaredMethod.setAccessible(false);
            result = declaredMethod.invoke(service, content.getArgs());
        } catch (Exception e) {
            LogUtil.error(this, e);
        }

        /*解析*/
        RpcFactory build = RpcFactoryProducer.build(RpcTypeEnum.RESPONSE);
        // 获取到的Request
        return build.createByInfo(request.unique(), new Object[]{RpcStatusEnum.OK.getCode()}, new RpcHeader[0], RpcResponseTypeEnum.STRING_BACK.getCode().toString(), JSON.toJSONString(result));

    }
}
