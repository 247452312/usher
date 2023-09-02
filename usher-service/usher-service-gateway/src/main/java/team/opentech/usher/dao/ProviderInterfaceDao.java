package team.opentech.usher.dao;

import org.apache.ibatis.annotations.Mapper;
import team.opentech.usher.dao.base.DefaultDao;
import team.opentech.usher.pojo.DO.ProviderInterfaceDO;


/**
 * 接口表,提供方提供的调用方式以及url(ProviderInterface)表 mapper
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2022年09月09日 15时45分
 */
@Mapper
public interface ProviderInterfaceDao extends DefaultDao<ProviderInterfaceDO> {

}
