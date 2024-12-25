package top.uhyils.usher.repository;

import java.util.List;
import top.uhyils.usher.enums.InvokeTypeEnum;
import top.uhyils.usher.pojo.DO.ProviderInterfaceDO;
import top.uhyils.usher.pojo.entity.AbstractDataNode;
import top.uhyils.usher.pojo.entity.ProviderExample;
import top.uhyils.usher.pojo.entity.ProviderInterface;
import top.uhyils.usher.pojo.entity.ProviderInterfaceParam;
import top.uhyils.usher.repository.base.BaseEntityRepository;

/**
 * 接口表,提供方提供的调用方式以及url(ProviderInterface)表 数据仓库层
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2022年09月09日 15时45分
 */
public interface ProviderInterfaceRepository extends BaseEntityRepository<ProviderInterfaceDO, ProviderInterface> {

    /**
     * 获取唯一接口信息
     *
     * @param invokeType
     * @param database
     * @param table
     *
     * @return
     */
    ProviderInterface find(Integer invokeType, String database, String table);

    /**
     * 根据path获取具体的根节点
     *
     * @param database
     * @param table
     *
     * @return
     */
    AbstractDataNode find(String database, String table);

    /**
     * 获取参数
     *
     * @param id
     *
     * @return
     */
    List<ProviderInterfaceParam> findParamByInterfaceId(Long id);

    /**
     * 获取实际执行者
     *
     * @param id
     * @param type
     *
     * @return
     */
    ProviderExample findExample(Long id, InvokeTypeEnum type);
}
