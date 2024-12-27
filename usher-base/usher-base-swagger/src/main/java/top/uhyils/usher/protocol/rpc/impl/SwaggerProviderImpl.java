package top.uhyils.usher.protocol.rpc.impl;

import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import top.uhyils.usher.annotation.MySwagger;
import top.uhyils.usher.annotation.Public;
import top.uhyils.usher.assembler.SwaggerAssembler;
import top.uhyils.usher.enums.ProtocolTypeEnum;
import top.uhyils.usher.pojo.DTO.ClassSwaggerDTO;
import top.uhyils.usher.pojo.DTO.RpcClassSwaggerDTO;
import top.uhyils.usher.pojo.cqe.FindSwaggerQuery;
import top.uhyils.usher.pojo.entity.SwaggerEntity;
import top.uhyils.usher.protocol.rpc.SwaggerProvider;
import top.uhyils.usher.rpc.annotation.RpcService;

/**
 * swagger信息
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年03月14日 14时32分
 */
@RpcService
@MySwagger(value = ProtocolTypeEnum.RPC, desc = "swagger详情获取")
public class SwaggerProviderImpl implements SwaggerProvider {

    @Resource
    private SwaggerAssembler assembler;

    @Override
    @Public
    public List<ClassSwaggerDTO> findSwagger(FindSwaggerQuery query) {
        SwaggerEntity entity = new SwaggerEntity();
        return entity.transClassSwagger().stream().map(t -> assembler.toDTO(t)).collect(Collectors.toList());
    }

    @Override
    @Public
    public List<RpcClassSwaggerDTO> findRpcSwagger(FindSwaggerQuery query) {
        SwaggerEntity entity = new SwaggerEntity();
        return entity.transClassSwagger().stream().map(t -> assembler.toDTO(t)).map(t -> (RpcClassSwaggerDTO) t).collect(Collectors.toList());
    }
}
