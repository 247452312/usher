package team.opentech.usher.pojo.entity;

import team.opentech.usher.annotation.Public;
import team.opentech.usher.pojo.DTO.base.ServiceResult;
import team.opentech.usher.util.CollectionUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2022年06月20日 11时04分
 */
public class PublicInterfaceInvoker extends AbstractAnnotationInterfaceInvoker {


    public PublicInterfaceInvoker(ProceedingJoinPoint pjp) {
        super(pjp);
    }


    /**
     * 检查是否是公开接口
     *
     * @param pjp
     *
     * @return
     */
    public static boolean checkAnnotation(ProceedingJoinPoint pjp) {
        Class<?> targetClass = pjp.getTarget().getClass();
        Signature signature = pjp.getSignature();
        Public[] methodNoLoginAnnotation = ((MethodSignature) signature).getMethod().getAnnotationsByType(Public.class);
        Public[] classNoLoginAnnotation = targetClass.getAnnotationsByType(Public.class);
        return CollectionUtil.isNotEmpty(methodNoLoginAnnotation) || CollectionUtil.isNotEmpty(classNoLoginAnnotation);
    }

    public static PublicInterfaceInvoker create(ProceedingJoinPoint pjp) {
        return new PublicInterfaceInvoker(pjp);
    }


    @Override
    public Object invoke() throws Throwable {
        ServiceResult<Long> result = checkIp();
        if (result != null) {
            return result;
        }
        return pjp.proceed();
    }


}
