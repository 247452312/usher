package team.opentech.usher.elegant;

import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import team.opentech.usher.common.content.UsherDecentralizedContent;
import team.opentech.usher.common.context.UsherDecentralizedContext;
import team.opentech.usher.common.netty.DecentralizedConsumer;
import team.opentech.usher.common.netty.DecentralizedServerImpl;
import team.opentech.usher.common.netty.enums.DecentralizedRequestTypeEnum;
import team.opentech.usher.common.netty.pojo.entity.DecentralizedProtocol;
import team.opentech.usher.core.DecentralizedManager;
import team.opentech.usher.util.ByteUtil;
import team.opentech.usher.util.IdUtil;
import team.opentech.usher.util.LogUtil;
import team.opentech.usher.util.RunnableUtil;
import team.opentech.usher.util.SpringUtil;

/**
 * 去中心化集群工具接入优雅上下线
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年11月07日 10时33分
 */
@Component
public class DecentralizedElegantHandler extends AbstractElegantHandler {


    @Resource
    private DecentralizedManager service;

    @Override
    public Boolean isOnline() {
        UsherDecentralizedContext instance = UsherDecentralizedContext.getInstance();
        return instance.getServer().isOnline();
    }

    @Override
    public void close() {
        UsherDecentralizedContext.getInstance().close();
    }

    @Override
    public String name() {
        return "去中心化集群";
    }

    @Override
    public void allowToPublish() {
        super.allowToPublish();
        try {
            IdUtil idUtil = SpringUtil.getBean(IdUtil.class);
            /*1.启动server,监听请求*/
            UsherDecentralizedContext instance = UsherDecentralizedContext.getInstance();
            if (instance.getServer() == null) {
                instance.setServer(new DecentralizedServerImpl(service));
            }
            instance.getServer().start();
            /*2.启动请求发送器*/
            DecentralizedConsumer decentralizedConsumer = instance.makeOrGetUdpConsumer();
            /*3.发送上线广播*/
            String host = instance.host();
            Integer port = instance.serverPort();
            String onlineMsg = String.format("%s:%d", host, port);
            DecentralizedProtocol decentralizedProtocol = DecentralizedProtocol.build(ByteUtil.subByte(instance.clusterTypeCode().getBytes(UsherDecentralizedContent.DEFAULT_CHARSET), 4), idUtil.newId(), DecentralizedRequestTypeEnum.ONLINE_BROADCAST, onlineMsg.getBytes(UsherDecentralizedContent.DEFAULT_CHARSET));
            long unique = RunnableUtil.run(() -> {
                try {
                    return decentralizedConsumer.send(decentralizedProtocol);
                } catch (InterruptedException e) {
                    LogUtil.error(this, e);
                }
                return false;
            }, 3, 30L);
            instance.putUnique(DecentralizedRequestTypeEnum.ONLINE_BROADCAST, unique);
        } catch (InterruptedException e) {
            LogUtil.error(this, e);
        }
    }

    @Override
    public void notAllowToPublish() {
        super.notAllowToPublish();
    }

    @Override
    protected void doShutdown() {
        try {
            UsherDecentralizedContext.getInstance().shutdown();
        } catch (InterruptedException e) {
            LogUtil.error(this, e);
        }
    }

    @Override
    protected Boolean canPublish() {
        return super.canPublish();
    }

    @Override
    protected void newRequest() {
        super.newRequest();
    }

    @Override
    protected void requestOver() {
        super.requestOver();
    }
}
