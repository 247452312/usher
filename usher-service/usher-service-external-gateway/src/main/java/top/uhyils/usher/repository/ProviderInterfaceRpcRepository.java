package top.uhyils.usher.repository;

import top.uhyils.usher.pojo.DO.ProviderInterfaceRpcDO;
import top.uhyils.usher.pojo.entity.ProviderExample;
import top.uhyils.usher.pojo.entity.ProviderInterfaceRpc;
import top.uhyils.usher.repository.base.BaseEntityRepository;

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
    ProviderExample findByProviderId(Long id);
}
