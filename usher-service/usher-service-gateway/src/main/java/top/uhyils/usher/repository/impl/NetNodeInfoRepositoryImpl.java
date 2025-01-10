package top.uhyils.usher.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import java.util.List;
import top.uhyils.usher.annotation.NotNull;
import top.uhyils.usher.annotation.Repository;
import top.uhyils.usher.assembler.NetNodeInfoAssembler;
import top.uhyils.usher.content.CallNodeContent;
import top.uhyils.usher.context.LoginInfoHelper;
import top.uhyils.usher.dao.NetNodeInfoDao;
import top.uhyils.usher.pojo.DO.NetNodeInfoDO;
import top.uhyils.usher.pojo.DTO.NetNodeInfoDTO;
import top.uhyils.usher.pojo.DTO.UserDTO;
import top.uhyils.usher.pojo.DTO.base.IdDTO;
import top.uhyils.usher.pojo.cqe.CallNodeQuery;
import top.uhyils.usher.pojo.entity.NetNodeInfo;
import top.uhyils.usher.repository.NetNodeInfoRepository;
import top.uhyils.usher.repository.base.AbstractRepository;
import top.uhyils.usher.util.Asserts;
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

    protected NetNodeInfoRepositoryImpl(NetNodeInfoAssembler convert, NetNodeInfoDao dao) {
        super(convert, dao);
    }

    @Override
    public List<NetNodeInfo> findByUser(UserDTO userDTO) {
        LambdaQueryWrapper<NetNodeInfoDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(NetNodeInfoDO::getCompanyId, userDTO.getId());
        List<NetNodeInfoDO> callNodeDOS = dao.selectList(queryWrapper);
        return assembler.listToEntity(callNodeDOS);
    }

    @NotNull
    @Override
    public NetNodeInfo findNodeByDatabaseAndTable(String database, String table) {
        Long companyId = LoginInfoHelper.get().map(IdDTO::getId).orElse(null);
        Asserts.assertTrue(companyId != null, "用户未登录");
        LambdaQueryWrapper<NetNodeInfoDO> queryWrapper = Wrappers.lambdaQuery();
        Asserts.assertTrue(StringUtil.isNotEmpty(database), "数据库名称不能为空");
        Asserts.assertTrue(StringUtil.isNotEmpty(table), "表名称不能为空");

        queryWrapper.eq(NetNodeInfoDO::getCompanyId, companyId);
        queryWrapper.eq(NetNodeInfoDO::getDatabase, database);
        queryWrapper.eq(NetNodeInfoDO::getTable, table);
        NetNodeInfoDO dO = dao.selectOne(queryWrapper);
        Asserts.assertTrue(dO != null, "未查询到指定的节点,库:{},表:{}", database, table);

        return assembler.toEntity(dO);


    }


    @Override
    public Boolean judgeSysTable(String database) {
        return CallNodeContent.SYS_DATABASE.contains(database);
    }

    @Override
    public List<NetNodeInfo> query(Long userId, CallNodeQuery callNodeQuery) {
        LambdaQueryWrapper<NetNodeInfoDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(userId != null, NetNodeInfoDO::getCompanyId, userId);
        List<NetNodeInfoDO> callNodeDOS = dao.selectList(queryWrapper);
        return assembler.listToEntity(callNodeDOS);
    }

    @Override
    public List<NetNodeInfo> findByCompanyIdAndDatabase(Long companyId, String database) {
        LambdaQueryChainWrapper<NetNodeInfoDO> wrapper = lambdaQuery();
        wrapper.eq(NetNodeInfoDO::getCompanyId, companyId);
        wrapper.eq(NetNodeInfoDO::getDatabase, database);
        List<NetNodeInfoDO> list = wrapper.list();
        return assembler.listToEntity(list);
    }


}
