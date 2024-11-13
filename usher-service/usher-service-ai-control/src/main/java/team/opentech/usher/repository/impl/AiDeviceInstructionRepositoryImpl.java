package team.opentech.usher.repository.impl;

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


}
