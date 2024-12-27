package top.uhyils.usher.protocol.rpc.impl;

import org.springframework.beans.factory.annotation.Autowired;
import top.uhyils.usher.pojo.DTO.PushPageMsgDTO;
import top.uhyils.usher.protocol.rpc.PushPageMsgProvider;
import top.uhyils.usher.protocol.rpc.base.BaseDefaultProvider;
import top.uhyils.usher.rpc.annotation.RpcService;
import top.uhyils.usher.service.BaseDoService;
import top.uhyils.usher.service.PushPageMsgService;

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

