package top.uhyils.usher.pojo.entity;

/**
 * 控制器, 控制器可以接收指令,并回应相应的消息或者不回应
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年12月02日 18时30分
 */
public interface ControlDevice extends Device {

    /**
     * 控制
     *
     * @param param
     *
     * @return
     */
    Object control(Object param);

}
