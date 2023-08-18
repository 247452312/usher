package team.opentech.usher.protocol.rpc.impl;

import team.opentech.usher.pojo.DTO.ParamDTO;
import team.opentech.usher.pojo.cqe.FlushAllSysEvent;
import team.opentech.usher.protocol.rpc.ParamProvider;
import team.opentech.usher.protocol.rpc.base.BaseDefaultProvider;
import team.opentech.usher.rpc.annotation.RpcService;
import team.opentech.usher.service.BaseDoService;
import team.opentech.usher.service.ParamService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 系统参数表(Param)表 RPC对外访问实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2022年06月17日 15时53分
 */
@RpcService
public class ParamProviderImpl extends BaseDefaultProvider<ParamDTO> implements ParamProvider {


    @Autowired
    private ParamService service;

    @Override
    public Boolean flushAllSys(FlushAllSysEvent event) {
        return service.flushAllGlobal(event);
    }


    @Override
    protected BaseDoService<ParamDTO> getService() {
        return service;
    }

}

