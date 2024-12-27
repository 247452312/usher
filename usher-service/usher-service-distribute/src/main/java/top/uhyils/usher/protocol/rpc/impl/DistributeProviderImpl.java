package top.uhyils.usher.protocol.rpc.impl;

import org.springframework.beans.factory.annotation.Autowired;
import top.uhyils.usher.pojo.DTO.response.welcome.WelcomeResponse;
import top.uhyils.usher.pojo.cqe.DefaultCQE;
import top.uhyils.usher.protocol.rpc.DistributeProvider;
import top.uhyils.usher.rpc.annotation.RpcService;
import top.uhyils.usher.service.DistributeService;


/**
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年09月10日 08时23分
 */
@RpcService
public class DistributeProviderImpl implements DistributeProvider {

    @Autowired
    private DistributeService service;


    @Override
    public WelcomeResponse getWelcomeData(DefaultCQE request) {
        return service.getWelcomeData();
    }
}
