package top.uhyils.usher.rpc.netty.core;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.EventLoop;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import top.uhyils.usher.rpc.netty.pojo.NettyInitDto;
import top.uhyils.usher.util.LogUtil;

/**
 * 斐波那契重试序列
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年11月12日 11时34分
 */
public class RpcFibonacciSequenceRetryHandle extends ChannelInboundHandlerAdapter {

    /**
     * 观察者
     */
    private RpcNettyNormalConsumer rpcNettyNormalConsumerObv;

    /**
     * 最大重试次数
     */
    private Integer retryMaxCount = 100;

    /**
     * 最长间隔重试时间
     */
    private Integer retryMaxTime = 60;

    /**
     * 初始重试次数
     */
    private int currentRetryCount = 0;

    /**
     * 初始重试间隔 1秒
     */
    private long delayFirst = 1L;

    /**
     * 初始重试间隔 1秒
     */
    private long delaySecond = 1L;


    public RpcFibonacciSequenceRetryHandle(RpcNettyNormalConsumer rpcNettyNormalConsumer) {
        this.rpcNettyNormalConsumerObv = rpcNettyNormalConsumer;
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        rpcNettyNormalConsumerObv.offlineRetrySuccessCallBack.onOffLine(this.rpcNettyNormalConsumerObv.getNettyInitDto());
        processRetryConnect(ctx);
    }


    public void processRetryConnect(ChannelHandlerContext ctx) {
        if (Objects.isNull(ctx)) {
            LogUtil.error("处理器都不存在!!!");
            // 关闭整个客户端, 使整个netty应用停止
            rpcNettyNormalConsumerObv.client.config().group().shutdownGracefully();
            return;
        }

        if (currentRetryCount >= retryMaxCount) {
            LogUtil.error("重试已达最大次数, 取消重试, 关闭客户端!!!");
            // 关闭整个客户端, 使整个netty应用停止
            rpcNettyNormalConsumerObv.client.config().group().shutdownGracefully();
            return;
        }

        long delay = this.getAndAddDelay();
        EventLoop eventLoop = ctx.channel().eventLoop();
        eventLoop.schedule(() -> {
            try {
                currentRetryCount++;
                int count = this.currentRetryCount;

                NettyInitDto nettyInitDto = rpcNettyNormalConsumerObv.getNettyInitDto();
                rpcNettyNormalConsumerObv.sendClient = new RpcNettySendClient(rpcNettyNormalConsumerObv.client.connect(nettyInitDto.getHost(), nettyInitDto.getPort()));
                rpcNettyNormalConsumerObv.sendClient.channelFuture().addListener(future -> {
                    // 触发事件
                    boolean futureSuccess = future.isSuccess();
                    // 失败后重调递归调
                    if (!futureSuccess) {
                        this.processRetryConnect(ctx);
                    } else {
                        // 成功后重置次数和延迟时间
                        this.currentRetryCount = 0;
                        this.delayFirst = 1L;
                        this.delaySecond = 1L;
                        Optional.ofNullable(rpcNettyNormalConsumerObv.offlineRetrySuccessCallBack).ifPresent(el -> el.onReConn(nettyInitDto));
                    }
                    LogUtil.info("重连, 当前次数:{}, 当前延迟重试间隔:{}秒, 重试结果:{}", count, delay, futureSuccess);
                });
                rpcNettyNormalConsumerObv.sendClient.channelFuture().sync();
            } catch (Exception e) {
                LogUtil.error("重连失败, 原因: ", e);
            }
        }, delay, TimeUnit.SECONDS);
        // 传递给下一个处理器
        ctx.fireChannelInactive();
    }

    /**
     * 获取延迟,并且斐波那契增加
     *
     * @return
     */
    private long getAndAddDelay() {
        long realDelay = delayFirst + delaySecond;
        delayFirst = delaySecond;
        delaySecond = realDelay;
        if (realDelay >= retryMaxTime) {
            return retryMaxTime;
        }
        return realDelay;
    }


}
