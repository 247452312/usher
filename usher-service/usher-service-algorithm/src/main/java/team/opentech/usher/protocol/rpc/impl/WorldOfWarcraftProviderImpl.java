package team.opentech.usher.protocol.rpc.impl;

import team.opentech.usher.protocol.rpc.WorldOfWarcraftProvider;
import team.opentech.usher.rpc.annotation.RpcService;
import team.opentech.usher.service.WorldOfWarcraftService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2022年10月26日 09时30分
 */
@RpcService
public class WorldOfWarcraftProviderImpl implements WorldOfWarcraftProvider {

    @Autowired
    private WorldOfWarcraftService service;

}