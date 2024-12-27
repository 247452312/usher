package top.uhyils.usher.protocol.rpc.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import top.uhyils.usher.pojo.DTO.SoftwareDTO;
import top.uhyils.usher.pojo.DTO.request.GetRedisKeysQuery;
import top.uhyils.usher.pojo.DTO.request.RedisKeyAndValue;
import top.uhyils.usher.pojo.DTO.response.GetInfosResponse;
import top.uhyils.usher.pojo.DTO.response.OperateSoftwareResponse;
import top.uhyils.usher.pojo.cqe.command.ChangeCommand;
import top.uhyils.usher.pojo.cqe.command.IdCommand;
import top.uhyils.usher.pojo.cqe.command.IdsCommand;
import top.uhyils.usher.pojo.cqe.command.base.AddCommand;
import top.uhyils.usher.pojo.cqe.query.IdQuery;
import top.uhyils.usher.pojo.cqe.query.KeyQuery;
import top.uhyils.usher.protocol.rpc.SoftwareProvider;
import top.uhyils.usher.protocol.rpc.base.BaseDefaultProvider;
import top.uhyils.usher.rpc.annotation.RpcService;
import top.uhyils.usher.service.BaseDoService;
import top.uhyils.usher.service.SoftwareService;

/**
 * 中间件表(Software)表 RPC对外访问实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年09月02日 08时42分22秒
 */
@RpcService
public class SoftwareProviderImpl extends BaseDefaultProvider<SoftwareDTO> implements SoftwareProvider {


    @Autowired
    private SoftwareService service;

    @Override
    public SoftwareDTO reload(IdCommand request) {
        return service.reload(request);
    }

    @Override
    public OperateSoftwareResponse start(IdCommand request) {
        return service.start(request);
    }

    @Override
    public OperateSoftwareResponse stop(IdCommand request) {
        return service.stop(request);
    }

    @Override
    public Boolean deleteManyRedis(IdsCommand request) {
        return service.deleteManyRedis(request);
    }

    @Override
    public Boolean reloadManyRedis(IdsCommand request) {
        return service.reloadManyRedis(request);
    }

    @Override
    public Boolean startManyRedis(IdsCommand request) {
        return service.startManyRedis(request);
    }

    @Override
    public Boolean stopManyRedis(IdsCommand request) {
        return service.stopManyRedis(request);
    }

    @Override
    public List<String> getRedisKeys(GetRedisKeysQuery request) {
        return service.getRedisKeys(request);
    }

    @Override
    public Integer getRedisDb(IdQuery request) {
        return service.getRedisDb(request);
    }

    @Override
    public Integer addKey(AddCommand<RedisKeyAndValue> request) {
        return service.addKey(request);
    }

    @Override
    public Integer addKeyCover(AddCommand<RedisKeyAndValue> request) {
        return service.addKeyCover(request);
    }

    @Override
    public Integer updateKey(ChangeCommand<RedisKeyAndValue> request) {
        return service.updateKey(request);
    }

    @Override
    public String getValueByKey(KeyQuery request) {
        return service.getValueByKey(request);
    }

    @Override
    public Boolean deleteRedisByKey(ChangeCommand<RedisKeyAndValue> request) {
        return service.deleteRedisByKey(request);
    }

    @Override
    public List<GetInfosResponse> getInfos(IdQuery request) {
        return service.getInfos(request);
    }

    @Override
    protected BaseDoService<SoftwareDTO> getService() {
        return service;
    }
}

