package top.uhyils.usher.pojo.entity;

import top.uhyils.usher.annotation.Default;
import top.uhyils.usher.facade.DeviceManageFacade;
import top.uhyils.usher.pojo.DO.AiDeviceInstructionDO;
import top.uhyils.usher.pojo.entity.base.AbstractDoEntity;
import top.uhyils.usher.util.IdUtil;
import top.uhyils.usher.util.SpringUtil;

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
     * 生成设备指令编号
     */
    public void generateNo() {
        IdUtil idUtil = SpringUtil.getBean(IdUtil.class);
        AiDeviceInstructionDO data = toDataAndValidate();
        data.setDeviceInstructionNo(idUtil.newId() + "");
        onUpdate();
    }

    public Object executeInstruction(DeviceManageFacade deviceManageFacade) {
        AiDeviceInstructionDO data = toDataAndValidate();
        return deviceManageFacade.executeInstruction(data.getDeviceInstructionNo(), data.getContext(), data.getUniqueMark());
    }

    public void fillUniqueMark(String uniqueMark) {
        AiDeviceInstructionDO dataAndValidate = toDataAndValidate();
        dataAndValidate.setUniqueMark(uniqueMark);
    }
}
