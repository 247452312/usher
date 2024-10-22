package team.opentech.usher.service.impl;

import team.opentech.usher.annotation.ReadWriteMark;
import team.opentech.usher.assembler.JobAssembler;
import team.opentech.usher.context.UserInfoHelper;
import team.opentech.usher.pojo.DO.JobDO;
import team.opentech.usher.pojo.DTO.JobDTO;
import team.opentech.usher.pojo.cqe.query.IdQuery;
import team.opentech.usher.pojo.cqe.query.demo.Arg;
import team.opentech.usher.pojo.entity.Job;
import team.opentech.usher.repository.JobRepository;
import team.opentech.usher.service.JobService;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * 定时任务表(Job)表 内部服务实现类
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年09月02日 19时47分00秒
 */
@Service
@ReadWriteMark(tables = {"sys_job"})
public class JobServiceImpl extends AbstractDoService<JobDO, Job, JobDTO, JobRepository, JobAssembler> implements JobService {

    public JobServiceImpl(JobAssembler assembler, JobRepository repository) {
        super(assembler, repository);
    }

    @Override
    public Long add(JobDTO dto) {
        Job job = assem.toEntity(dto);
        rep.save(job);
        job.fillUser(UserInfoHelper.doGet());
        job.addSelfToJob();
        return 1L;
    }

    @Override
    public Integer update(JobDTO dto, List<Arg> args) {
        Job job = assem.toEntity(dto);
        rep.change(job, args);

        job.fillUser(UserInfoHelper.doGet());
        job.delJob();
        job.addSelfToJob();
        return 1;
    }

    @Override
    public Boolean pause(IdQuery request) {
        Job job = new Job(request.getId());
        job.pause();
        rep.save(job);
        return true;
    }

    @Override
    public Boolean start(IdQuery request) {
        Job job = new Job(request.getId());
        job.start();
        rep.save(job);
        return true;
    }

    @Override
    public Boolean test(IdQuery request) {
        Job job = new Job(request.getId());
        job.completion(rep);
        job.fillUser(request.getUser());
        job.test();
        return true;
    }
}
