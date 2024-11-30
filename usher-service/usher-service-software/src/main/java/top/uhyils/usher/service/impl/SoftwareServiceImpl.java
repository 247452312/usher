package top.uhyils.usher.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.uhyils.usher.annotation.ReadWriteMark;
import top.uhyils.usher.assembler.SoftwareAssembler;
import top.uhyils.usher.enums.ReadWriteTypeEnum;
import top.uhyils.usher.enums.RedisAddEnum;
import top.uhyils.usher.enums.SoftwareStatusEnum;
import top.uhyils.usher.pojo.DO.SoftwareDO;
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
import top.uhyils.usher.pojo.entity.Software;
import top.uhyils.usher.pojo.entity.software.RedisSoftware;
import top.uhyils.usher.pojo.entity.software.RedisSoftwareInterface;
import top.uhyils.usher.repository.ServerRepository;
import top.uhyils.usher.repository.SoftwareRepository;
import top.uhyils.usher.service.SoftwareService;

/**
 * 中间件表(Software)表 内部服务实现类
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年09月02日 08时42分22秒
 */
@Service
@ReadWriteMark(tables = {"sys_software"})
public class SoftwareServiceImpl extends AbstractDoService<SoftwareDO, Software, SoftwareDTO, SoftwareRepository, SoftwareAssembler> implements SoftwareService {

    @Autowired
    private ServerRepository serverRepository;

    public SoftwareServiceImpl(SoftwareAssembler assembler, SoftwareRepository repository) {
        super(assembler, repository);
    }

    @Override
    @ReadWriteMark(type = ReadWriteTypeEnum.WRITE)
    public Long add(SoftwareDTO dto) {
        Software software = assem.toEntity(dto);
        software.findServer(serverRepository);
        software.link();
        // 获取基础信息,例如version
        software.initBaseInfo();
        software.close();
        return software.saveSelf(rep);
    }

    @Override
    public SoftwareDTO reload(IdCommand request) {

        Software software = new Software(request.getId());
        software.completion(rep);
        software.findServer(serverRepository);
        software.link();
        software.initBaseInfo();
        software.close();
        software.saveSelf(rep);
        return assem.toDTO(software);
    }

    @Override
    public OperateSoftwareResponse start(IdCommand request) {
        Software software = new Software(request.getId());
        software.completion(rep);
        software.findServer(serverRepository);

        software.start();

        OperateSoftwareResponse operateSoftwareResponse = new OperateSoftwareResponse();
        operateSoftwareResponse.setStatus(SoftwareStatusEnum.RUNNING.getStatus());
        operateSoftwareResponse.setMsg("redis docker启动成功");
        return operateSoftwareResponse;
    }

    @Override
    public OperateSoftwareResponse stop(IdCommand request) {

        Software software = new Software(request.getId());
        software.completion(rep);
        software.findServer(serverRepository);

        software.stop();
        OperateSoftwareResponse operateSoftwareResponse = new OperateSoftwareResponse();
        operateSoftwareResponse.setStatus(SoftwareStatusEnum.STOP.getStatus());
        operateSoftwareResponse.setMsg("docker停止成功");
        return operateSoftwareResponse;
    }

    @Override
    public Boolean deleteManyRedis(IdsCommand request) {
        Long[] identifiers = request.getIds().stream().toArray(Long[]::new);
        rep.remove(identifiers);
        return true;
    }

    @Override
    public Boolean reloadManyRedis(IdsCommand request) {

        List<Software> softwares = request.getIds().stream()
                                          .map(Software::new)
                                          .peek(t -> t.completion(rep))
                                          .peek(t -> t.findServer(serverRepository))
                                          .peek(Software::link)
                                          .peek(Software::initBaseInfo)
                                          .peek(t -> t.saveSelf(rep))
                                          .collect(Collectors.toList());
        for (Software software : softwares) {
            software.close();
        }
        return true;
    }

    @Override
    public Boolean startManyRedis(IdsCommand request) {
        List<Software> software = request.getIds().stream()
                                         .map(t -> new Software(t, rep))
                                         .peek(t -> t.findServer(serverRepository))
                                         .peek(Software::start)
                                         .collect(Collectors.toList());

        return true;
    }

    @Override
    public Boolean stopManyRedis(IdsCommand request) {
        List<Software> software = request.getIds().stream()
                                         .map(t -> new Software(t, rep))
                                         .peek(t -> t.findServer(serverRepository))
                                         .peek(Software::stop)
                                         .collect(Collectors.toList());
        return true;
    }

    @Override
    public List<String> getRedisKeys(GetRedisKeysQuery request) {
        RedisSoftwareInterface redis = new RedisSoftware(request.getRedisId(), rep);
        redis.select(request.getDb());
        List<String> keys = redis.keys();
        redis.close();
        return keys;
    }

    @Override
    public Integer getRedisDb(IdQuery request) {
        RedisSoftwareInterface redis = new RedisSoftware(request.getId(), rep);
        redis.findServer(serverRepository);
        Integer dbCount = redis.findRedisDb();
        redis.close();
        return dbCount;
    }

    @Override
    public Integer addKey(AddCommand<RedisKeyAndValue> request) {
        RedisKeyAndValue dto = request.getDto();
        RedisSoftwareInterface redis = new RedisSoftware(dto.getRedisId(), rep);
        redis.select(dto.getDb());
        // compare and swap
        redis.cas(dto.getKey(), dto.getValue());
        redis.close();
        return RedisAddEnum.SUCCESS.getType();
    }

    @Override
    public Integer addKeyCover(AddCommand<RedisKeyAndValue> request) {
        RedisKeyAndValue dto = request.getDto();
        RedisSoftwareInterface redis = new RedisSoftware(dto.getRedisId(), rep);
        redis.select(dto.getDb());
        redis.forcePut(dto.getKey(), dto.getValue());
        redis.close();
        return RedisAddEnum.SUCCESS.getType();
    }

    @Override
    public Integer updateKey(ChangeCommand<RedisKeyAndValue> request) {
        RedisKeyAndValue dto = request.getDto();
        RedisSoftwareInterface redis = new RedisSoftware(dto.getRedisId(), rep);
        redis.select(dto.getDb());
        redis.update(dto.getKey(), dto.getValue());
        redis.close();
        return RedisAddEnum.SUCCESS.getType();

    }

    @Override
    public String getValueByKey(KeyQuery request) {
        RedisSoftwareInterface redis = new RedisSoftware(request.getRedisId(), rep);
        String value = redis.get(request.getKey());
        redis.close();
        return value;
    }

    @Override
    public Boolean deleteRedisByKey(ChangeCommand<RedisKeyAndValue> request) {
        RedisKeyAndValue dto = request.getDto();
        RedisSoftwareInterface redis = new RedisSoftware(dto.getRedisId(), rep);
        redis.del(dto.getKey());
        redis.close();
        return true;
    }

    @Override
    public List<GetInfosResponse> getInfos(IdQuery request) {
        RedisSoftwareInterface redis = new RedisSoftware(request.getId(), rep);
        redis.findServer(serverRepository);
        List<GetInfosResponse> redisInfo = redis.findRedisInfo();
        redis.close();
        return redisInfo;
    }


}
