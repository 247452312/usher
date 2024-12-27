package top.uhyils.usher.protocol.rpc;

import java.util.List;
import top.uhyils.usher.annotation.Public;
import top.uhyils.usher.pojo.DTO.ClassSwaggerDTO;
import top.uhyils.usher.pojo.DTO.RpcClassSwaggerDTO;
import top.uhyils.usher.pojo.cqe.FindSwaggerQuery;
import top.uhyils.usher.protocol.rpc.base.BaseProvider;

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
