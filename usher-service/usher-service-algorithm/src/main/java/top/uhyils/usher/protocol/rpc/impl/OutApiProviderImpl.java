package top.uhyils.usher.protocol.rpc.impl;

import org.springframework.beans.factory.annotation.Autowired;
import top.uhyils.usher.pojo.DTO.OutApiDTO;
import top.uhyils.usher.protocol.rpc.OutApiProvider;
import top.uhyils.usher.protocol.rpc.base.BaseDefaultProvider;
import top.uhyils.usher.rpc.annotation.RpcService;
import top.uhyils.usher.service.BaseDoService;
import top.uhyils.usher.service.OutApiService;

/**
 * 开放api(OutApi)表 RPC对外访问实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年09月09日 20时58分12秒
 */
@RpcService
public class OutApiProviderImpl extends BaseDefaultProvider<OutApiDTO> implements OutApiProvider {


    @Autowired
    private OutApiService service;


    @Override
    protected BaseDoService<OutApiDTO> getService() {
        return service;
    }

}

