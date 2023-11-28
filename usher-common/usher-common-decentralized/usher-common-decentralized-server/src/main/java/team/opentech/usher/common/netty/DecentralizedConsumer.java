package team.opentech.usher.common.netty;

import team.opentech.usher.common.netty.pojo.entity.DecentralizedProtocol;
import team.opentech.usher.enums.DecentralizedRequestTypeEnum;

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
    Boolean send(byte[] body, DecentralizedRequestTypeEnum decentralizedRequestTypeEnum) throws InterruptedException;

    /**
     * 发送信息
     *
     * @param decentralizedProtocol
     */
    Boolean send(DecentralizedProtocol decentralizedProtocol) throws InterruptedException;

    /**
     * 停止/关闭发送信息
     *
     * @return
     */
    Boolean shutdown();

    /**
     * 强制关闭
     */
    void close();
}
