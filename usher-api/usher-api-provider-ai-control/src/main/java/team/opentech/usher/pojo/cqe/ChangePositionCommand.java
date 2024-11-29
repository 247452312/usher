package team.opentech.usher.pojo.cqe;

import team.opentech.usher.pojo.DTO.Point3D;
import team.opentech.usher.pojo.cqe.command.base.AbstractCommand;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年11月21日 08时35分
 */
public class ChangePositionCommand extends AbstractCommand {

    /**
     * 设备id
     */
    private Long id;


    /**
     * 修改后位置坐标
     */
    private Point3D position;

    /**
     * 修改后角度
     */
    private Point3D angle;


    /**
     * 修改后旋转角度
     */
    private String rotate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
