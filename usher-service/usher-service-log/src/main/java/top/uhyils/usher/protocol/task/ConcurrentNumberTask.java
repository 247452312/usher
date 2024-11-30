package top.uhyils.usher.protocol.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.uhyils.usher.pojo.cqe.command.BlankCommand;
import top.uhyils.usher.service.TraceInfoService;

/**
 * 并发数统计
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年09月18日 06时28分
 */
@Component
public class ConcurrentNumberTask implements BaseTask<Object, Object> {


    @Autowired
    private TraceInfoService service;


    public void demoSchedule() {
        service.monitorConcurrentNumber(new BlankCommand());
    }

    /**
     * 测试环境暂时关闭 todo
     */
    //    @Scheduled(cron = "*/2 * * * * ?")
    @Override
    public Object executeTask(Object o) {
        service.monitorConcurrentNumber(new BlankCommand());
        return true;
    }
}
