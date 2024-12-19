package top.uhyils.usher.mysql.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelInboundHandlerAdapter;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.jetbrains.annotations.Nullable;
import top.uhyils.usher.context.LoginInfoHelper;
import top.uhyils.usher.exception.AssertException;
import top.uhyils.usher.mysql.content.MysqlContent;
import top.uhyils.usher.mysql.decode.MysqlDecoder;
import top.uhyils.usher.mysql.enums.MysqlCommandTypeEnum;
import top.uhyils.usher.mysql.enums.MysqlHandlerStatusEnum;
import top.uhyils.usher.mysql.pojo.cqe.MysqlCommand;
import top.uhyils.usher.mysql.pojo.cqe.impl.ComBinlogDumpCommand;
import top.uhyils.usher.mysql.pojo.cqe.impl.ComChangeUserCommand;
import top.uhyils.usher.mysql.pojo.cqe.impl.ComConnectCommand;
import top.uhyils.usher.mysql.pojo.cqe.impl.ComConnectOutCommand;
import top.uhyils.usher.mysql.pojo.cqe.impl.ComCreateDbCommand;
import top.uhyils.usher.mysql.pojo.cqe.impl.ComDebugCommand;
import top.uhyils.usher.mysql.pojo.cqe.impl.ComDelayedInsertCommand;
import top.uhyils.usher.mysql.pojo.cqe.impl.ComDropDbCommand;
import top.uhyils.usher.mysql.pojo.cqe.impl.ComFieldListCommand;
import top.uhyils.usher.mysql.pojo.cqe.impl.ComInitDbCommand;
import top.uhyils.usher.mysql.pojo.cqe.impl.ComPingCommand;
import top.uhyils.usher.mysql.pojo.cqe.impl.ComProcessInfoCommand;
import top.uhyils.usher.mysql.pojo.cqe.impl.ComProcessKillCommand;
import top.uhyils.usher.mysql.pojo.cqe.impl.ComQueryCommand;
import top.uhyils.usher.mysql.pojo.cqe.impl.ComQuitCommand;
import top.uhyils.usher.mysql.pojo.cqe.impl.ComRefreshCommand;
import top.uhyils.usher.mysql.pojo.cqe.impl.ComRegisterSlaveCommand;
import top.uhyils.usher.mysql.pojo.cqe.impl.ComSetOptionCommand;
import top.uhyils.usher.mysql.pojo.cqe.impl.ComShutdownCommand;
import top.uhyils.usher.mysql.pojo.cqe.impl.ComSleepCommand;
import top.uhyils.usher.mysql.pojo.cqe.impl.ComStatisticsCommand;
import top.uhyils.usher.mysql.pojo.cqe.impl.ComStmtCloseCommand;
import top.uhyils.usher.mysql.pojo.cqe.impl.ComStmtExecuteCommand;
import top.uhyils.usher.mysql.pojo.cqe.impl.ComStmtFetchCommand;
import top.uhyils.usher.mysql.pojo.cqe.impl.ComStmtPrepareCommand;
import top.uhyils.usher.mysql.pojo.cqe.impl.ComStmtResetCommand;
import top.uhyils.usher.mysql.pojo.cqe.impl.ComStmtSendLongDataCommand;
import top.uhyils.usher.mysql.pojo.cqe.impl.ComTableDumpCommand;
import top.uhyils.usher.mysql.pojo.cqe.impl.ComTimeCommand;
import top.uhyils.usher.mysql.pojo.cqe.impl.MysqlAuthCommand;
import top.uhyils.usher.mysql.pojo.entity.MysqlTcpLink;
import top.uhyils.usher.mysql.pojo.response.MysqlResponse;
import top.uhyils.usher.mysql.pojo.response.impl.AuthResponse;
import top.uhyils.usher.mysql.pojo.response.impl.ErrResponse;
import top.uhyils.usher.mysql.util.MysqlUtil;
import top.uhyils.usher.mysql.util.Proto;
import top.uhyils.usher.util.Asserts;
import top.uhyils.usher.util.CollectionUtil;
import top.uhyils.usher.util.LogUtil;

/**
 * mysql协议的处理器
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2022年03月22日 19时19分
 */
public class MysqlInfoHandler extends ChannelInboundHandlerAdapter implements ChannelInboundHandler {


    /**
     * 连接
     */
    private Channel mysqlChannel;


    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        super.handlerRemoved(ctx);
        MysqlContent.MYSQL_TCP_INFO.remove();
    }

    /**
     * 初始化连接
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);

        this.mysqlChannel = ctx.channel();
        //入口连接
        InetSocketAddress inetSocketAddress = (InetSocketAddress) mysqlChannel.localAddress();

        MysqlTcpLink value = MysqlTcpLink.build(mysqlChannel.id(), inetSocketAddress);
        value.addToLocalCache();

        LogUtil.info("mysql 连接! ip:{}", inetSocketAddress.toString());
        AuthResponse authResponse = AuthResponse.build();
        List<byte[]> msgs = authResponse.toByte();
        value.getAndIncrementStatus();
        value.addIndex();

        for (byte[] msg : msgs) {
            LogUtil.debug(() -> "mysql服务端初始发送握手信息:\n" + MysqlUtil.dump(msg));
            send(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        Channel channel = ctx.channel();
        if (channel.isActive()) {
            ctx.close();
        }
    }

    /**
     * 接收到信息时调用
     *
     * @param ctx
     * @param msg
     *
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);

        /**
         * 因{@link MysqlDecoder#decode} 所以这里一定为byte[]
         */
        byte[] mysqlBytes = (byte[]) msg;

        MysqlTcpLink mysqlTcpLink = MysqlTcpLink.findByCache();
        // mysql 此次请求携带的信息
        //初始化index为客户端发过来的index
        mysqlTcpLink.changeIndex(findIndex(mysqlBytes));
        try {
            // 1.判断请求登录情况
            MysqlCommand mysqlCommand = findCommandByStatus(mysqlTcpLink, mysqlBytes);
            sendResponse(mysqlCommand.invoke());
            mysqlTcpLink.getAndIncrementStatus();

        } catch (AssertException e) {
            LogUtil.error(e);
            sendResponse(ErrResponse.build(e.getLocalizedMessage()));
            closeOnFlush();
        } finally {
            LoginInfoHelper.clean();
        }
    }

    /**
     * 关闭
     */
    public void closeOnFlush() {
        if (mysqlChannel != null && mysqlChannel.isActive()) {
            mysqlChannel.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
        }
    }

    /**
     * 获取初始化index为客户端发过来的index
     *
     * @param mysqlBytes
     *
     * @return
     */
    private long findIndex(byte[] mysqlBytes) {
        Proto proto = new Proto(mysqlBytes);
        proto.getFixedInt(3);
        return proto.getFixedInt(1);
    }

    @Nullable
    private MysqlCommand findCommandByStatus(MysqlTcpLink mysqlTcpLink, byte[] mysqlBytes) {
        MysqlHandlerStatusEnum status = mysqlTcpLink.status();
        MysqlCommand mysqlCommand = null;
        switch (status) {
            case FIRST_SIGHT:
                // 第一次见,默认为登录请求
                mysqlCommand = new MysqlAuthCommand(mysqlBytes);
                break;
            case PASSED:
                // 其他状态,正确接收请求
                LoginInfoHelper.setUser(mysqlTcpLink.findUserDTO());
                LoginInfoHelper.setIp(mysqlTcpLink.findLocalAddress().getAddress().getHostAddress());
                mysqlCommand = parseForCommand(mysqlBytes);
                break;
            case OVER:
                // 已经结束,不再接收请求
            default:
                Asserts.throwException("请求已经结束,请不要再次请求");
        }
        return mysqlCommand;
    }

    /**
     * 发送数据
     *
     * @param msg
     */
    private void send(byte[] msg) {
        ByteBuf buf = Unpooled.buffer();
        buf.writeBytes(msg);
        this.mysqlChannel.writeAndFlush(buf);
    }

    /**
     * 发送回应
     *
     * @param invokes
     */
    private void sendResponse(List<MysqlResponse> invokes) {
        if (CollectionUtil.isEmpty(invokes)) {
            return;
        }
        try {
            List<byte[]> finalResponse = new ArrayList<>();
            for (MysqlResponse mysqlResponse : invokes) {
                finalResponse.addAll(mysqlResponse.toByte());
            }
            byte[] bytes = MysqlUtil.mergeListBytes(finalResponse);
            String responseBytes = MysqlUtil.dump(bytes);
            LogUtil.debug("mysql回应:\n" + responseBytes);
            send(bytes);
        } catch (AssertException ae) {
            throw ae;
        } catch (Exception e) {
            LogUtil.error(this, e);
            Asserts.throwException("系统错误,请联系管理员或查询日志!");
        }
    }

    /**
     * 发送回应
     *
     * @param invoke
     */
    private void sendResponse(MysqlResponse invoke) {
        sendResponse(Collections.singletonList(invoke));
    }

    /**
     * 处理请求
     */
    private MysqlCommand parseForCommand(byte[] mysqlBytes) {
        // 2.判断为已登录,加载并解析请求
        MysqlCommandTypeEnum load = MysqlUtil.load(mysqlBytes);
        // 3.获取请求
        return doFindCommand(mysqlBytes, load);
    }

    /**
     * 根据不同请求获取结果
     */
    private MysqlCommand doFindCommand(byte[] mysqlBytes, MysqlCommandTypeEnum parse) {
        switch (parse) {
            /*这里是需要发送往后台进行处理的请求类型*/
            case COM_QUERY:
                // sql查询请求
                return new ComQueryCommand(mysqlBytes);
            case COM_FIELD_LIST:
                // 字段获取请求
                return new ComFieldListCommand(mysqlBytes);

            case COM_TABLE_DUMP:
                // 表结构获取请求
                return new ComTableDumpCommand(mysqlBytes);

            case COM_STMT_EXECUTE:
                // 执行预处理语句
                return new ComStmtExecuteCommand(mysqlBytes);


            /*以下是不需要发送往服务器进行处理的请求类型*/

            /* 这里是和服务器相关的请求类型*/
            case COM_PROCESS_INFO:
                return new ComProcessInfoCommand(mysqlBytes);

            case COM_PROCESS_KILL:
                return new ComProcessKillCommand(mysqlBytes);

            case COM_STATISTICS:
                return new ComStatisticsCommand(mysqlBytes);

            /*正常请求*/
            case COM_STMT_SEND_LONG_DATA:
                return new ComStmtSendLongDataCommand(mysqlBytes);

            case COM_STMT_PREPARE:
                return new ComStmtPrepareCommand(mysqlBytes);

            case COM_PING:
                return new ComPingCommand(mysqlBytes);

            case COM_QUIT:
                return new ComQuitCommand(mysqlBytes);

            case COM_TIME:
                return new ComTimeCommand(mysqlBytes);

            case COM_DEBUG:
                return new ComDebugCommand(mysqlBytes);

            case COM_SLEEP:
                return new ComSleepCommand(mysqlBytes);

            case COM_CONNECT:
                return new ComConnectCommand(mysqlBytes);

            case COM_DROP_DB:
                return new ComDropDbCommand(mysqlBytes);

            case COM_INIT_DB:
                return new ComInitDbCommand(mysqlBytes);

            case COM_REFRESH:
                return new ComRefreshCommand(mysqlBytes);

            case COM_SHUTDOWN:
                return new ComShutdownCommand(mysqlBytes);

            case COM_CREATE_DB:
                return new ComCreateDbCommand(mysqlBytes);

            case COM_SET_OPTION:
                return new ComSetOptionCommand(mysqlBytes);

            case COM_STMT_CLOSE:
                return new ComStmtCloseCommand(mysqlBytes);

            case COM_STMT_FETCH:
                return new ComStmtFetchCommand(mysqlBytes);

            case COM_STMT_RESET:
                return new ComStmtResetCommand(mysqlBytes);

            case COM_BINLOG_DUMP:
                return new ComBinlogDumpCommand(mysqlBytes);

            case COM_CHANGE_USER:
                return new ComChangeUserCommand(mysqlBytes);

            case COM_CONNECT_OUT:
                return new ComConnectOutCommand(mysqlBytes);

            case COM_DELAYED_INSERT:
                return new ComDelayedInsertCommand(mysqlBytes);

            case COM_REGISTER_SLAVE:
                return new ComRegisterSlaveCommand(mysqlBytes);

            default:
                Asserts.throwException("未找到协议对应的请求类型");
                return null;
        }

    }

}
