package team.opentech.usher.handler.rabbit;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import team.opentech.usher.dao.OrderApiDao;
import team.opentech.usher.handler.InitApiHandler;
import team.opentech.usher.handler.RunApiHandler;
import team.opentech.usher.handler.SaveApiHandler;
import team.opentech.usher.handler.TransApiHandler;
import team.opentech.usher.pojo.DO.OrderApiDO;
import team.opentech.usher.pojo.DO.OrderNodeDO;
import team.opentech.usher.pojo.temp.InitApiRequestTemporary;
import team.opentech.usher.pojo.temp.InitToRunApiTemporary;
import team.opentech.usher.pojo.temp.RunToSaveApiTemporary;
import team.opentech.usher.pojo.temp.SaveToTransApiTemporary;
import team.opentech.usher.util.ObjectByteUtil;
import java.io.IOException;
import org.springframework.context.ApplicationContext;

/**
 * 工单自动节点处理方式
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年11月24日 07时27分
 */
public class OrderAutoDealConsumer extends DefaultConsumer {


    private ApplicationContext applicationContext;

    /*以下两个 初始化方法在构造方法中*/

    private OrderApiDao orderApiDao;

    /**
     * Constructs a new instance and records its association to the passed-in channel.
     *
     * @param channel the channel to which this consumer is attached
     */
    public OrderAutoDealConsumer(Channel channel, ApplicationContext applicationContext) {
        super(channel);
        this.applicationContext = applicationContext;
        orderApiDao = applicationContext.getBean(OrderApiDao.class);

    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        InitApiRequestTemporary initApiRequestTemporary = ObjectByteUtil.toObject(body);
        OrderNodeDO orderNodeEntity = initApiRequestTemporary.getOrderNode();

        // 初始化方法
        OrderApiDO initApiEntity = orderApiDao.selectById(orderNodeEntity.getInitApiId());
        InitApiHandler initHandler = applicationContext.getBean(initApiEntity.getBeanName(), InitApiHandler.class);
        InitToRunApiTemporary init = initHandler.init(initApiRequestTemporary);

        // 运行方法
        OrderApiDO runApiEntity = orderApiDao.selectById(orderNodeEntity.getRunApiId());
        RunApiHandler runHandler = applicationContext.getBean(runApiEntity.getBeanName(), RunApiHandler.class);
        RunToSaveApiTemporary run = runHandler.run(init);

        // 保存方法
        OrderApiDO saveApiEntity = orderApiDao.selectById(orderNodeEntity.getSaveApiId());
        SaveApiHandler saveHandler = applicationContext.getBean(saveApiEntity.getBeanName(), SaveApiHandler.class);
        SaveToTransApiTemporary save = saveHandler.save(run);

        // 运行方法
        OrderApiDO transApiEntity = orderApiDao.selectById(orderNodeEntity.getTransApiId());
        TransApiHandler transHandler = applicationContext.getBean(transApiEntity.getBeanName(), TransApiHandler.class);
        transHandler.trans(save);

    }
}
