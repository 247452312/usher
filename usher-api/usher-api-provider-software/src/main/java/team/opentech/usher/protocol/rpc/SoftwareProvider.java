package team.opentech.usher.protocol.rpc;

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
import team.opentech.usher.protocol.rpc.base.DTOProvider;
import java.util.List;

/**
 * 中间件表(Software)表 Rpc对外访问层
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年09月02日 08时42分22秒
 */
public interface SoftwareProvider extends DTOProvider<SoftwareDTO> {

    /**
     * 刷新某一个id的状态
     *
     * @param request
     *
     * @return
     */
    SoftwareDTO reload(IdCommand request);


    /**
     * 启动redis
     *
     * @param request id
     *
     * @return 返回信息
     */
    OperateSoftwareResponse start(IdCommand request);


    /**
     * 停止某一个redis
     *
     * @param request id
     *
     * @return 返回信息
     */
    OperateSoftwareResponse stop(IdCommand request);


    /**
     * 批量删除redis
     *
     * @param request 多个redis id
     *
     * @return 删除是否成功
     */
    Boolean deleteManyRedis(IdsCommand request);


    /**
     * 批量更新redis信息
     *
     * @param request
     *
     * @return
     */
    Boolean reloadManyRedis(IdsCommand request);

    /**
     * 开启选中的redis
     *
     * @param request 多个redis id
     *
     * @return 是否成功
     */
    Boolean startManyRedis(IdsCommand request);

    /**
     * 停止选中的redis
     *
     * @param request 多个redis id
     *
     * @return 是否成功
     */
    Boolean stopManyRedis(IdsCommand request);


    /**
     * redis的key值
     *
     * @param request 仓库名称
     *
     * @return redis的key
     */
    List<String> getRedisKeys(GetRedisKeysQuery request);

    /**
     * 获取redis仓库名称
     *
     * @param request 请求
     *
     * @return 仓库名称
     */
    Integer getRedisDb(IdQuery request);

    /**
     * 添加新的key 如果成功返回1 如果有重复 返回2
     *
     * @param request redis中的实体
     *
     * @return 如果成功 返回1 如果有重复 返回2
     */
    Integer addKey(AddCommand<RedisKeyAndValue> request);

    /**
     * 添加新的key 如果成功返回1
     * 如果有重复就覆盖 啥也不管
     *
     * @param request redis中的实体
     *
     * @return 如果成功 返回1 如果有重复 返回2
     */
    Integer addKeyCover(AddCommand<RedisKeyAndValue> request);

    /**
     * 修改key值
     *
     * @param request 请求
     *
     * @return 1->成功 2->没有此key
     */
    Integer updateKey(ChangeCommand<RedisKeyAndValue> request);


    /**
     * 获取key的value值
     *
     * @param request redisId,DB,key
     *
     * @return
     */
    String getValueByKey(KeyQuery request);


    /**
     * 删除指定的key
     *
     * @param request
     *
     * @return
     */
    Boolean deleteRedisByKey(ChangeCommand<RedisKeyAndValue> request);


    /**
     * 获取redis的内容
     *
     * @param request id
     *
     * @return jedis.info()
     */
    List<GetInfosResponse> getInfos(IdQuery request);
}

