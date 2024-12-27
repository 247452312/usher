package top.uhyils.usher.pojo.DO;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import top.uhyils.usher.pojo.DO.base.BaseDO;

/**
 * 设备实时状态表(AiDeviceRealTime)表 数据库实体类
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2024年12月06日 13时57分
 */
@TableName(value = "sys_ai_device_real_time")
public class AiDeviceRealTimeDO extends BaseDO {

    private static final long serialVersionUID = -1L;

    /**
     * 所属设备id
     */
    @TableField
    private Long deviceId;

    /**
     * 唯一标示
     */
    @TableField
    private String uniqueMark;

    /**
     * 相对主轴旋转角度
     */
    @TableField
    private String rotate;

    /**
     * 相对子空间角度
     */
    @TableField
    private String angle;

    /**
     * 相对子空间位置
     */
    @TableField
    private String position;

    /**
     * 连接地址
     */
    @TableField
    private String address;

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public String getUniqueMark() {
        return uniqueMark;
    }

    public void setUniqueMark(String uniqueMark) {
        this.uniqueMark = uniqueMark;
    }

    public String getRotate() {
        return rotate;
    }

    public void setRotate(String rotate) {
        this.rotate = rotate;
    }

    public String getAngle() {
        return angle;
    }

    public void setAngle(String angle) {
        this.angle = angle;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .append("deviceId", getDeviceId())
            .append("uniqueMark", getUniqueMark())
            .append("rotate", getRotate())
            .append("angle", getAngle())
            .append("position", getPosition())
            .append("address", getAddress())
            .toString();
    }
}
