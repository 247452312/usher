package team.opentech.usher.pojo.DTO;

import java.io.Serializable;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年11月14日 17时25分
 */
public class Point3D implements Serializable {

    /**
     * x坐标
     */
    private Float x;

    /**
     * y坐标
     */
    private Float y;

    /**
     * z坐标
     */
    private Float z;


    public Float getX() {
        return x;
    }

    public void setX(Float x) {
        this.x = x;
    }

    public Float getY() {
        return y;
    }

    public void setY(Float y) {
        this.y = y;
    }

    public Float getZ() {
        return z;
    }

    public void setZ(Float z) {
        this.z = z;
    }
}
