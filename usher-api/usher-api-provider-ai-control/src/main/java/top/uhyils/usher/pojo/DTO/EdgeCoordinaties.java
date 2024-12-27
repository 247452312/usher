package top.uhyils.usher.pojo.DTO;

import java.io.Serializable;
import java.util.List;

/**
 * 边缘坐标(面) 每三个坐标点确定一个面
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年11月14日 17时24分
 */
public class EdgeCoordinaties implements Serializable {

    /**
     * 面边缘坐标点 一般是三个
     */
    private List<Point3D> points;


    public List<Point3D> getPoints() {
        return points;
    }

    public void setPoints(List<Point3D> points) {
        this.points = points;
    }
}
