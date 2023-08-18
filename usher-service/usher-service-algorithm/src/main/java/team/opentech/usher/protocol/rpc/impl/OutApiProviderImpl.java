package team.opentech.usher.protocol.rpc.impl;

import team.opentech.usher.pojo.DTO.OutApiDTO;
import team.opentech.usher.protocol.rpc.OutApiProvider;
import team.opentech.usher.protocol.rpc.base.BaseDefaultProvider;
import team.opentech.usher.rpc.annotation.RpcService;
import team.opentech.usher.service.BaseDoService;
import team.opentech.usher.service.OutApiService;
import org.springframework.beans.factory.annotation.Autowired;

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

