package top.uhyils.usher.pojo.cqe;

import top.uhyils.usher.pojo.DTO.Point3D;
import top.uhyils.usher.pojo.cqe.command.base.AbstractCommand;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年11月21日 08时33分
 */
public class CreateDeviceCommand extends AbstractCommand {

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
     * 设备连接地址
     */
    private String address;

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


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
