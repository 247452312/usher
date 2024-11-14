package team.opentech.usher.repository;

import team.opentech.usher.pojo.DO.ProviderInterfaceRpcDO;
import team.opentech.usher.pojo.entity.ProviderExample;
import team.opentech.usher.pojo.entity.ProviderInterfaceRpc;
import team.opentech.usher.pojo.entity.type.Identifier;
import team.opentech.usher.repository.base.BaseEntityRepository;

/**
 * http接口子表(ProviderInterfaceRpc)表 数据仓库层
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2022年09月09日 15时45分
 */
public interface ProviderInterfaceRpcRepository extends BaseEntityRepository<ProviderInterfaceRpcDO, ProviderInterfaceRpc> {

    /**
     * 根据providerId获取
     *
     * @param id
     *
     * @return
     */
    ProviderExample findByProviderId(Identifier id);
}
