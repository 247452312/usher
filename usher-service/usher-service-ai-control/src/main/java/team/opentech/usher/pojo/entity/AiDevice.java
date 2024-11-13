package team.opentech.usher.pojo.entity;

import team.opentech.usher.annotation.Default;
import team.opentech.usher.pojo.DO.AiDeviceDO;
import team.opentech.usher.pojo.entity.base.AbstractDoEntity;

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

}
