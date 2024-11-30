package top.uhyils.usher.repository.impl;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import java.util.List;
import javax.annotation.Resource;
import top.uhyils.usher.annotation.Repository;
import top.uhyils.usher.assembler.AiDeviceAssembler;
import top.uhyils.usher.dao.AiDeviceDao;
import top.uhyils.usher.pojo.DO.AiDeviceDO;
import top.uhyils.usher.pojo.DTO.AiDeviceDTO;
import top.uhyils.usher.pojo.entity.AiDevice;
import top.uhyils.usher.pojo.entity.AiSubspace;
import top.uhyils.usher.repository.AiDeviceRepository;
import top.uhyils.usher.repository.AiSubspaceRepository;
import top.uhyils.usher.repository.base.AbstractRepository;


/**
 * 设备表(AiDevice)表 仓库实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2024年11月13日 20时55分
 */
@Repository
public class AiDeviceRepositoryImpl extends AbstractRepository<AiDevice, AiDeviceDO, AiDeviceDao, AiDeviceDTO, AiDeviceAssembler> implements AiDeviceRepository {

    protected AiDeviceRepositoryImpl(AiDeviceAssembler convert, AiDeviceDao dao) {
        super(convert, dao);
    }


    @Override
    public List<AiDevice> findBySubSpaceId(Long subspaceId) {
        LambdaQueryChainWrapper<AiDeviceDO> wrapper = lambdaQuery();
        wrapper.eq(AiDeviceDO::getSubspaceId, subspaceId);
        List<AiDeviceDO> list = wrapper.list();
        return assembler.listToEntity(list);
    }

    @Override
    public void removeBySubSpaceId(Long subSpaceId) {
        LambdaUpdateChainWrapper<AiDeviceDO> wrapper = lambdaUpdate();
        wrapper.eq(AiDeviceDO::getSubspaceId, subSpaceId);
        wrapper.set(AiDeviceDO::getDeleteFlag, true);
        wrapper.update();
    }

    @Override
    public List<AiDevice> findBySubSpaceIds(List<Long> subSpaceIds) {
        LambdaQueryChainWrapper<AiDeviceDO> wrapper = lambdaQuery();
        wrapper.eq(AiDeviceDO::getSubspaceId, subSpaceIds);
        List<AiDeviceDO> list = wrapper.list();
        return assembler.listToEntity(list);
    }

    @Resource
    private AiSubspaceRepository subspaceRepository;

    @Override
    public AiSubspace findSubSpaceById(Long deviceId) {
        Long subSpaceId = dao.findSubSpaceIdById(deviceId);
        return subspaceRepository.find(subSpaceId);
    }
}
