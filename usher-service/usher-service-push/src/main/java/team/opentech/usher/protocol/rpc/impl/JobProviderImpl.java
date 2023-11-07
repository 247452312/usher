package team.opentech.usher.protocol.rpc.impl;

import team.opentech.usher.pojo.DTO.JobDTO;
import team.opentech.usher.pojo.cqe.query.IdQuery;
import team.opentech.usher.protocol.rpc.JobProvider;
import team.opentech.usher.protocol.rpc.base.BaseDefaultProvider;
import team.opentech.usher.rpc.annotation.RpcService;
import team.opentech.usher.service.BaseDoService;
import team.opentech.usher.service.JobService;
import javax.annotation.Resource;

/**
 * 定时任务表(Job)表 RPC对外访问实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年09月02日 19时47分00秒
 */
@RpcService
public class JobProviderImpl extends BaseDefaultProvider<JobDTO> implements JobProvider {


    @Resource
    private JobService service;

    @Override
    public Boolean pause(IdQuery request) {
        return service.pause(request);
    }

    @Override
    public Boolean start(IdQuery request) {
        return service.start(request);
    }

    @Override
    public Boolean test(IdQuery request) {
        return service.test(request);
    }

    @Override
    protected BaseDoService<JobDTO> getService() {
        return service;
    }
}

