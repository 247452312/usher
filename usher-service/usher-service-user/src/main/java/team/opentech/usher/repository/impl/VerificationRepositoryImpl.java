package team.opentech.usher.repository.impl;

import team.opentech.usher.annotation.Repository;
import team.opentech.usher.pojo.entity.Verification;
import team.opentech.usher.redis.RedisPoolHandle;
import team.opentech.usher.redis.Redisable;
import team.opentech.usher.repository.VerificationRepository;
import javax.annotation.Resource;


/**
 * 仓库
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月24日 17时27分
 */
@Repository
public class VerificationRepositoryImpl implements VerificationRepository {


    @Resource
    private RedisPoolHandle redis;


    @Override
    public void saveVerificationToCache(String key, String code, Integer time) {
        try (Redisable jedis = redis.getJedis()) {
            jedis.set(key, code);
            jedis.expire(key, time);
        }
    }

    @Override
    public Boolean verification(Verification verification) {
        try (Redisable jedis = redis.getJedis()) {
            String realCode = jedis.get(verification.key());
            return verification.code().equals(realCode);
        }
    }

    @Override
    public void cleanCache(Verification verification) {
        try (Redisable jedis = redis.getJedis()) {
            jedis.del(verification.key());
        }
    }
}
