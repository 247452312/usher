package team.opentech.usher.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import java.util.List;
import team.opentech.usher.annotation.Repository;
import team.opentech.usher.assembler.CompanyAssembler;
import team.opentech.usher.dao.CompanyDao;
import team.opentech.usher.pojo.DO.CompanyDO;
import team.opentech.usher.pojo.DTO.CompanyDTO;
import team.opentech.usher.pojo.entity.Company;
import team.opentech.usher.repository.CompanyRepository;
import team.opentech.usher.repository.base.AbstractRepository;
import team.opentech.usher.util.Asserts;
import team.opentech.usher.util.StringUtil;


/**
 * 厂商表(Company)表 仓库实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2022年09月09日 15时45分
 */
@Repository
public class CompanyRepositoryImpl extends AbstractRepository<Company, CompanyDO, CompanyDao, CompanyDTO, CompanyAssembler> implements CompanyRepository {

    protected CompanyRepositoryImpl(CompanyAssembler convert, CompanyDao dao) {
        super(convert, dao);
    }


    @Override
    public Company findByAk(String ak) {
        Asserts.assertTrue(StringUtil.isNotEmpty(ak), "用户名不存在,厂商登录失败!");
        LambdaQueryWrapper<CompanyDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(CompanyDO::getAk, ak);
        CompanyDO companyDO = dao.selectOne(queryWrapper);
        return assembler.toEntity(companyDO);
    }

    @Override
    public List<Company> queryUser(String username) {
        LambdaQueryWrapper<CompanyDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.like(StringUtil.isNotEmpty(username), CompanyDO::getName, username);
        List<CompanyDO> companyDOS = dao.selectList(queryWrapper);
        return assembler.listToEntity(companyDOS);
    }
}
