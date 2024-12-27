package top.uhyils.usher.pojo.entity;

import top.uhyils.usher.annotation.Default;
import top.uhyils.usher.pojo.DO.AiDeviceRealTimeDO;
import top.uhyils.usher.pojo.entity.base.AbstractDoEntity;

/**
 * 设备实时状态表(AiDeviceRealTime)表 数据库实体类
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年12月06日 13时57分
 */
public class AiDeviceRealTime extends AbstractDoEntity<AiDeviceRealTimeDO> {

    @Default
    public AiDeviceRealTime(AiDeviceRealTimeDO data) {
        super(data);
    }

    public AiDeviceRealTime(Long id) {
        super(id, new AiDeviceRealTimeDO());
    }

    /**
     * 填充设备信息
     */
    public void fillDeviceInfo(Long id, String uniqueMark) {
        AiDeviceRealTimeDO dataAndValidate = toDataAndValidate();
        dataAndValidate.setDeviceId(id);
        dataAndValidate.setUniqueMark(uniqueMark);
        onUpdate();
    }
}
