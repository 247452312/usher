package top.uhyils.usher.repository.impl;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import java.util.List;
import top.uhyils.usher.annotation.Repository;
import top.uhyils.usher.assembler.CompanyPowerAssembler;
import top.uhyils.usher.dao.CompanyPowerDao;
import top.uhyils.usher.pojo.DO.CompanyPowerDO;
import top.uhyils.usher.pojo.DTO.CompanyPowerDTO;
import top.uhyils.usher.pojo.entity.CompanyPower;
import top.uhyils.usher.repository.CompanyPowerRepository;
import top.uhyils.usher.repository.base.AbstractRepository;


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


    @Override
    public List<CompanyPower> findByCompanyId(Long id) {
        LambdaQueryChainWrapper<CompanyPowerDO> wrapper = lambdaQuery();
        wrapper.eq(CompanyPowerDO::getCompanyId, id);
        List<CompanyPowerDO> list = wrapper.list();
        return assembler.listToEntity(list);
    }

}
