package team.opentech.usher.pojo.entity;

import team.opentech.usher.annotation.Default;
import team.opentech.usher.pojo.DO.AiDeviceInstructionDO;
import team.opentech.usher.pojo.entity.base.AbstractDoEntity;
import team.opentech.usher.util.IdUtil;
import team.opentech.usher.util.SpringUtil;

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

    public void changeDevice(Long targetDeviceId) {
        AiDeviceInstructionDO data = toDataAndValidate();
        data.setDeviceId(targetDeviceId);
        onUpdate();
    }

    /**
     * 生成设备编号
     */
    public void generateNo() {
        IdUtil idUtil = SpringUtil.getBean(IdUtil.class);
        AiDeviceInstructionDO data = toDataAndValidate();
        data.setDeviceInstructionNo(idUtil.newId() + "");
        onUpdate();
    }
}
