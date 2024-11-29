package team.opentech.usher.pojo.DO;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import team.opentech.usher.pojo.DO.base.BaseDO;

/**
 * 设备表(AiDevice)表 数据库实体类
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2024年11月13日 20时55分
 */
@TableName(value = "sys_ai_device")
public class AiDeviceDO extends BaseDO {

    private static final long serialVersionUID = -1L;

    /**
     * 设备编号
     */
    @TableField
    private String deviceNo;

    /**
     * 设备名称
     */
    @TableField
    private String name;

    /**
     * 所属子空间
     */
    @TableField
    private Long subspaceId;

    /**
     * 设备类型 1-传感器 2-控制器
     */
    @TableField
    private Integer type;

    /**
     * 子类型 详情见枚举
     */
    @TableField
    private Integer subtype;

    /**
     * 相对子空间位置
     */
    @TableField
    private String position;

    /**
     * 相对子空间角度
     */
    @TableField
    private String angle;

    /**
     * 相对主轴旋转角度
     */
    @TableField
    private String rotate;

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getAngle() {
        return angle;
    }

    public void setAngle(String angle) {
        this.angle = angle;
    }

    public String getRotate() {
        return rotate;
    }

    public void setRotate(String rotate) {
        this.rotate = rotate;
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getSubspaceId() {
        return subspaceId;
    }

    public void setSubspaceId(Long subspaceId) {
        this.subspaceId = subspaceId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getSubtype() {
        return subtype;
    }

    public void setSubtype(Integer subtype) {
        this.subtype = subtype;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .append("deviceNo", getDeviceNo())
            .append("name", getName())
            .append("subspaceId", getSubspaceId())
            .append("type", getType())
            .append("subtype", getSubtype())
            .toString();
    }
}
