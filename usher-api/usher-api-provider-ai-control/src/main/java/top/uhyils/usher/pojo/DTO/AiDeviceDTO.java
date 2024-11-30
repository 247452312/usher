package top.uhyils.usher.pojo.DTO;

import org.apache.commons.lang3.builder.ToStringBuilder;
import top.uhyils.usher.pojo.DTO.base.IdDTO;

/**
 * 设备表表(AiDevice)表 对外数据传输载体
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2024年11月13日 20时55分
 */
public class AiDeviceDTO extends IdDTO {

    private static final long serialVersionUID = -1L;

    /**
     * 设备编号
     */
    private String deviceNo;

    /**
     * 设备名称
     */
    private String name;

    /**
     * 所属子空间
     */
    private Long subspaceId;

    /**
     * 设备类型 1-传感器 2-控制器
     */
    private Integer type;

    /**
     * 子类型 详情见枚举
     */
    private Integer subtype;

    /**
     * 相对子空间位置
     */
    private Point3D position;

    /**
     * 相对子空间角度
     */
    private Point3D angle;

    /**
     * 相对主轴旋转角度
     */
    private String rotate;

    public Point3D getPosition() {
        return position;
    }

    public void setPosition(Point3D position) {
        this.position = position;
    }

    public Point3D getAngle() {
        return angle;
    }

    public void setAngle(Point3D angle) {
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
