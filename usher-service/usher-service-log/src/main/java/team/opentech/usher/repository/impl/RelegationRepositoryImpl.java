package team.opentech.usher.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import team.opentech.usher.annotation.Repository;
import team.opentech.usher.assembler.RelegationAssembler;
import team.opentech.usher.dao.RelegationDao;
import team.opentech.usher.pojo.DO.RelegationDO;
import team.opentech.usher.pojo.DTO.RelegationDTO;
import team.opentech.usher.pojo.entity.Relegation;
import team.opentech.usher.redis.RedisPoolHandle;
import team.opentech.usher.redis.Redisable;
import team.opentech.usher.repository.RelegationRepository;
import team.opentech.usher.repository.base.AbstractRepository;
import team.opentech.usher.util.Asserts;
import java.util.List;
import javax.annotation.Resource;


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

    @Resource
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
