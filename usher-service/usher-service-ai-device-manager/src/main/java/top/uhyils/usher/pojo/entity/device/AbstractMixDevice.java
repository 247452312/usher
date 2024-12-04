package top.uhyils.usher.pojo.entity.device;

/**
 * 混合设备, 既有控制器又有接收器
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年12月02日 18时55分
 */
public abstract class AbstractMixDevice extends AbstractDevice implements ControlDevice, ReceptorDevice {

    public AbstractMixDevice() {
        super();
    }
}
