package team.opentech.usher.protocol.task;

import team.opentech.usher.assembler.JobAssembler;
import team.opentech.usher.dao.JobDao;
import team.opentech.usher.pojo.DO.JobDO;
import team.opentech.usher.pojo.DTO.JobDTO;
import team.opentech.usher.thread.ThreadPoolExecutorUtil;
import team.opentech.usher.util.LogUtil;
import team.opentech.usher.util.ScheduledManager;
import team.opentech.usher.util.SpringUtil;
import java.util.concurrent.ThreadPoolExecutor;
import org.quartz.JobExecutionContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年06月26日 08时26分
 */
@Async
public class ExecutionJob extends QuartzJobBean implements BaseTask<JobExecutionContext, Object> {

    /**
     * 执行定时任务线程池
     */
    private static final ThreadPoolExecutor EXECUTOR = ThreadPoolExecutorUtil.getPoll();

    @Override
    public Object executeTask(JobExecutionContext jobExecutionContext) {
        JobDTO quartzJob = (JobDTO) jobExecutionContext.getMergedJobDataMap().get(JobConfig.JOB_KEY);
        // 获取spring bean
        JobDao dao = SpringUtil.getBean(JobDao.class);
        JobAssembler jobAssembler = SpringUtil.getBean(JobAssembler.class);
        ScheduledManager manager = SpringUtil.getBean(ScheduledManager.class);
        try {
            // 执行任务
            LogUtil.info(this, "任务准备执行，任务名称：" + quartzJob.getInterfaceName() + "");
            JobRunnable task = new JobRunnable(quartzJob.getInterfaceName(), quartzJob.getMethodName(),
                                               quartzJob.getParams(), quartzJob.getParamType(), quartzJob.getUser());
            EXECUTOR.submit(task);
            // 任务状态
            LogUtil.info(this, "任务执行完毕，任务名称：" + quartzJob.getName());
        } catch (Exception e) {
            LogUtil.error(this, e);
            quartzJob.setPause(Boolean.FALSE);
            //更新状态
            JobDO dO = jobAssembler.toDo(quartzJob);
            dO.preUpdate();
            dao.updateById(dO);
            manager.deleteJob(dO);
        }
        return null;
    }

    /**
     * 执行定时任务
     *
     * @param jobExecutionContext
     */
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) {
        executeTask(jobExecutionContext);
    }
}
