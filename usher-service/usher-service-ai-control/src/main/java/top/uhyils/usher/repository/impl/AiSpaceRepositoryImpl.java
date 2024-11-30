package top.uhyils.usher.repository.impl;

import javax.annotation.Resource;
import top.uhyils.usher.annotation.Repository;
import top.uhyils.usher.assembler.AiSpaceAssembler;
import top.uhyils.usher.dao.AiSpaceDao;
import top.uhyils.usher.pojo.DO.AiSpaceDO;
import top.uhyils.usher.pojo.DTO.AiSpaceDTO;
import top.uhyils.usher.pojo.entity.AiSpace;
import top.uhyils.usher.pojo.entity.AiSubspace;
import top.uhyils.usher.repository.AiDeviceRepository;
import top.uhyils.usher.repository.AiSpaceRepository;
import top.uhyils.usher.repository.base.AbstractRepository;


/**
 * 独立空间(AiSpace)表 仓库实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2024年11月13日 09时38分
 */
@Repository
public class AiSpaceRepositoryImpl extends AbstractRepository<AiSpace, AiSpaceDO, AiSpaceDao, AiSpaceDTO, AiSpaceAssembler> implements AiSpaceRepository {

    @Resource
    private AiDeviceRepository deviceRepository;

    protected AiSpaceRepositoryImpl(AiSpaceAssembler convert, AiSpaceDao dao) {
        super(convert, dao);
    }

    @Override
    public AiSpace findByDeviceId(Long deviceId) {
        AiSubspace aiSubspace = deviceRepository.findSubSpaceById(deviceId);
        Long spaceId = aiSubspace.spaceId();
        return find(spaceId);
    }
}
