package team.opentech.usher.pojo.entity;

import team.opentech.usher.pojo.DTO.RpcClassSwaggerDTO;
import team.opentech.usher.util.SwaggerUtils;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年03月22日 10时58分
 */
public class RpcClassSwaggerEntity extends ClassSwaggerEntity {


    public RpcClassSwaggerEntity(Class<?> targetClass) {
        super(targetClass);
    }

    @Override
    protected RpcClassSwaggerDTO parseClass() {
        RpcClassSwaggerDTO rpcClassSwaggerDTO = new RpcClassSwaggerDTO();
        rpcClassSwaggerDTO.setServiceType(annotation.value());
        rpcClassSwaggerDTO.setName(targetClass.getName());
        rpcClassSwaggerDTO.setDesc(annotation.desc());
        rpcClassSwaggerDTO.setMethods(SwaggerUtils.parseToRpcMethods(targetClass));
        return rpcClassSwaggerDTO;
    }
}
