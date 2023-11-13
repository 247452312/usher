package team.opentech.usher.common.netty;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年11月03日 15时57分
 */
public interface DecentralizedUdpConsumer extends DecentralizedConsumer {


    /**
     * 发送上线信息
     *
     * @return 异步唯一标示
     */
    Long sendOnline() throws InterruptedException;
}
