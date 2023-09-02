package team.opentech.usher.pojo.entity;

import java.util.Arrays;
import team.opentech.usher.mq.UsherMq;
import team.opentech.usher.pojo.DTO.ClassSwaggerDTO;
import team.opentech.usher.pojo.DTO.MqClassSwaggerDTO;

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

        UsherMq myMq = targetClass.getAnnotation(UsherMq.class);
        swaggerDTO.setServiceType(annotation.value());
        swaggerDTO.setName(targetClass.getName());
        swaggerDTO.setDesc(annotation.desc());
        swaggerDTO.setTopic(myMq.topic());
        swaggerDTO.setTag(Arrays.asList(myMq.tags()));
        swaggerDTO.setConsumerInfo(myMq.group());
        return swaggerDTO;
    }
}
