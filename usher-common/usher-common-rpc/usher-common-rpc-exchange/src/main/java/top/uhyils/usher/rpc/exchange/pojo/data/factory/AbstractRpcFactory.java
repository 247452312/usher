package top.uhyils.usher.rpc.exchange.pojo.data.factory;

import io.netty.buffer.ByteBuf;
import top.uhyils.usher.rpc.enums.RpcTypeEnum;
import top.uhyils.usher.rpc.exchange.pojo.data.RpcData;

/**
 * 抽象rpc体工厂
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年12月21日 10时07分
 */
public abstract class AbstractRpcFactory implements RpcFactory {

    @Override
    public RpcData createByByteBuf(ByteBuf in) throws InterruptedException {
        byte[] bytes = new byte[in.readableBytes()];
        in.readBytes(bytes);
        return createByBytes(bytes);
    }

    /**
     * 获取rpc的类型
     *
     * @return
     */
    protected abstract RpcTypeEnum getRpcType();

}
