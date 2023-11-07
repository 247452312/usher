package team.opentech.usher.service.impl;

import team.opentech.usher.annotation.ReadWriteMark;
import team.opentech.usher.assembler.BlackListAssembler;
import team.opentech.usher.builder.BlackListBuilder;
import team.opentech.usher.pojo.DO.BlackListDO;
import team.opentech.usher.pojo.DTO.BlackListDTO;
import team.opentech.usher.pojo.DTO.request.AddBlackIpRequest;
import team.opentech.usher.pojo.DTO.request.GetLogIntervalByIpQuery;
import team.opentech.usher.pojo.cqe.DefaultCQE;
import team.opentech.usher.pojo.entity.BlackList;
import team.opentech.usher.pojo.entity.UserSpiderBehavior;
import team.opentech.usher.repository.BlackListRepository;
import team.opentech.usher.repository.TraceDetailRepository;
import team.opentech.usher.service.BlackListService;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 黑名单(BlackList)表 内部服务实现类
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月29日 16时58分52秒
 */
@Service
@ReadWriteMark(tables = {"sys_black_list"})
public class BlackListServiceImpl extends AbstractDoService<BlackListDO, BlackList, BlackListDTO, BlackListRepository, BlackListAssembler> implements BlackListService {


    /**
     * 可以评价的最小 ip访问次数
     */
    @Value("${black-list.size}")
    private Integer canEvaluateMinSize;

    /**
     * 方差阈值 低于此阈值被认为是爬虫
     */
    @Value("${black-list.spider.frequency}")
    private Integer frequency;

    @Resource
    private TraceDetailRepository traceDetailRepository;

    public BlackListServiceImpl(BlackListAssembler assembler, BlackListRepository repository) {
        super(assembler, repository);
    }

    @Override
    public Boolean getLogIntervalByIp(GetLogIntervalByIpQuery request) {
        UserSpiderBehavior userBehavior = new UserSpiderBehavior(request.getIp());
        userBehavior.fillLastTime(canEvaluateMinSize, traceDetailRepository);
        return userBehavior.judgeSpider(frequency);

    }

    @Override
    public List<String> getAllIpBlackList(DefaultCQE request) {
        return rep.findAllIpBlackList();
    }

    @Override
    public Boolean addBlackIp(AddBlackIpRequest request) {
        BlackListDTO blackListEntity = BlackListBuilder.buildByIp(request.getIp());
        BlackList blackList = assem.toEntity(blackListEntity);
        rep.save(blackList);
        return true;
    }
}
