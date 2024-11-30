package top.uhyils.usher.protocol.rpc.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import top.uhyils.usher.pojo.DTO.BlackListDTO;
import top.uhyils.usher.pojo.DTO.request.AddBlackIpRequest;
import top.uhyils.usher.pojo.DTO.request.GetLogIntervalByIpQuery;
import top.uhyils.usher.pojo.cqe.DefaultCQE;
import top.uhyils.usher.protocol.rpc.BlackListProvider;
import top.uhyils.usher.protocol.rpc.base.BaseDefaultProvider;
import top.uhyils.usher.rpc.annotation.RpcService;
import top.uhyils.usher.service.BaseDoService;
import top.uhyils.usher.service.BlackListService;

/**
 * 黑名单(BlackList)表 RPC对外访问实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月29日 16时58分52秒
 */
@RpcService
public class BlackListProviderImpl extends BaseDefaultProvider<BlackListDTO> implements BlackListProvider {


    @Autowired
    private BlackListService service;

    @Override
    public Boolean getLogIntervalByIp(GetLogIntervalByIpQuery request) {
        return service.getLogIntervalByIp(request);
    }

    @Override
    public List<String> getAllIpBlackList(DefaultCQE request) {
        return service.getAllIpBlackList(request);
    }

    @Override
    public Boolean addBlackIp(AddBlackIpRequest request) {
        return service.addBlackIp(request);
    }

    @Override
    protected BaseDoService<BlackListDTO> getService() {
        return service;
    }
}

