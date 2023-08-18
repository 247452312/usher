package team.opentech.usher.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import team.opentech.usher.annotation.ReadWriteMark;
import team.opentech.usher.assembler.TraceDetailAssembler;
import team.opentech.usher.dao.TraceDetailDao;
import team.opentech.usher.pojo.DO.TraceDetailDO;
import team.opentech.usher.pojo.DTO.TraceDetailDTO;
import team.opentech.usher.pojo.DTO.request.GetTraceDetailByHashCodeRequest;
import team.opentech.usher.pojo.DTO.response.GetTraceDetailByHashCodeResponse;
import team.opentech.usher.pojo.entity.TraceDetail;
import team.opentech.usher.repository.TraceDetailRepository;
import team.opentech.usher.service.TraceDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * (TraceDetail)表 内部服务实现类
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月29日 16时58分54秒
 */
@Service
@ReadWriteMark(tables = {"sys_trace_detail"})
public class TraceDetailServiceImpl extends AbstractDoService<TraceDetailDO, TraceDetail, TraceDetailDTO, TraceDetailRepository, TraceDetailAssembler> implements TraceDetailService {

    @Autowired
    private TraceDetailDao dao;


    public TraceDetailServiceImpl(TraceDetailAssembler assembler, TraceDetailRepository repository) {
        super(assembler, repository);
    }

    @Override
    public GetTraceDetailByHashCodeResponse getTraceDetailByHashCode(GetTraceDetailByHashCodeRequest request) {
        LambdaQueryWrapper<TraceDetailDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(TraceDetailDO::getHashCode, request.getHashCode());
        queryWrapper.eq(TraceDetailDO::getType, request.getType());
        queryWrapper.orderByDesc(TraceDetailDO::getEndTime);
        queryWrapper.last("limit 1");
        TraceDetailDO entity = dao.selectOne(queryWrapper);
        TraceDetailDTO traceDetailDTO = assem.toDTO(entity);
        return GetTraceDetailByHashCodeResponse.build(traceDetailDTO);
    }


}
