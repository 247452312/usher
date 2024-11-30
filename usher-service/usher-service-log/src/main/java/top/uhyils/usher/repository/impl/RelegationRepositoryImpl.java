package top.uhyils.usher.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import top.uhyils.usher.annotation.Repository;
import top.uhyils.usher.assembler.RelegationAssembler;
import top.uhyils.usher.dao.RelegationDao;
import top.uhyils.usher.pojo.DO.RelegationDO;
import top.uhyils.usher.pojo.DTO.RelegationDTO;
import top.uhyils.usher.pojo.entity.Relegation;
import top.uhyils.usher.redis.RedisPoolHandle;
import top.uhyils.usher.redis.Redisable;
import top.uhyils.usher.repository.RelegationRepository;
import top.uhyils.usher.repository.base.AbstractRepository;
import top.uhyils.usher.util.Asserts;


/**
 * 接口降级策略(Relegation)表 仓库实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年09月27日 09时33分23秒
 */
@Repository
public class RelegationRepositoryImpl extends AbstractRepository<Relegation, RelegationDO, RelegationDao, RelegationDTO, RelegationAssembler> implements RelegationRepository {

    /**
     * 操作间隔
     */
    private static final String OPTION_INTERVAL_TIME_KEY = "OPTION_INTERVAL_TIME_KEY";

    @Autowired
    private RedisPoolHandle redisPool;

    protected RelegationRepositoryImpl(RelegationAssembler convert, RelegationDao dao) {
        super(convert, dao);
    }

    @Override
    public boolean checkRepeat(Relegation relegation) {
        RelegationDO relegationDO = relegation.toData().orElseThrow(() -> Asserts.makeException("接口降级时未找到对应信息"));
        LambdaQueryWrapper<RelegationDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(RelegationDO::getServiceName, relegationDO.getServiceName());
        queryWrapper.eq(RelegationDO::getMethodName, relegationDO.getMethodName());
        queryWrapper.eq(RelegationDO::getParamLength, relegationDO.getParamLength() != null ? relegationDO.getParamLength() : 1);
        Long count = dao.selectCount(queryWrapper);
        return count != 0;
    }

    @Override
    public void markRelegationOptionTime(Long intervalTime) {
        Asserts.assertTrue(intervalTime != null);
        try (Redisable jedis = redisPool.getJedis()) {
            // 记录当前时间
            jedis.set(OPTION_INTERVAL_TIME_KEY, Long.toString(System.currentTimeMillis()));
            // 过期时间同指定时间
            jedis.expire(OPTION_INTERVAL_TIME_KEY, (int) (intervalTime / 1000L));
        }
    }

    @Override
    public boolean checkRelegationOptionTime() {
        try (Redisable jedis = redisPool.getJedis()) {
            // 如果存在则不能操作
            return !jedis.exists(OPTION_INTERVAL_TIME_KEY);
        }
    }

    @Override
    public List<Relegation> findDegradationLowInterfaceByLevel(Long level) {
        LambdaQueryWrapper<RelegationDO> wrapper = Wrappers.lambdaQuery();
        wrapper.ge(RelegationDO::getLevel, level);
        List<RelegationDO> dos = dao.selectList(wrapper);
        return assembler.listToEntity(dos);
    }

}
