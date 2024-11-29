package team.opentech.usher.repository.impl;

import team.opentech.usher.annotation.Repository;
import team.opentech.usher.assembler.CompanyPowerAssembler;
import team.opentech.usher.dao.CompanyPowerDao;
import team.opentech.usher.pojo.DO.CompanyPowerDO;
import team.opentech.usher.pojo.DTO.CompanyPowerDTO;
import team.opentech.usher.pojo.entity.CompanyPower;
import team.opentech.usher.repository.CompanyPowerRepository;
import team.opentech.usher.repository.base.AbstractRepository;


/**
 * 厂商接口调用权限表(CompanyPower)表 仓库实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2022年09月09日 15时45分
 */
@Repository
public class CompanyPowerRepositoryImpl extends AbstractRepository<CompanyPower, CompanyPowerDO, CompanyPowerDao, CompanyPowerDTO, CompanyPowerAssembler> implements CompanyPowerRepository {

    protected CompanyPowerRepositoryImpl(CompanyPowerAssembler convert, CompanyPowerDao dao) {
        super(convert, dao);
    }


}
