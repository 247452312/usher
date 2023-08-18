package team.opentech.usher.protocol.rpc.impl;

import team.opentech.usher.pojo.DTO.PushMsgDTO;
import team.opentech.usher.pojo.DTO.request.CronRequest;
import team.opentech.usher.pojo.DTO.request.PushMsgToSomeoneRequest;
import team.opentech.usher.protocol.rpc.PushMsgProvider;
import team.opentech.usher.protocol.rpc.base.BaseDefaultProvider;
import team.opentech.usher.rpc.annotation.RpcService;
import team.opentech.usher.service.BaseDoService;
import team.opentech.usher.service.PushMsgService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 推送日志表(PushMsg)表 RPC对外访问实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年09月02日 19时47分05秒
 */
@RpcService
public class PushMsgProviderImpl extends BaseDefaultProvider<PushMsgDTO> implements PushMsgProvider {


    @Autowired
    private PushMsgService service;

    @Override
    public Boolean push(CronRequest request) {
        return service.push(request);
    }

    @Override
    public Boolean pushMsgToSomeone(PushMsgToSomeoneRequest request) {
        return service.pushMsgToSomeone(request);
    }

    @Override
    protected BaseDoService<PushMsgDTO> getService() {
        return service;
    }
}

