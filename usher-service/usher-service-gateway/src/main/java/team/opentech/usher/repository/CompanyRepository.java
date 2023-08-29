package team.opentech.usher.repository;

import java.util.List;
import team.opentech.usher.pojo.DO.CompanyDO;
import team.opentech.usher.pojo.entity.Company;
import team.opentech.usher.repository.base.BaseEntityRepository;

/**
 * 厂商表(Company)表 数据仓库层
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2022年09月09日 15时45分
 */
public interface CompanyRepository extends BaseEntityRepository<CompanyDO, Company> {

    /**
     * 根据ak查询用户
     *
     * @param ak
     *
     * @return
     */
    Company findByAk(String ak);

    /**
     * @param username
     *
     * @return
     */
    List<Company> queryUser(String username);
}
