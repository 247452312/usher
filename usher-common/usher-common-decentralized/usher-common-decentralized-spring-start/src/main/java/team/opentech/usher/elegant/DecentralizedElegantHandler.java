package team.opentech.usher.elegant;

import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import team.opentech.usher.DecentralizedStarter;
import team.opentech.usher.common.netty.DecentralizedUdpConsumer;
import team.opentech.usher.util.LogUtil;

/**
 * 去中心化集群工具接入优雅上下线
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年11月07日 10时33分
 */
@Component
public class DecentralizedElegantHandler extends AbstractElegantHandler {

    @Resource
    private DecentralizedStarter starter;

    @Override
    public Boolean isOnline() {
        return starter.isOnline();
    }

    @Override
    public void close() {
        starter.close();
    }

    @Override
    public String name() {
        return "去中心化集群";
    }

    @Override
    public void allowToPublish() {
        super.allowToPublish();
        try {
            /*1.启动server,监听请求*/
            starter.startServer();
            /*2.启动请求发送器*/
            DecentralizedUdpConsumer decentralizedConsumer = starter.makeOrGetUdpConsumer();
            /*3.发送上线广播*/
            decentralizedConsumer.sendOnline();
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
            starter.shutdown();
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
