package top.uhyils.usher.pojo.entity;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson2.JSON;
import java.util.ArrayList;
import java.util.List;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import top.uhyils.usher.annotation.AccessApi;
import top.uhyils.usher.context.LoginInfoHelper;
import top.uhyils.usher.pojo.DTO.LoginDTO;
import top.uhyils.usher.pojo.DTO.UserDTO;
import top.uhyils.usher.pojo.cqe.DefaultCQE;
import top.uhyils.usher.pojo.cqe.command.StringCommand;
import top.uhyils.usher.redis.Redisable;
import top.uhyils.usher.rpc.spring.util.RpcApiUtil;
import top.uhyils.usher.util.AopUtil;
import top.uhyils.usher.util.Asserts;
import top.uhyils.usher.util.CollectionUtil;

/**
 * 令牌请求
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2022年06月21日 08时19分
 */
public class AccessTokenInterfaceInvoker extends AbstractAnnotationInterfaceInvoker {


    public AccessTokenInterfaceInvoker(ProceedingJoinPoint pjp) {
        super(pjp);
    }

    public static AccessTokenInterfaceInvoker create(ProceedingJoinPoint pjp) {
        return new AccessTokenInterfaceInvoker(pjp);
    }


    /**
     * 检查是否是口令api接口
     *
     * @param pjp
     *
     * @return
     */
    public static boolean checkAnnotation(ProceedingJoinPoint pjp) {
        Class<?> targetClass = pjp.getTarget().getClass();
        Signature signature = pjp.getSignature();
        AccessApi[] methodNoLoginAnnotation = ((MethodSignature) signature).getMethod().getAnnotationsByType(AccessApi.class);
        AccessApi[] classNoLoginAnnotation = targetClass.getAnnotationsByType(AccessApi.class);
        return CollectionUtil.isNotEmpty(methodNoLoginAnnotation) || CollectionUtil.isNotEmpty(classNoLoginAnnotation);
    }


    @Override
    public Object invoke() throws Throwable {
        //获取token
        DefaultCQE arg = AopUtil.getDefaultCQEInPjp(pjp);
        String accessToken = arg.getAccessToken();

        // 口令登录
        if (arg.getUser() != null) {
            LoginInfoHelper.setUser(arg.getUser());
            return pjp.proceed();
        }
        UserDTO loginDTO = accessTokenLogin(accessToken);
        Asserts.assertTrue(loginDTO != null, "未知的口令");
        LoginInfoHelper.setUser(loginDTO);
        LoginInfoHelper.setToken(accessToken);
        return pjp.proceed();
    }


    /**
     * 口令登录
     *
     * @return 口令登录
     */
    private UserDTO accessTokenLogin(String accessToken) throws Throwable {
        try (Redisable jedis = redisPool.getJedis()) {
            if (jedis.exists(accessToken)) {
                return JSONObject.parseObject(jedis.get(accessToken), UserDTO.class);
            }
        }
        StringCommand build = new StringCommand();
        build.setValue(accessToken);
        List<Object> args = new ArrayList<>();
        args.add(build);
        Object o1 = RpcApiUtil.rpcApiTool("UserAccessTokenProvider", "accessToken", args);
        JSONObject o = JSONObject.parseObject(JSON.toJSONString(o1));
        return o.toJavaObject(LoginDTO.class).getUserEntity();
    }

}
