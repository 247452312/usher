package top.uhyils.usher.pojo.DTO;

import top.uhyils.usher.pojo.DTO.base.IdDTO;

/**
 * 设备实时状态表表(AiDeviceRealTime)表 对外数据传输载体
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2024年12月06日 13时57分
 */
public class AiDeviceRealTimeDTO extends IdDTO {

    private static final long serialVersionUID = -1L;

    /**
     * 所属设备id
     */
    private Long deviceId;

    /**
     * 唯一标示
     */
    private String uniqueMark;

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

    /**
     * 连接地址
     */
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
