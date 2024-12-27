package top.uhyils.usher.pojo.entity;

import top.uhyils.usher.annotation.Default;
import top.uhyils.usher.pojo.DO.JobDO;
import top.uhyils.usher.pojo.DTO.UserDTO;
import top.uhyils.usher.pojo.entity.base.AbstractDoEntity;
import top.uhyils.usher.repository.JobRepository;
import top.uhyils.usher.util.Asserts;
import top.uhyils.usher.util.ScheduledManager;
import top.uhyils.usher.util.SpringUtil;

/**
 * 定时任务表(Job)表 数据库实体类
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2021年09月02日 19时46分58秒
 */
public class Job extends AbstractDoEntity<JobDO> {

    private ScheduledManager scheduledManager;

    private UserDTO user;

    @Default
    public Job(JobDO data) {
        super(data);
    }

    public Job(Long id) {
        super(id, new JobDO());
    }

    public Job(Long id, JobRepository rep) {
        super(id, new JobDO());
        completion(rep);
    }

    public void fillUser(UserDTO user) {
        if (this.user != null) {
            return;
        }
        this.user = user;
    }

    public void addSelfToJob() {
        initScheduled();
        scheduledManager.addJob(toData().orElseThrow(Asserts::throwOptionalException));

    }

    public void delJob() {
        initScheduled();
        scheduledManager.deleteJob(toData().orElseThrow(Asserts::throwOptionalException));
    }

    public void pause() {
        initScheduled();
        JobDO jobDO = toData().orElseThrow(Asserts::throwOptionalException);
        jobDO.setPause(true);
        scheduledManager.pauseJob(jobDO);

    }

    public void start() {
        initScheduled();
        JobDO jobDO = toData().orElseThrow(Asserts::throwOptionalException);
        jobDO.setPause(false);
        scheduledManager.resumeJob(jobDO);
    }

    public void test() {
        initScheduled();
        JobDO jobDO = toData().orElseThrow(Asserts::throwOptionalException);
        scheduledManager.runJobNow(jobDO);
    }

    private void initScheduled() {
        if (this.scheduledManager != null) {
            return;
        }
        scheduledManager = SpringUtil.getBean(ScheduledManager.class);

    }
}
