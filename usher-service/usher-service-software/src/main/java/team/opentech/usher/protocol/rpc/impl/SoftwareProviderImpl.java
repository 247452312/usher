package team.opentech.usher.protocol.rpc.impl;

import team.opentech.usher.pojo.DTO.SoftwareDTO;
import team.opentech.usher.pojo.DTO.request.GetRedisKeysQuery;
import team.opentech.usher.pojo.DTO.request.RedisKeyAndValue;
import team.opentech.usher.pojo.DTO.response.GetInfosResponse;
import team.opentech.usher.pojo.DTO.response.OperateSoftwareResponse;
import team.opentech.usher.pojo.cqe.command.ChangeCommand;
import team.opentech.usher.pojo.cqe.command.IdCommand;
import team.opentech.usher.pojo.cqe.command.IdsCommand;
import team.opentech.usher.pojo.cqe.command.base.AddCommand;
import team.opentech.usher.pojo.cqe.query.IdQuery;
import team.opentech.usher.pojo.cqe.query.KeyQuery;
import team.opentech.usher.protocol.rpc.SoftwareProvider;
import team.opentech.usher.protocol.rpc.base.BaseDefaultProvider;
import team.opentech.usher.rpc.annotation.RpcService;
import team.opentech.usher.service.BaseDoService;
import team.opentech.usher.service.SoftwareService;
import java.util.List;
import javax.annotation.Resource;

/**
 * 中间件表(Software)表 RPC对外访问实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年09月02日 08时42分22秒
 */
@RpcService
public class SoftwareProviderImpl extends BaseDefaultProvider<SoftwareDTO> implements SoftwareProvider {


    @Resource
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

