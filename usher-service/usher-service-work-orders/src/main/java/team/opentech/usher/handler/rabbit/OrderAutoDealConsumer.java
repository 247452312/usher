package team.opentech.usher.handler.rabbit;

import org.springframework.context.ApplicationContext;
import team.opentech.usher.content.OrderContent;
import team.opentech.usher.dao.OrderApiDao;
import team.opentech.usher.handler.InitApiHandler;
import team.opentech.usher.handler.RunApiHandler;
import team.opentech.usher.handler.SaveApiHandler;
import team.opentech.usher.handler.TransApiHandler;
import team.opentech.usher.mq.MQMessage;
import team.opentech.usher.mq.consumer.MQTTConsumer;
import team.opentech.usher.pojo.DO.OrderApiDO;
import team.opentech.usher.pojo.DO.OrderNodeDO;
import team.opentech.usher.pojo.temp.InitApiRequestTemporary;
import team.opentech.usher.pojo.temp.InitToRunApiTemporary;
import team.opentech.usher.pojo.temp.RunToSaveApiTemporary;
import team.opentech.usher.pojo.temp.SaveToTransApiTemporary;

/**
 * 工单自动节点处理方式
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年11月24日 07时27分
 */
public class OrderAutoDealConsumer implements MQTTConsumer {


    private ApplicationContext applicationContext;

    /*以下两个 初始化方法在构造方法中*/

    private OrderApiDao orderApiDao;

    public OrderAutoDealConsumer(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        orderApiDao = applicationContext.getBean(OrderApiDao.class);

    }


    @Override
    public void receive(MQMessage message) {
        InitApiRequestTemporary initApiRequestTemporary = message.body(InitApiRequestTemporary.class);
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

    @Override
    public String topic() {
        return OrderContent.ORDER_TOPIC;
    }
}
