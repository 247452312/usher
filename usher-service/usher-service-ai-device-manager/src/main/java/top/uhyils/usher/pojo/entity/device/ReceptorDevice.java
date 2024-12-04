package top.uhyils.usher.pojo.entity.device;

/**
 * 感受器本身可以主动向系统推送状态, 也可以由系统主动获取
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年12月02日 17时31分
 */
public interface ReceptorDevice extends Device {


    /**
     * 获取信息,主动获取设备当前状态
     *
     * @param request
     *
     * @return
     */
    Object findMsg(Object request);
}
