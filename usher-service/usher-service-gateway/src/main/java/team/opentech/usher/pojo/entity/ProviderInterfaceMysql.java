package team.opentech.usher.pojo.entity;

import team.opentech.usher.annotation.Default;
import team.opentech.usher.pojo.DO.ProviderInterfaceMysqlDO;

/**
 * mysql接口子表(ProviderInterfaceMysql)表 数据库实体类
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2022年09月09日 15时45分
 */
public class ProviderInterfaceMysql extends AbstractProviderExample<ProviderInterfaceMysqlDO> implements ProviderExample {

    @Default
    public ProviderInterfaceMysql(ProviderInterfaceMysqlDO data) {
        super(data);
    }

    public ProviderInterfaceMysql(Long id) {
        super(id, new ProviderInterfaceMysqlDO());
    }

}
