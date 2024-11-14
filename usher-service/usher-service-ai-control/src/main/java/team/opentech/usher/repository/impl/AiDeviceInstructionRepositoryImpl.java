package team.opentech.usher.repository.impl;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import java.util.List;
import team.opentech.usher.annotation.Repository;
import team.opentech.usher.assembler.AiDeviceInstructionAssembler;
import team.opentech.usher.dao.AiDeviceInstructionDao;
import team.opentech.usher.pojo.DO.AiDeviceInstructionDO;
import team.opentech.usher.pojo.DTO.AiDeviceInstructionDTO;
import team.opentech.usher.pojo.entity.AiDeviceInstruction;
import team.opentech.usher.repository.AiDeviceInstructionRepository;
import team.opentech.usher.repository.base.AbstractRepository;


/**
 * 设备指令表(AiDeviceInstruction)表 仓库实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2024年11月13日 20时55分
 */
@Repository
public class AiDeviceInstructionRepositoryImpl extends AbstractRepository<AiDeviceInstruction, AiDeviceInstructionDO, AiDeviceInstructionDao, AiDeviceInstructionDTO, AiDeviceInstructionAssembler> implements AiDeviceInstructionRepository {

    protected AiDeviceInstructionRepositoryImpl(AiDeviceInstructionAssembler convert, AiDeviceInstructionDao dao) {
        super(convert, dao);
    }


    @Override
    public List<AiDeviceInstruction> findByDeviceId(Long deviceId) {
        LambdaQueryChainWrapper<AiDeviceInstructionDO> wrapper = lambdaQuery();
        wrapper.eq(AiDeviceInstructionDO::getDeviceId, deviceId);
        List<AiDeviceInstructionDO> list = wrapper.list();
        return assembler.listToEntity(list);
    }

    @Override
    public void removeByDeviceId(Long deviceId) {
        LambdaUpdateChainWrapper<AiDeviceInstructionDO> wrapper = lambdaUpdate();
        wrapper.eq(AiDeviceInstructionDO::getDeviceId, deviceId);
        wrapper.remove();
    }
}
