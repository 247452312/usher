package team.opentech.usher.protocol.task;

import team.opentech.usher.pojo.cqe.command.BlankCommand;
import team.opentech.usher.service.TraceInfoService;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

/**
 * 并发数统计
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年09月18日 06时28分
 */
@Component
public class ConcurrentNumberTask implements BaseTask<Object, Object> {


    @Resource
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
