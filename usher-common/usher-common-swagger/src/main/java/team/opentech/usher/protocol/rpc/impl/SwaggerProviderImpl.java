package team.opentech.usher.protocol.rpc.impl;

import team.opentech.usher.annotation.MySwagger;
import team.opentech.usher.annotation.Public;
import team.opentech.usher.assembler.SwaggerAssembler;
import team.opentech.usher.enums.ProtocolTypeEnum;
import team.opentech.usher.pojo.DTO.ClassSwaggerDTO;
import team.opentech.usher.pojo.DTO.RpcClassSwaggerDTO;
import team.opentech.usher.pojo.cqe.FindSwaggerQuery;
import team.opentech.usher.pojo.entity.SwaggerEntity;
import team.opentech.usher.protocol.rpc.SwaggerProvider;
import team.opentech.usher.rpc.annotation.RpcService;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;

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
