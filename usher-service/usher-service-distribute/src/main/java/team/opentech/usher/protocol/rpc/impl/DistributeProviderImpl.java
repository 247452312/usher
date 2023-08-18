package team.opentech.usher.protocol.rpc.impl;

import team.opentech.usher.pojo.DTO.response.welcome.WelcomeResponse;
import team.opentech.usher.pojo.cqe.DefaultCQE;
import team.opentech.usher.protocol.rpc.DistributeProvider;
import team.opentech.usher.rpc.annotation.RpcService;
import team.opentech.usher.service.DistributeService;
import org.springframework.beans.factory.annotation.Autowired;


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
