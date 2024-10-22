package team.opentech.usher.service.impl;

import team.opentech.usher.annotation.ReadWriteMark;
import team.opentech.usher.assembler.SoftwareAssembler;
import team.opentech.usher.enums.ReadWriteTypeEnum;
import team.opentech.usher.enums.RedisAddEnum;
import team.opentech.usher.enums.SoftwareStatusEnum;
import team.opentech.usher.pojo.DO.SoftwareDO;
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
import team.opentech.usher.pojo.entity.Software;
import team.opentech.usher.pojo.entity.software.RedisSoftware;
import team.opentech.usher.pojo.entity.software.RedisSoftwareInterface;
import team.opentech.usher.pojo.entity.type.Identifier;
import team.opentech.usher.repository.ServerRepository;
import team.opentech.usher.repository.SoftwareRepository;
import team.opentech.usher.service.SoftwareService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        Identifier id = software.saveSelf(rep);
        return id.getId();
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
        Identifier[] identifiers = request.getIds().stream().map(Identifier::new).toArray(Identifier[]::new);
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
