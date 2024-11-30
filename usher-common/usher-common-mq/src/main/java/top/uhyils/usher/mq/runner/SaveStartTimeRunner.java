package top.uhyils.usher.mq.runner;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import top.uhyils.usher.mq.content.RocketMqContent;
import top.uhyils.usher.util.LogUtil;

/**
 * 初始化ip和程序启动时间
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年06月19日 09时58分
 */
@Component
@Order(1)
public class SaveStartTimeRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) {
        RocketMqContent.init();
        LogUtil.info(this, "MQ日志初始化成功");
    }
}
