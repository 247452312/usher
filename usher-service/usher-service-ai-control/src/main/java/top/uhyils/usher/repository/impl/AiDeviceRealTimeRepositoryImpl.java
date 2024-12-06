package top.uhyils.usher.repository.impl;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import top.uhyils.usher.annotation.Repository;
import top.uhyils.usher.assembler.AiDeviceRealTimeAssembler;
import top.uhyils.usher.dao.AiDeviceRealTimeDao;
import top.uhyils.usher.pojo.DO.AiDeviceRealTimeDO;
import top.uhyils.usher.pojo.DTO.AiDeviceRealTimeDTO;
import top.uhyils.usher.pojo.entity.AiDeviceRealTime;
import top.uhyils.usher.repository.AiDeviceRealTimeRepository;
import top.uhyils.usher.repository.base.AbstractRepository;


/**
 * 设备实时状态表(AiDeviceRealTime)表 仓库实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2024年12月06日 13时57分
 */
@Repository
public class AiDeviceRealTimeRepositoryImpl extends AbstractRepository<AiDeviceRealTime, AiDeviceRealTimeDO, AiDeviceRealTimeDao, AiDeviceRealTimeDTO, AiDeviceRealTimeAssembler> implements AiDeviceRealTimeRepository {

    protected AiDeviceRealTimeRepositoryImpl(AiDeviceRealTimeAssembler convert, AiDeviceRealTimeDao dao) {
        super(convert, dao);
    }


    @Override
    public AiDeviceRealTime findByDevice(Long deviceId) {
        LambdaQueryChainWrapper<AiDeviceRealTimeDO> wrapper = lambdaQuery();
        wrapper.eq(AiDeviceRealTimeDO::getDeviceId, deviceId);
        AiDeviceRealTimeDO one = wrapper.one();
        return assembler.toEntity(one);
    }

    @Override
    public AiDeviceRealTime findByUnique(String value) {
        LambdaQueryChainWrapper<AiDeviceRealTimeDO> wrapper = lambdaQuery();
        wrapper.eq(AiDeviceRealTimeDO::getUniqueMark, value);
        AiDeviceRealTimeDO one = wrapper.one();
        return assembler.toEntity(one);
    }
}
