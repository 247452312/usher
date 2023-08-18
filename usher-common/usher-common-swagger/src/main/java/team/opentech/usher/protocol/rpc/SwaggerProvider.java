package team.opentech.usher.protocol.rpc;

import team.opentech.usher.annotation.Public;
import team.opentech.usher.pojo.DTO.ClassSwaggerDTO;
import team.opentech.usher.pojo.DTO.RpcClassSwaggerDTO;
import team.opentech.usher.pojo.cqe.FindSwaggerQuery;
import team.opentech.usher.protocol.rpc.base.BaseProvider;
import java.util.List;

/**
 * swagger解析
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年03月14日 14时19分
 */
public interface SwaggerProvider extends BaseProvider {

    /**
     * 获取当前项目的swagger信息
     *
     * @param query
     *
     * @return
     */
    List<ClassSwaggerDTO> findSwagger(FindSwaggerQuery query);

    /**
     * 测试用,请忽略
     *
     * @param query
     *
     * @return
     */
    @Public
    List<RpcClassSwaggerDTO> findRpcSwagger(FindSwaggerQuery query);
}
