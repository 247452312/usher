package team.opentech.usher.repository.impl;

import team.opentech.usher.annotation.Repository;
import team.opentech.usher.assembler.JobAssembler;
import team.opentech.usher.dao.JobDao;
import team.opentech.usher.pojo.DO.JobDO;
import team.opentech.usher.pojo.DTO.JobDTO;
import team.opentech.usher.pojo.entity.Job;
import team.opentech.usher.repository.JobRepository;
import team.opentech.usher.repository.base.AbstractRepository;


/**
 * 定时任务表(Job)表 仓库实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年09月02日 19时46分59秒
 */
@Repository
public class JobRepositoryImpl extends AbstractRepository<Job, JobDO, JobDao, JobDTO, JobAssembler> implements JobRepository {

    protected JobRepositoryImpl(JobAssembler convert, JobDao dao) {
        super(convert, dao);
    }


}
