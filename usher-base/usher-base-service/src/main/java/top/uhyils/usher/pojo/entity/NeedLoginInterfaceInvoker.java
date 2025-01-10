package top.uhyils.usher.pojo.entity;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import top.uhyils.usher.context.LoginInfoHelper;
import top.uhyils.usher.context.UsherContext;
import top.uhyils.usher.enums.UserTypeEnum;
import top.uhyils.usher.exception.LoginOutException;
import top.uhyils.usher.exception.NoAuthException;
import top.uhyils.usher.exception.NoLoginException;
import top.uhyils.usher.pojo.DTO.UserDTO;
import top.uhyils.usher.pojo.cqe.DefaultCQE;
import top.uhyils.usher.pojo.cqe.query.CheckUserHavePowerQuery;
import top.uhyils.usher.redis.RedisPoolHandle;
import top.uhyils.usher.rpc.spring.util.RpcApiUtil;
import top.uhyils.usher.util.AopUtil;
import top.uhyils.usher.util.Asserts;
import top.uhyils.usher.util.SpringUtil;

/**
 * 需要登录的普通请求
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2022年06月21日 08时19分
 */
public class NeedLoginInterfaceInvoker extends AbstractAnnotationInterfaceInvoker {


    /**
     * service后缀
     */
    private static final String IMPL = "Impl";

    /**
     * 超级管理员账号
     */
    private static final String ADMIN = "admin";

    public NeedLoginInterfaceInvoker(ProceedingJoinPoint pjp) {
        super(pjp);
    }

    public static NeedLoginInterfaceInvoker create(ProceedingJoinPoint pjp) {
        return new NeedLoginInterfaceInvoker(pjp);
    }


    @Override
    public Object invoke() throws Throwable {

        String className = pjp.getTarget().getClass().getCanonicalName();
        String methodName = pjp.getSignature().getName();

        //获取token
        DefaultCQE arg = AopUtil.getDefaultCQEInPjp(pjp);
        String token = arg.getToken();


        /* 查询有没有登录 */
        if (StringUtils.isEmpty(token) && arg.getUser() == null) {
            throw new NoLoginException();
        }

        UserDTO userDTO;
        // 如果参数中携带了用户,则不需要去再次查询用户
        if (arg.getUser() != null) {
            userDTO = arg.getUser();
            if (Objects.equals(userDTO.getUsername(), ADMIN)) {
                // 放行
            } else if (!Objects.equals(userDTO.getUserType(), UserTypeEnum.USER.getCode())) {
                Asserts.assertTrue(false, "用户类型不正确");
            }
        } else {
            Optional<UserDTO> user = SpringUtil.getBean(RedisPoolHandle.class).getUser(token);
            Asserts.assertTrue(user.isPresent(), "登录已过期");
            userDTO = user.get();
            Optional<UserTypeEnum> byCode = UserTypeEnum.getByCode(userDTO.getUserType());
            Asserts.assertTrue(byCode.isPresent(), "用户类型不存在");
            Asserts.assertTrue(byCode.get() == UserTypeEnum.USER, "用户类型不正确");
        }
        /* 查询是否超时 */
        if (userDTO == null) {
            throw new LoginOutException();
        }
        LoginInfoHelper.setUser(userDTO);
        try {
            // 权限检验
            checkPower(className, methodName, arg, token, userDTO);
            return pjp.proceed(new DefaultCQE[]{arg});
        } finally {
            LoginInfoHelper.clean();
        }

    }

    /**
     * 检查是否有权限
     *
     * @param className
     * @param methodName
     * @param arg
     * @param token
     * @param userDTO
     *
     * @throws Throwable
     */
    private void checkPower(String className, String methodName, DefaultCQE arg, String token, UserDTO userDTO) throws Throwable {
        /* 查询是否有权限 */
        // 超级管理员直接放行
        if (ADMIN.equals(userDTO.getUsername())) {
            userDTO.setRoleId(UsherContext.ADMIN_ROLE_ID);
            arg.setUser(userDTO);
            //执行方法
            return;
        }

        String substring = className.substring(className.lastIndexOf('.') + 1);
        if (substring.contains(IMPL)) {
            substring = substring.substring(0, substring.length() - 4);
        }
        Boolean havePower = checkUserHavePower(userDTO, userDTO.getId(), substring, methodName, token, arg);

        if (!havePower) {
            throw new NoAuthException();
        }
        arg.setUser(userDTO);
    }

    /**
     * 检查指定用户有没有指定权限
     *
     * @param id            用户id
     * @param interfaceName 权限接口名
     * @param methodName    权限方法名
     *
     * @return 是否有权限
     */
    private Boolean checkUserHavePower(UserDTO userEntity, Long id, String interfaceName, String methodName, String token, DefaultCQE request) throws Throwable {
        CheckUserHavePowerQuery build = CheckUserHavePowerQuery.build(interfaceName, methodName, id);
        build.setToken(token);
        build.setUser(userEntity);
        ArrayList<Object> args = new ArrayList<>();
        args.add(build);
        Object o = RpcApiUtil.rpcApiTool("PowerProvider", "checkUserHavePower", args);
        return (Boolean) o;
    }

}
