package team.opentech.usher.mq;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import javax.annotation.Resource;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import team.opentech.usher.mq.client.MQClient;
import team.opentech.usher.mq.consumer.MQConsumer;
import team.opentech.usher.util.ClassUtil;
import team.opentech.usher.util.LogUtil;
import team.opentech.usher.util.SpringUtil;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年09月02日 20时19分
 */
public class MQAnnotationRunner implements ApplicationRunner {

    @Resource
    private MQClient mqClient;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 使用了
        Map<String, Object> byAnnotation = SpringUtil.getByAnnotation(UsherMq.class);
        for (Object value : byAnnotation.values()) {
            Class<?> realClass = ClassUtil.getRealClass(value);
            UsherMq annotation = realClass.getAnnotation(UsherMq.class);
            String[] tags = annotation.tags();
            String topic = annotation.topic();
            String group = annotation.group();
            mqClient.addConsumer(topic, () -> new MQConsumer() {

                private volatile Method targetMethod;

                private volatile boolean init = false;

                @Override
                public void receive(MQMessage message) {
                    if (targetMethod == null && !init) {
                        for (Method declaredMethod : realClass.getDeclaredMethods()) {
                            Optional<Annotation> first = Arrays.stream(declaredMethod.getAnnotations()).filter(t -> Objects.equals(UsherMqReceiveMethod.class, t.getClass())).findFirst();
                            if (first.isPresent()) {
                                targetMethod = declaredMethod;
                                targetMethod.setAccessible(true);
                                break;
                            }
                        }
                        init = true;
                    }

                    if (targetMethod != null) {
                        try {
                            targetMethod.invoke(value, message);
                        } catch (Exception e) {
                            LogUtil.error(this, e);
                        }
                    }
                }

                @Override
                public String topic() {
                    return topic;
                }

                @Override
                public String[] tag() {
                    return tags;
                }

                @Override
                public String groupName() {
                    return group;
                }
            });

        }
    }
}
