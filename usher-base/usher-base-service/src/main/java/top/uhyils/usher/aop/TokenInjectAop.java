package top.uhyils.usher.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import top.uhyils.usher.pojo.entity.AccessTokenInterfaceInvoker;
import top.uhyils.usher.pojo.entity.NeedLoginInterfaceInvoker;
import top.uhyils.usher.pojo.entity.NoTokenInterfaceInvoker;
import top.uhyils.usher.pojo.entity.PublicInterfaceInvoker;
import top.uhyils.usher.redis.RedisPoolHandle;

/**
 * token转用户信息切面(包含验证)
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年04月27日 16时32分
 */
@Component
@Aspect
@Order(20)
public class TokenInjectAop {


    @Value("${token.enable:true}")
    private Boolean enable;

    @Autowired
    private RedisPoolHandle redisPoolHandle;

    /**
     * 定义切入点，切入点为service包中的所有类的所有函数
     * 通过@Pointcut注解声明频繁使用的切点表达式
     */
    @Pointcut("execution(public * top.uhyils.usher.protocol.rpc..*.*(..)))")
    public void tokenInjectPoint() {
    }


    /**
     * 1. 如果方法有NoToken注解,直接放行,不需要user注入
     * 2. 查询用户是否存在, 如果存在 就不需要检查token
     * 3. 如果没有user 根据token获取用户 检查用户是否存在
     * 4. 超级管理员直接放行
     * 5. 查询用户是否有权限
     * 6. 查询正常用户是否登录
     *
     * @param pjp pjp
     *
     * @return pjp 的返回值
     *
     * @throws Throwable pjp执行出错
     */
    @Around("tokenInjectPoint()")
    public Object tokenInjectAroundAspect(ProceedingJoinPoint pjp) throws Throwable {

        // 是否开启用户登录验证
        if (!enable) {
            return pjp.proceed();
        }
        //令牌方法如果有令牌直接放行 不需要校验
        if (AccessTokenInterfaceInvoker.checkAnnotation(pjp)) {
            return AccessTokenInterfaceInvoker.create(pjp).invoke();
        }
        // public接口直接放行,不进行解析token的动作
        if (PublicInterfaceInvoker.checkAnnotation(pjp)) {
            return PublicInterfaceInvoker.create(pjp).invoke();
        }
        //NoLogin注释的方法直接放行 不需要登录
        if (NoTokenInterfaceInvoker.checkAnnotation(pjp)) {
            return NoTokenInterfaceInvoker.create(pjp).invoke();
        }

        // 正常登录
        return NeedLoginInterfaceInvoker.create(pjp).invoke();

    }


}
