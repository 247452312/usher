package team.opentech.usher.pojo.entity;

import com.alibaba.fastjson.JSON;
import team.opentech.usher.annotation.Default;
import team.opentech.usher.bus.Bus;
import team.opentech.usher.pojo.DO.AiDeviceDO;
import team.opentech.usher.pojo.DTO.Point3D;
import team.opentech.usher.pojo.entity.base.AbstractDoEntity;
import team.opentech.usher.pojo.event.DeviceRemoveParentEvent;
import team.opentech.usher.repository.base.BaseEntityRepository;

/**
 * 设备表(AiDevice)表 数据库实体类
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年11月13日 20时55分
 */
public class AiDevice extends AbstractDoEntity<AiDeviceDO> {

    @Default
    public AiDevice(AiDeviceDO data) {
        super(data);
    }

    public AiDevice(Long id) {
        super(id, new AiDeviceDO());
    }

    @Override
    public <EN extends AbstractDoEntity<AiDeviceDO>> void removeSelf(BaseEntityRepository<AiDeviceDO, EN> repository) {
        Bus.single().commitAndPush(new DeviceRemoveParentEvent(this));
        super.removeSelf(repository);
    }

    /**
     * 修改位置
     *
     * @param position 位置
     * @param angle    角度
     * @param rotate   旋转角
     */
    public void changePosition(Point3D position, Point3D angle, String rotate) {
        AiDeviceDO data = toDataAndValidate();
        data.setPosition(JSON.toJSONString(position));
        data.setAngle(JSON.toJSONString(angle));
        data.setRotate(rotate);
        onUpdate();
    }

    public void name(String name) {
        AiDeviceDO data = toDataAndValidate();
        data.setName(name);
        onUpdate();
    }

    public void type(Integer type, Integer subtype) {
        AiDeviceDO data = toDataAndValidate();
        data.setType(type);
        data.setSubtype(subtype);
        onUpdate();
    }

    public void subSpace(Long subspaceId) {
        AiDeviceDO data = toDataAndValidate();
        data.setSubspaceId(subspaceId);
        onUpdate();
    }
}
