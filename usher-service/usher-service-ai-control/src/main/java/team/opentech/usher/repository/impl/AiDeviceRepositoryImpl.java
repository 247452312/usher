package team.opentech.usher.repository.impl;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import java.util.List;
import team.opentech.usher.annotation.Repository;
import team.opentech.usher.assembler.AiDeviceAssembler;
import team.opentech.usher.dao.AiDeviceDao;
import team.opentech.usher.pojo.DO.AiDeviceDO;
import team.opentech.usher.pojo.DTO.AiDeviceDTO;
import team.opentech.usher.pojo.entity.AiDevice;
import team.opentech.usher.repository.AiDeviceRepository;
import team.opentech.usher.repository.base.AbstractRepository;


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
}
