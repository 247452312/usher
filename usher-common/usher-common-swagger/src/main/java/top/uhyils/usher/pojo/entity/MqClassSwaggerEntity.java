package top.uhyils.usher.pojo.entity;

import java.util.Arrays;
import top.uhyils.usher.annotation.UsherMq;
import top.uhyils.usher.pojo.DTO.ClassSwaggerDTO;
import top.uhyils.usher.pojo.DTO.MqClassSwaggerDTO;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年03月22日 10时58分
 */
public class MqClassSwaggerEntity extends ClassSwaggerEntity {


    public MqClassSwaggerEntity(Class<?> targetClass) {
        super(targetClass);
    }

    @Override
    protected ClassSwaggerDTO parseClass() {
        MqClassSwaggerDTO swaggerDTO = new MqClassSwaggerDTO();

        UsherMq usherMq = targetClass.getAnnotation(UsherMq.class);
        swaggerDTO.setServiceType(annotation.value());
        swaggerDTO.setName(targetClass.getName());
        swaggerDTO.setDesc(annotation.desc());
        swaggerDTO.setTopic(usherMq.topic());
        swaggerDTO.setTag(Arrays.asList(usherMq.tags()));
        swaggerDTO.setConsumerInfo(usherMq.group());
        return swaggerDTO;
    }
}
