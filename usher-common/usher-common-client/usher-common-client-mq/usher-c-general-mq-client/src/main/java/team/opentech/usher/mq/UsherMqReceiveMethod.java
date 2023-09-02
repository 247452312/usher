package team.opentech.usher.mq;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年09月02日 20时25分
 */
@Documented
@Target(value = {ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UsherMqReceiveMethod {

}
