package team.opentech.usher.dao;

import org.apache.ibatis.annotations.Mapper;
import team.opentech.usher.dao.base.DefaultDao;
import team.opentech.usher.pojo.DO.ProviderInterfaceRpcDO;


/**
 * http接口子表(ProviderInterfaceRpc)表 mapper
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2022年09月09日 15时45分
 */
@Mapper
public interface ProviderInterfaceRpcDao extends DefaultDao<ProviderInterfaceRpcDO> {

}
