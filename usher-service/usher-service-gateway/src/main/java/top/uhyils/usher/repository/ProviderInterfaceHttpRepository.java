package top.uhyils.usher.repository;

import top.uhyils.usher.pojo.DO.ProviderInterfaceHttpDO;
import top.uhyils.usher.pojo.entity.ProviderExample;
import top.uhyils.usher.pojo.entity.ProviderInterfaceHttp;
import top.uhyils.usher.repository.base.BaseEntityRepository;

/**
 * http接口子表(ProviderInterfaceHttp)表 数据仓库层
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2022年09月09日 15时45分
 */
public interface ProviderInterfaceHttpRepository extends BaseEntityRepository<ProviderInterfaceHttpDO, ProviderInterfaceHttp> {

    /**
     * 根据providerId获取
     *
     * @param id
     *
     * @return
     */
    ProviderExample findByProviderId(Long id);
}
