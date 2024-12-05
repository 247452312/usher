package top.uhyils.usher;

import java.util.concurrent.TimeoutException;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年12月05日 09时38分
 */
public interface SendClient<T, R> {

    /**
     * 发送请求
     *
     * @param request 请求
     *
     * @return 响应
     */
    R send(T request) throws InterruptedException, TimeoutException;

}
