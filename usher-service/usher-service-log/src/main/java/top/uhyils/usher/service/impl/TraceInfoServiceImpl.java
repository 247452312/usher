package top.uhyils.usher.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.uhyils.usher.annotation.ReadWriteMark;
import top.uhyils.usher.assembler.TraceInfoAssembler;
import top.uhyils.usher.context.UsherContext;
import top.uhyils.usher.enums.LogTypeEnum;
import top.uhyils.usher.facade.DictFacade;
import top.uhyils.usher.facade.ServiceControlFacade;
import top.uhyils.usher.pojo.DO.TraceDetailStatisticsView;
import top.uhyils.usher.pojo.DO.TraceInfoDO;
import top.uhyils.usher.pojo.DTO.DictItemDTO;
import top.uhyils.usher.pojo.DTO.TraceDetailStatisticsDTO;
import top.uhyils.usher.pojo.DTO.TraceInfoDTO;
import top.uhyils.usher.pojo.DTO.base.Page;
import top.uhyils.usher.pojo.DTO.request.GetLinkByTraceIdAndRpcIdQuery;
import top.uhyils.usher.pojo.DTO.request.GetTraceInfoByArgAndPageRequest;
import top.uhyils.usher.pojo.DTO.response.LogTypeDTO;
import top.uhyils.usher.pojo.cqe.DefaultCQE;
import top.uhyils.usher.pojo.cqe.command.BlankCommand;
import top.uhyils.usher.pojo.cqe.query.BlackQuery;
import top.uhyils.usher.pojo.cqe.query.TraceIdQuery;
import top.uhyils.usher.pojo.entity.MonitorConcurrent;
import top.uhyils.usher.pojo.entity.Trace;
import top.uhyils.usher.pojo.entity.TraceInfo;
import top.uhyils.usher.repository.RelegationRepository;
import top.uhyils.usher.repository.TraceInfoRepository;
import top.uhyils.usher.service.TraceInfoService;
import top.uhyils.usher.util.Asserts;
import top.uhyils.usher.util.CollectionUtil;

/**
 * (TraceInfo)表 内部服务实现类
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月29日 16时58分55秒
 */
@Service
@ReadWriteMark(tables = {"sys_trace_info"})
public class TraceInfoServiceImpl extends AbstractDoService<TraceInfoDO, TraceInfo, TraceInfoDTO, TraceInfoRepository, TraceInfoAssembler> implements TraceInfoService {

    @Autowired
    private DictFacade dictFacade;

    @Autowired
    private ServiceControlFacade serviceControlFacade;

    @Autowired
    private RelegationRepository repository;

    public TraceInfoServiceImpl(TraceInfoAssembler assembler, TraceInfoRepository repository) {
        super(assembler, repository);
    }

    @Override
    public List<TraceInfoDTO> getLinkByTraceIdAndRpcId(GetLinkByTraceIdAndRpcIdQuery request) {
        Trace trace = new Trace(request.getTraceId(), request.getRpcId());
        List<TraceInfo> lists = trace.findTraceInfoByTraceIdAndRpcId(rep);
        return assem.listEntityToDTO(lists);
    }

    @Override
    public List<TraceInfoDTO> getLinkByTraceId(TraceIdQuery request) {
        List<TraceInfo> traceInfo = rep.findLinkByTraceId(request.getTraceId());
        return assem.listEntityToDTO(traceInfo);
    }

    @Override
    public Page<TraceInfoDTO> getTraceInfoByArgAndPage(GetTraceInfoByArgAndPageRequest request) {
        Page<TraceInfo> traceInfoPage = rep.find(request);
        return assem.pageToDTO(traceInfoPage);
    }

    @Override
    public Page<TraceDetailStatisticsDTO> getTraceStatistics(BlackQuery request) {
        Page<TraceDetailStatisticsView> result = rep.findView(request);
        return assem.pageViewToDTO(result);
    }

    @Override
    public ArrayList<LogTypeDTO> getLogType(DefaultCQE request) {
        ArrayList<LogTypeDTO> list = new ArrayList<>();
        for (LogTypeEnum value : LogTypeEnum.values()) {
            LogTypeDTO build = LogTypeDTO.build(value.name(), value.getCode());
            list.add(build);
        }
        return list;
    }

    @Override
    public void monitorConcurrentNumber(BlankCommand request) {
        // 获取每秒网关的并发数
        Long concurrentNumber = this.getConcurrentNumber(request);

        //获取字典中人工设置的自动降级的并发数
        List<DictItemDTO> code = dictFacade.getByCode(UsherContext.CONCURRENT_NUM_DICT_CODE);
        Asserts.assertTrue(CollectionUtil.isNotEmpty(code));
        DictItemDTO dictItemEntity = code.get(0);
        Long concurrentNumberSetable = Long.parseLong(dictItemEntity.getValue());
        MonitorConcurrent concurrent = new MonitorConcurrent(rep, repository, concurrentNumber, concurrentNumberSetable);
        concurrent.syncDegradationStatus(repository, serviceControlFacade);
    }

    @Override
    public Long getConcurrentNumber(DefaultCQE request) {
        return rep.findConcurrentNumber(LogTypeEnum.CONTROLLER.getCode(), System.currentTimeMillis() - 1000L);
    }
}
