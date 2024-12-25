package top.uhyils.usher.dao;

import org.apache.ibatis.annotations.Mapper;
import top.uhyils.usher.dao.base.DefaultDao;
import top.uhyils.usher.pojo.DO.ProviderInterfaceMysqlDO;


/**
 * mysql接口子表(ProviderInterfaceMysql)表 mapper
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2022年09月09日 15时45分
 */
@Mapper
public interface ProviderInterfaceMysqlDao extends DefaultDao<ProviderInterfaceMysqlDO> {

}
