package team.opentech.usher.pojo.entity;

import team.opentech.usher.annotation.Default;
import team.opentech.usher.pojo.DO.AiDeviceInstructionDO;
import team.opentech.usher.pojo.entity.base.AbstractDoEntity;

/**
 * 设备指令表(AiDeviceInstruction)表 数据库实体类
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年11月13日 20时55分
 */
public class AiDeviceInstruction extends AbstractDoEntity<AiDeviceInstructionDO> {

    @Default
    public AiDeviceInstruction(AiDeviceInstructionDO data) {
        super(data);
    }

    public AiDeviceInstruction(Long id) {
        super(id, new AiDeviceInstructionDO());
    }

}
