package team.opentech.usher.repository.impl;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import java.util.List;
import team.opentech.usher.annotation.Repository;
import team.opentech.usher.assembler.AiSpaceUserLinkAssembler;
import team.opentech.usher.dao.AiSpaceUserLinkDao;
import team.opentech.usher.pojo.DO.AiSpaceUserLinkDO;
import team.opentech.usher.pojo.DTO.AiSpaceUserLinkDTO;
import team.opentech.usher.pojo.entity.AiSpaceUserLink;
import team.opentech.usher.repository.AiSpaceUserLinkRepository;
import team.opentech.usher.repository.base.AbstractRepository;


/**
 * 独立空间-用户关联表(AiSpaceUserLink)表 仓库实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2024年11月13日 09时38分
 */
@Repository
public class AiSpaceUserLinkRepositoryImpl extends AbstractRepository<AiSpaceUserLink, AiSpaceUserLinkDO, AiSpaceUserLinkDao, AiSpaceUserLinkDTO, AiSpaceUserLinkAssembler> implements AiSpaceUserLinkRepository {

    protected AiSpaceUserLinkRepositoryImpl(AiSpaceUserLinkAssembler convert, AiSpaceUserLinkDao dao) {
        super(convert, dao);
    }


    @Override
    public boolean haveAdmin(Long spaceId) {
        LambdaQueryChainWrapper<AiSpaceUserLinkDO> wrapper = lambdaQuery();
        wrapper.eq(AiSpaceUserLinkDO::getSpaceId, spaceId);
        wrapper.eq(AiSpaceUserLinkDO::getIsAdmin, true);
        Long count = wrapper.count();
        return count != 0;
    }

    @Override
    public boolean checkUsher(Long userId, Long spaceId) {
        LambdaQueryChainWrapper<AiSpaceUserLinkDO> wrapper = lambdaQuery();
        wrapper.eq(AiSpaceUserLinkDO::getUserId, userId);
        wrapper.eq(AiSpaceUserLinkDO::getSpaceId, spaceId);
        return wrapper.count() != 0;
    }

    @Override
    public Boolean removeUser(Long id, Long userId) {
        LambdaUpdateChainWrapper<AiSpaceUserLinkDO> wrapper = lambdaUpdate();
        wrapper.eq(AiSpaceUserLinkDO::getId, id);
        wrapper.eq(AiSpaceUserLinkDO::getUserId, userId);
        return wrapper.remove();
    }

    @Override
    public boolean isAdmin(Long userId, Long spaceId) {
        LambdaQueryChainWrapper<AiSpaceUserLinkDO> wrapper = lambdaQuery();
        wrapper.eq(AiSpaceUserLinkDO::getUserId, userId);
        wrapper.eq(AiSpaceUserLinkDO::getSpaceId, spaceId);
        wrapper.eq(AiSpaceUserLinkDO::getIsAdmin, true);
        return wrapper.count() != 0;
    }

    @Override
    public void removeBySpaceId(Long spaceId) {
        LambdaUpdateChainWrapper<AiSpaceUserLinkDO> wrapper = lambdaUpdate();
        wrapper.eq(AiSpaceUserLinkDO::getSpaceId, spaceId);
        wrapper.remove();
    }

    @Override
    public List<AiSpaceUserLink> findByUserId(Long usherId) {
        LambdaQueryChainWrapper<AiSpaceUserLinkDO> wrapper = lambdaQuery();
        wrapper.eq(AiSpaceUserLinkDO::getUserId, usherId);
        List<AiSpaceUserLinkDO> list = wrapper.list();
        return assembler.listToEntity(list);
    }

    @Override
    public List<AiSpaceUserLink> findBySpaceId(Long spaceId) {
        LambdaQueryChainWrapper<AiSpaceUserLinkDO> wrapper = lambdaQuery();
        wrapper.eq(AiSpaceUserLinkDO::getSpaceId, spaceId);
        List<AiSpaceUserLinkDO> list = wrapper.list();
        return assembler.listToEntity(list);
    }
}
