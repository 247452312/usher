package top.uhyils.usher.repository;

import java.util.List;
import top.uhyils.usher.pojo.DO.CompanyPowerDO;
import top.uhyils.usher.pojo.entity.CompanyPower;
import top.uhyils.usher.repository.base.BaseEntityRepository;

/**
 * 厂商接口调用权限表(CompanyPower)表 数据仓库层
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2022年09月09日 15时45分
 */
public interface CompanyPowerRepository extends BaseEntityRepository<CompanyPowerDO, CompanyPower> {

    /**
     * 根据公司id查询权限
     */
    List<CompanyPower> findByCompanyId(Long id);

}
