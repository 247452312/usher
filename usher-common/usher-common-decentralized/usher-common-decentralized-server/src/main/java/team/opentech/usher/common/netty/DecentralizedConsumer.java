package team.opentech.usher.common.netty;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年11月03日 15时57分
 */
public interface DecentralizedConsumer {

    /**
     * 发送信息
     *
     * @param body
     */
    Boolean send(byte[] body) throws InterruptedException;

    /**
     * 停止/关闭发送信息
     *
     * @return
     */
    Boolean shutdown();

}
