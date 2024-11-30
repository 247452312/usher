package top.uhyils.usher.protocol.register;


import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import top.uhyils.usher.pojo.cqe.event.base.BaseEvent;
import top.uhyils.usher.protocol.register.base.Register;


/**
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年09月22日 14时39分
 */
@top.uhyils.usher.annotation.Register
public class LogRegister implements Register {

    @Value("${event.log.info:true}")
    private Boolean info;

    @Override
    public List<Class<? extends BaseEvent>> targetEvent() {
        return Arrays.asList(BaseEvent.class);
    }

    @Override
    public void onEvent(BaseEvent event) {
        //        if (StringUtils.containsIgnoreCase(Thread.currentThread().getName(), LogContent.TRACE_INFO)) {
        //            return;
        //        }
        //        if (info) {
        //            LogUtil.info(MessageFormat.format("事件:{0}发布", event.getClass()));
        //        } else {
        //            if (LogUtil.isDebugEnabled(this)) {
        //                LogUtil.debug(() -> MessageFormat.format("事件:{0}发布", event.getClass()));
        //            }
        //        }
    }

}
