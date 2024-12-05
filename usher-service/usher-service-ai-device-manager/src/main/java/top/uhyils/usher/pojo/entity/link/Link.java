package top.uhyils.usher.pojo.entity.link;

import java.util.function.Consumer;

/**
 * 连接插件,支持自定义扩展
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年12月04日 19时58分
 */
public interface Link {

    /**
     * 连接相应与回调
     *
     * @param consumer
     */
    <T> void callBack(Consumer<T> consumer);

    /**
     * 发送请求,异步
     *
     * @param request
     */
    void request(Object request);

    /**
     * 发送请求,同步
     *
     * @param request
     */
    Object requestSync(Object request);


    /**
     * 设备所在的ip
     *
     * @return 设备所在的ip, 网络设备则返回对应的网络ip
     */
    String ip();


    /**
     * 尝试连接
     */
    void tryLink();
}
