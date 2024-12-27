package top.uhyils.usher.protocol.rpc.impl;

import org.springframework.beans.factory.annotation.Autowired;
import top.uhyils.usher.pojo.DTO.ParamDTO;
import top.uhyils.usher.pojo.cqe.FlushAllSysEvent;
import top.uhyils.usher.protocol.rpc.ParamProvider;
import top.uhyils.usher.protocol.rpc.base.BaseDefaultProvider;
import top.uhyils.usher.rpc.annotation.RpcService;
import top.uhyils.usher.service.BaseDoService;
import top.uhyils.usher.service.ParamService;

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

