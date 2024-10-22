package team.opentech.usher.protocol.mq;


import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Envelope;
import team.opentech.usher.UsherExecutorWrapper;
import team.opentech.usher.annotation.MyMq;
import team.opentech.usher.bus.BusInterface;
import team.opentech.usher.enums.LogDetailTypeEnum;
import team.opentech.usher.mq.util.LogInfoSendMqUtil;
import team.opentech.usher.pojo.DTO.TraceDetailDTO;
import team.opentech.usher.pojo.DTO.TraceInfoDTO;
import team.opentech.usher.pojo.DTO.TraceLogDTO;
import team.opentech.usher.pojo.cqe.event.parent.LogReceiveParentEvent;
import team.opentech.usher.pojo.trace.DetailTraceDeal;
import team.opentech.usher.pojo.trace.LinkTraceDeal;
import team.opentech.usher.pojo.trace.LogTraceDeal;
import team.opentech.usher.protocol.mq.base.AbstractMqConsumer;
import team.opentech.usher.service.TraceDetailService;
import team.opentech.usher.service.TraceInfoService;
import team.opentech.usher.service.TraceLogService;
import team.opentech.usher.util.Asserts;
import team.opentech.usher.util.LogUtil;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.context.ApplicationContext;


/**
 * 监听日志消息
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年07月29日 14时14分
 */
@MyMq(topic = LogInfoSendMqUtil.QUEUE_NAME, tags = {LogInfoSendMqUtil.QUEUE_NAME}, group = "监听日志消息")
public class RabbitLogInfoConsumer extends AbstractMqConsumer {

    private static final String THREAD_NAME = "thread_";

    /**
     * 包含这个字段的日志不发送MQ
     */
    private static final String TRACE_INFO = "sys_trace";

    private final Executor executor;

    private final TraceDetailService traceDetailService;

    private final TraceInfoService traceInfoService;

    private final TraceLogService traceLogService;

    private final BusInterface bus;

    /**
     * @param channel
     * @param applicationContext
     */
    public RabbitLogInfoConsumer(Channel channel, ApplicationContext applicationContext) {
        super(channel);
        this.bus = applicationContext.getBean(BusInterface.class);
        int process = Runtime.getRuntime().availableProcessors();
        executor = UsherExecutorWrapper.createByThreadPoolExecutor(new ThreadPoolExecutor(process, process * 2, 3000L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(100), new LogDealThreadFactory(), new CallerRunsPolicy()));
        traceDetailService = applicationContext.getBean(TraceDetailService.class);
        traceInfoService = applicationContext.getBean(TraceInfoService.class);
        traceLogService = applicationContext.getBean(TraceLogService.class);
    }

    /**
     * 注: 此处不打印日志,如果打印日志,会造成递归效果.快速将磁盘吃没 具体方法为,线程名称中带有:
     * {@link RabbitLogInfoConsumer#TRACE_INFO}
     * 请不要在此方法创建的线程中再次创建其他线程
     */
    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) {
        executor.execute(() -> {
            String text = new String(body, StandardCharsets.UTF_8);
            try {
                text = text.substring(1, text.length() - 1);
                LogDetailTypeEnum parse = LogDetailTypeEnum.parse(text.charAt(0)).orElseThrow(() -> Asserts.makeException("日志标志对应类型不存在"));
                switch (parse) {
                    case DETAIL:
                        TraceDetailDTO traceDetailDTO = new DetailTraceDeal().doDeal(text);
                        if (traceDetailDTO == null) {
                            break;
                        }
                        bus.commitAndPush(new LogReceiveParentEvent(traceDetailDTO));
                        traceDetailService.add(traceDetailDTO);
                        break;
                    case LOG:
                        TraceLogDTO traceDeal = new LogTraceDeal().doDeal(text);
                        if (traceDeal == null) {
                            break;
                        }
                        traceLogService.add(traceDeal);
                        break;
                    case LINK:
                        TraceInfoDTO traceInfoDTO = new LinkTraceDeal().doDeal(text);
                        if (traceInfoDTO == null) {
                            break;
                        }
                        traceInfoService.add(traceInfoDTO);
                        break;
                    default:
                        LogUtil.error("前缀错误" + text.charAt(0));
                }
            } catch (Exception e) {
                LogUtil.error(e, text);
            }
        });


    }

    private static class LogDealThreadFactory implements ThreadFactory {

        private static final AtomicInteger ATOMIC_INTEGER = new AtomicInteger(0);

        @Override
        public Thread newThread(Runnable runnable) {
            return new Thread(runnable, TRACE_INFO + "_" + THREAD_NAME + ATOMIC_INTEGER.getAndAdd(1));
        }
    }


}
