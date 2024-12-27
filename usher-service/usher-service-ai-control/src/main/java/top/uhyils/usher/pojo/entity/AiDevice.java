package top.uhyils.usher.pojo.entity;

import top.uhyils.usher.annotation.Default;
import top.uhyils.usher.bus.Bus;
import top.uhyils.usher.enums.AiDeviceStatusEnum;
import top.uhyils.usher.pojo.DO.AiDeviceDO;
import top.uhyils.usher.pojo.entity.base.AbstractDoEntity;
import top.uhyils.usher.pojo.event.DeviceRemoveParentEvent;
import top.uhyils.usher.repository.AiDeviceRealTimeRepository;
import top.uhyils.usher.repository.base.BaseEntityRepository;
import top.uhyils.usher.util.SpringUtil;

/**
 * 设备表(AiDevice)表 数据库实体类
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年11月13日 20时55分
 */
public class AiDevice extends AbstractDoEntity<AiDeviceDO> {

    private AiDeviceRealTime realTime;

    @Default
    public AiDevice(AiDeviceDO data) {
        super(data);
    }

    public AiDevice(Long id) {
        super(id, new AiDeviceDO());
    }

    /**
     * 填充实时数据
     *
     * @param realTime
     */
    public void fillRealTime(AiDeviceRealTime realTime) {
        this.realTime = realTime;
    }

    @Override
    public <EN extends AbstractDoEntity<AiDeviceDO>> void removeSelf(BaseEntityRepository<AiDeviceDO, EN> repository) {
        Bus.single().commitAndPush(new DeviceRemoveParentEvent(this));
        super.removeSelf(repository);
    }


    public void changeName(String name) {
        AiDeviceDO data = toDataAndValidate();
        data.setName(name);
        onUpdate();
    }

    public void changeType(Integer type) {
        AiDeviceDO data = toDataAndValidate();
        data.setType(type);
        onUpdate();
    }

    public void changeSubSpace(Long subspaceId) {
        AiDeviceDO data = toDataAndValidate();
        data.setSubspaceId(subspaceId);
        onUpdate();
    }

    @Override
    public <EN extends AbstractDoEntity<AiDeviceDO>> void saveSelf(BaseEntityRepository<AiDeviceDO, EN> repository) {
        super.saveSelf(repository);
        if (realTime != null) {
            AiDeviceDO dataAndValidate = toDataAndValidate();
            realTime.fillDeviceInfo(dataAndValidate.getId(), dataAndValidate.getUniqueMark());
            realTime.saveSelf(SpringUtil.getBean(AiDeviceRealTimeRepository.class));
        }
    }

    /**
     * 设备类型
     *
     * @return
     */
    public AiDeviceStatusEnum type() {
        return AiDeviceStatusEnum.getByCode(toDataAndValidate().getType());
    }

    public String uniqueMark() {
        return toDataAndValidate().getUniqueMark();
    }
}
