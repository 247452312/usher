package top.uhyils.usher.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import java.util.List;
import top.uhyils.usher.annotation.Repository;
import top.uhyils.usher.assembler.CompanyAssembler;
import top.uhyils.usher.dao.CompanyDao;
import top.uhyils.usher.pojo.DO.CompanyDO;
import top.uhyils.usher.pojo.DTO.CompanyDTO;
import top.uhyils.usher.pojo.entity.Company;
import top.uhyils.usher.repository.CompanyRepository;
import top.uhyils.usher.repository.base.AbstractRepository;
import top.uhyils.usher.util.Asserts;
import top.uhyils.usher.util.StringUtil;


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
