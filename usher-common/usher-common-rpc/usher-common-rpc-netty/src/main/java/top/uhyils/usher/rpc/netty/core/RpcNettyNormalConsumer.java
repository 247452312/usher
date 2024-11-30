package top.uhyils.usher.rpc.netty.core;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.Future;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import top.uhyils.usher.rpc.annotation.RpcSpi;
import top.uhyils.usher.rpc.exception.RpcTimeOutException;
import top.uhyils.usher.rpc.exchange.pojo.data.RpcData;
import top.uhyils.usher.rpc.netty.AbstractRpcNetty;
import top.uhyils.usher.rpc.netty.callback.ReConnCallBack;
import top.uhyils.usher.rpc.netty.core.handler.RpcConsumerHandler;
import top.uhyils.usher.rpc.netty.pojo.NettyInitDto;
import top.uhyils.usher.rpc.netty.spi.filter.FilterContext;
import top.uhyils.usher.rpc.netty.spi.filter.filter.InvokerChainBuilder;
import top.uhyils.usher.rpc.netty.spi.filter.invoker.LastConsumerInvoker;
import top.uhyils.usher.rpc.netty.spi.filter.invoker.RpcInvoker;
import top.uhyils.usher.rpc.netty.util.FixedLengthQueue;
import top.uhyils.usher.util.LogUtil;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年12月20日 15时06分
 */
@RpcSpi(single = false)
public class RpcNettyNormalConsumer extends AbstractRpcNetty implements RpcNettyConsumer {

    /**
     * 客户端
     */
    protected EventLoopGroup group;

    /**
     * 客户端
     */
    protected Bootstrap client;

    /**
     * 客户端channel
     */
    protected ChannelFuture channelFuture;

    /*如果返回值来了,先判断是不是超时了, 如果超时了, 就直接释放内存, 如果没有超时,就先把值放入rpcResponse,然后唤醒*/

    /**
     * 存储返回值->如果返回值来了 但是还没有wait,就把返回值存在这里
     */
    protected Map<Long, RpcData> rpcResponseMap = new ConcurrentHashMap<>();

    /**
     * 记录等待中的请求 -> 想获取的时候 还没有返回
     */
    protected volatile Map<Long, CountDownLatch> waitLock = new ConcurrentHashMap<>();

    /**
     * 超时的记录在这里,防止返回值的内存溢出
     */
    protected FixedLengthQueue<Long> timeOutUnique = new FixedLengthQueue<>(200, Long.class);

    /**
     * 断线重连成功回调
     */
    protected ReConnCallBack offlineRetrySuccessCallBack;


    @Override
    public void init(Object... params) throws InterruptedException {
        super.init(params);

        this.offlineRetrySuccessCallBack = (ReConnCallBack) params[2];

        this.client = new Bootstrap();
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        this.group = eventLoopGroup;
        this.bootstrap = client;
        this.client.group(eventLoopGroup)
              .channel(NioSocketChannel.class)
              .handler(new LoggingHandler(LogLevel.DEBUG))
              .handler(new ChannelInitializer<NioSocketChannel>() {

                  @Override
                  protected void initChannel(NioSocketChannel ch) {
                      ChannelPipeline p = ch.pipeline();
                      p.addLast("length-decoder", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 3, 4, 9, 0));
                      p.addLast("byte-to-object", new RpcConsumerHandler(getRpcCallBack(), RpcNettyNormalConsumer.this));
                      p.addLast("offline-retry-connect", new RpcFibonacciSequenceRetryHandle(RpcNettyNormalConsumer.this));
                  }
              });

        //连接服务器
        this.channelFuture = client.connect(nettyInitDto.getHost(), nettyInitDto.getPort());
    }

    public NettyInitDto getNettyInitDto() {
        return this.nettyInitDto;
    }

    @Override
    public Boolean shutdown() {
        try {
            if (group != null) {
                Future<?> future = this.group.shutdownGracefully();
                future.get();
            }
            return Boolean.TRUE;
        } catch (Exception e) {
            LogUtil.error(e, "报错啦.夭寿啊");
            return Boolean.FALSE;
        }
    }

    @Override
    public RpcData sendMsg(RpcData rpcData) throws InterruptedException {
        LastConsumerInvoker lastConsumerInvoker = new LastConsumerInvoker(this);
        RpcInvoker rpcInvoker = InvokerChainBuilder.buildConsumerSendInvokerChain(lastConsumerInvoker);
        FilterContext context = new FilterContext(rpcData);
        return rpcInvoker.invoke(context);
    }


    @Override
    public boolean sendMsg(byte[] bytes) {
        ByteBuf buf = Unpooled.buffer();
        buf.writeBytes(bytes);
        channelFuture.channel().writeAndFlush(buf);
        return Boolean.TRUE;
    }

    @Override
    public RpcData wait(Long unique) {
        // 请求第一次
        if (rpcResponseMap.containsKey(unique)) {
            RpcData rpcData = rpcResponseMap.get(unique);
            rpcResponseMap.remove(unique);
            LogUtil.info("请求接收到信息,标识:{}", unique.toString());
            return rpcData;
        }
        try {
            // 等待第一次
            CountDownLatch value = new CountDownLatch(1);
            waitLock.put(unique, value);
            LogUtil.debug("请求进入等待状态,标识:{}", unique.toString());
            //阻塞
            value.await(50000L, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            LogUtil.error(this, e);
        }

        //查询第二次是否存在
        if (rpcResponseMap.containsKey(unique)) {
            RpcData rpcData = rpcResponseMap.get(unique);
            rpcResponseMap.remove(unique);
            LogUtil.debug("请求等待后接收到信息,标识:{}", unique.toString());
            return rpcData;
        }
        LogUtil.debug("请求没有接收到信息,标识:{}", unique.toString());
        timeOutUnique.add(unique);
        throw new RpcTimeOutException("rpc超出最大等待时间:" + getTimeOut());
    }

    /**
     * 请求来了就执行这个
     *
     * @param rpcData
     */
    @Override
    public void put(RpcData rpcData) {
        Long unique = rpcData.unique();
        // 先判断是否曾经执行过并且超时了
        Boolean contain = timeOutUnique.contain(unique);
        if (contain) {
            return;
        }
        LogUtil.debug("返回唯一标示:{}", unique.toString());
        rpcResponseMap.put(unique, rpcData);
        // 尝试唤醒
        awaken(unique);
    }

    @Override
    public boolean isActive() {
        if (channelFuture == null) {
            return false;
        }
        return channelFuture.channel().isActive();
    }

    private void awaken(Long unique) {
        try {
            // 重复等待waitLock 直到等待线程将key置入
            while (!waitLock.containsKey(unique)) {
                // 如果responseMap中不存在结果,说明已经被拿走.可以直接结束线程
                if (!rpcResponseMap.containsKey(unique)) {
                    return;
                }
                Thread.sleep(100L);
            }
        } catch (InterruptedException e) {
            LogUtil.error(this, e);
        }
        CountDownLatch countDownLatch = waitLock.get(unique);
        waitLock.remove(unique);
        //唤醒
        countDownLatch.countDown();


    }
}
