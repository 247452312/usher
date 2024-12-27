package top.uhyils.usher.repository.impl;

import top.uhyils.usher.annotation.Repository;
import top.uhyils.usher.assembler.JobAssembler;
import top.uhyils.usher.dao.JobDao;
import top.uhyils.usher.pojo.DO.JobDO;
import top.uhyils.usher.pojo.DTO.JobDTO;
import top.uhyils.usher.pojo.entity.Job;
import top.uhyils.usher.repository.JobRepository;
import top.uhyils.usher.repository.base.AbstractRepository;


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
