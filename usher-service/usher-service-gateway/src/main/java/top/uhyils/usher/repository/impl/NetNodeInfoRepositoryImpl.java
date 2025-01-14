package top.uhyils.usher.repository.impl;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import java.util.Collections;
import java.util.List;
import javax.annotation.Resource;
import top.uhyils.usher.annotation.Repository;
import top.uhyils.usher.assembler.NetNodeInfoAssembler;
import top.uhyils.usher.dao.NetNodeInfoDao;
import top.uhyils.usher.pojo.DO.NetNodeInfoDO;
import top.uhyils.usher.pojo.DTO.NetNodeInfoDTO;
import top.uhyils.usher.pojo.cqe.command.IdCommand;
import top.uhyils.usher.pojo.entity.CompanyPower;
import top.uhyils.usher.pojo.entity.NetNodeInfo;
import top.uhyils.usher.repository.CompanyPowerRepository;
import top.uhyils.usher.repository.NetNodeInfoRepository;
import top.uhyils.usher.repository.base.AbstractRepository;
import top.uhyils.usher.util.Asserts;
import top.uhyils.usher.util.CollectionUtil;
import top.uhyils.usher.util.StringUtil;


/**
 * 网络调用独立可工作节点(NetNodeInfo)表 仓库实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2025年01月10日 15时22分
 */
@Repository
public class NetNodeInfoRepositoryImpl extends AbstractRepository<NetNodeInfo, NetNodeInfoDO, NetNodeInfoDao, NetNodeInfoDTO, NetNodeInfoAssembler> implements NetNodeInfoRepository {

    @Resource
    private CompanyPowerRepository companyPowerRepository;


    protected NetNodeInfoRepositoryImpl(NetNodeInfoAssembler convert, NetNodeInfoDao dao) {
        super(convert, dao);
    }

    @Override
    public NetNodeInfo findNodeByDatabaseAndTable(Long companyId, String database, String table) {

        Asserts.assertTrue(StringUtil.isNotEmpty(database), "数据库名称不能为空");
        Asserts.assertTrue(StringUtil.isNotEmpty(table), "表名称不能为空");

        List<CompanyPower> powers = companyPowerRepository.findByCompanyId(companyId);
        if (CollectionUtil.isEmpty(powers)) {
            return null;
        }
        LambdaQueryChainWrapper<NetNodeInfoDO> wrapper = lambdaQuery();
        wrapper.in(NetNodeInfoDO::getId, powers.ustream().map(CompanyPower::nodeId).toList());
        wrapper.eq(NetNodeInfoDO::getDatabase, database);
        NetNodeInfoDO dO = wrapper.one();
        Asserts.assertTrue(dO != null, "未查询到指定的节点,库:{},表:{}", database, table);
        return assembler.toEntity(dO);


    }


    @Override
    public List<NetNodeInfo> findByCompanyIdAndDatabase(Long companyId, List<String> databases) {
        List<CompanyPower> powers = companyPowerRepository.findByCompanyId(companyId);
        if (CollectionUtil.isEmpty(powers)) {
            return Collections.emptyList();
        }
        if (CollectionUtil.isEmpty(databases)) {
            return Collections.emptyList();
        }
        LambdaQueryChainWrapper<NetNodeInfoDO> wrapper = lambdaQuery();
        wrapper.in(NetNodeInfoDO::getId, powers.ustream().map(CompanyPower::nodeId).toList());
        wrapper.in(NetNodeInfoDO::getDatabase, databases);
        List<NetNodeInfoDO> list = wrapper.list();
        return assembler.listToEntity(list);
    }

    @Override
    public List<NetNodeInfo> findByCompanyId(IdCommand idCommand) {
        List<CompanyPower> powers = companyPowerRepository.findByCompanyId(idCommand.getId());
        if (CollectionUtil.isEmpty(powers)) {
            return Collections.emptyList();
        }
        LambdaQueryChainWrapper<NetNodeInfoDO> wrapper = lambdaQuery();
        wrapper.in(NetNodeInfoDO::getId, powers.ustream().map(CompanyPower::nodeId).toList());
        List<NetNodeInfoDO> list = wrapper.list();
        return assembler.listToEntity(list);
    }


}
