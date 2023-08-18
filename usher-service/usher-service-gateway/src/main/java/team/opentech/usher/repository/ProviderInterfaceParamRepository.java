package team.opentech.usher.repository;

import team.opentech.usher.pojo.DO.ProviderInterfaceParamDO;
import team.opentech.usher.pojo.entity.ProviderInterfaceParam;
import team.opentech.usher.pojo.entity.type.Identifier;
import team.opentech.usher.repository.base.BaseEntityRepository;
import java.util.List;

/**
 * 接口参数表(ProviderInterfaceParam)表 数据仓库层
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2022年09月09日 15时45分
 */
public interface ProviderInterfaceParamRepository extends BaseEntityRepository<ProviderInterfaceParamDO, ProviderInterfaceParam> {

    /**
     * 根据接口获取接口参数
     *
     * @param interfaceId
     *
     * @return
     */
    List<ProviderInterfaceParam> findByInterfaceId(Identifier interfaceId);
}
