package team.opentech.usher.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import java.util.List;
import javax.annotation.Resource;
import javax.annotation.Resource;
import team.opentech.usher.annotation.NotNull;
import team.opentech.usher.annotation.Repository;
import team.opentech.usher.assembler.CallNodeAssembler;
import team.opentech.usher.context.UserInfoHelper;
import team.opentech.usher.dao.CallNodeDao;
import team.opentech.usher.enums.InvokeTypeEnum;
import team.opentech.usher.mysql.content.MysqlContent;
import team.opentech.usher.pojo.DO.CallNodeDO;
import team.opentech.usher.pojo.DTO.CallNodeDTO;
import team.opentech.usher.pojo.DTO.UserDTO;
import team.opentech.usher.pojo.DTO.base.IdDTO;
import team.opentech.usher.pojo.cqe.CallNodeQuery;
import team.opentech.usher.pojo.entity.CallNode;
import team.opentech.usher.repository.CallNodeRepository;
import team.opentech.usher.repository.NodeRepository;
import team.opentech.usher.repository.ProviderInterfaceRepository;
import team.opentech.usher.repository.base.AbstractRepository;
import team.opentech.usher.util.Asserts;
import team.opentech.usher.util.StringUtil;


/**
 * 调用节点表, 真正调用的节点(CallNode)表 仓库实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2022年09月09日 15时45分
 */
@Repository
public class CallNodeRepositoryImpl extends AbstractRepository<CallNode, CallNodeDO, CallNodeDao, CallNodeDTO, CallNodeAssembler> implements CallNodeRepository {

    @Resource
    private NodeRepository nodeRepository;

    @Resource
    private ProviderInterfaceRepository providerInterfaceRepository;

    protected CallNodeRepositoryImpl(CallNodeAssembler convert, CallNodeDao dao) {
        super(convert, dao);
    }

    @Override
    public List<CallNode> findByUser(UserDTO userDTO) {
        LambdaQueryWrapper<CallNodeDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(CallNodeDO::getCompanyId, userDTO.getId());
        List<CallNodeDO> callNodeDOS = dao.selectList(queryWrapper);
        return assembler.listToEntity(callNodeDOS);
    }

    @NotNull
    @Override
    public CallNode findNodeByDatabaseAndTable(String database, String table, InvokeTypeEnum invokeType) {
        Long companyId = UserInfoHelper.get().map(IdDTO::getId).orElse(null);
        Asserts.assertTrue(companyId != null, "用户未登录");
        LambdaQueryWrapper<CallNodeDO> queryWrapper = Wrappers.lambdaQuery();
        Asserts.assertTrue(StringUtil.isNotEmpty(database), "数据库名称不能为空");
        Asserts.assertTrue(StringUtil.isNotEmpty(table), "表名称不能为空");

        queryWrapper.eq(CallNodeDO::getCompanyId, companyId);
        queryWrapper.eq(CallNodeDO::getInvokeType, invokeType.getCode());
        String url = database + "/" + table;
        queryWrapper.eq(CallNodeDO::getUrl, url);
        CallNodeDO dO = dao.selectOne(queryWrapper);
        Asserts.assertTrue(dO != null, "未查询到指定的节点,名称:{}", url);

        return assembler.toEntity(dO);


    }


    @Override
    public Boolean judgeSysTable(String path) {
        String lowerPath = path.toLowerCase();
        return MysqlContent.SYS_DATABASE.stream().anyMatch(t -> lowerPath.contains(t + MysqlContent.PATH_SEPARATOR));
    }

    @Override
    public List<CallNode> query(Long userId, CallNodeQuery callNodeQuery) {
        LambdaQueryWrapper<CallNodeDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(userId != null, CallNodeDO::getCompanyId, userId);
        List<CallNodeDO> callNodeDOS = dao.selectList(queryWrapper);
        return assembler.listToEntity(callNodeDOS);
    }

}
