package team.opentech.usher.protocol.rpc.impl;

import team.opentech.usher.pojo.DTO.PushPageMsgDTO;
import team.opentech.usher.protocol.rpc.PushPageMsgProvider;
import team.opentech.usher.protocol.rpc.base.BaseDefaultProvider;
import team.opentech.usher.rpc.annotation.RpcService;
import team.opentech.usher.service.BaseDoService;
import team.opentech.usher.service.PushPageMsgService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 推送日志信息表(PushPageMsg)表 RPC对外访问实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年09月02日 19时47分10秒
 */
@RpcService
public class PushPageMsgProviderImpl extends BaseDefaultProvider<PushPageMsgDTO> implements PushPageMsgProvider {


    @Autowired
    private PushPageMsgService service;


    @Override
    protected BaseDoService<PushPageMsgDTO> getService() {
        return service;
    }

}

