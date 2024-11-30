package top.uhyils.usher.handler.rocket;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import top.uhyils.usher.annotation.UsherMq;
import top.uhyils.usher.content.OrderContent;
import top.uhyils.usher.dao.OrderApiDao;
import top.uhyils.usher.handler.InitApiHandler;
import top.uhyils.usher.handler.RunApiHandler;
import top.uhyils.usher.handler.SaveApiHandler;
import top.uhyils.usher.handler.TransApiHandler;
import top.uhyils.usher.mq.core.AbstractRocketMqConsumer;
import top.uhyils.usher.mq.core.RocketMqMessageResEnum;
import top.uhyils.usher.pojo.DO.OrderApiDO;
import top.uhyils.usher.pojo.DO.OrderNodeDO;
import top.uhyils.usher.pojo.temp.InitApiRequestTemporary;
import top.uhyils.usher.pojo.temp.InitToRunApiTemporary;
import top.uhyils.usher.pojo.temp.RunToSaveApiTemporary;
import top.uhyils.usher.pojo.temp.SaveToTransApiTemporary;
import top.uhyils.usher.util.LogUtil;
import top.uhyils.usher.util.ObjectByteUtil;

/**
 * 工单自动节点处理方式
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年11月24日 07时27分
 */
@UsherMq(topic = OrderContent.ORDER_TOPIC, tags = {OrderContent.ORDER_AUTO_NODE_SEND_TAG}, group = OrderContent.ORDER_GROUP, isOrder = false)
public class OrderAutoDealConsumer extends AbstractRocketMqConsumer {


    private ApplicationContext applicationContext;

    /*以下两个 初始化方法在构造方法中*/

    private OrderApiDao orderApiDao;

    /**
     *
     */
    public OrderAutoDealConsumer(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        orderApiDao = applicationContext.getBean(OrderApiDao.class);

    }


    @Override
    public RocketMqMessageResEnum doOnMessage(byte[] bytes) {
        try {
            InitApiRequestTemporary initApiRequestTemporary = ObjectByteUtil.toObject(bytes);
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
            return RocketMqMessageResEnum.SUCCESS;
        } catch (BeansException e) {
            LogUtil.error(this, e);
            throw e;
        }
    }
}
