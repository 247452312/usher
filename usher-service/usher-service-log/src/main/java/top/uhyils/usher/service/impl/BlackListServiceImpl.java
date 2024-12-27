package top.uhyils.usher.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import top.uhyils.usher.annotation.ReadWriteMark;
import top.uhyils.usher.assembler.BlackListAssembler;
import top.uhyils.usher.builder.BlackListBuilder;
import top.uhyils.usher.pojo.DO.BlackListDO;
import top.uhyils.usher.pojo.DTO.BlackListDTO;
import top.uhyils.usher.pojo.DTO.request.AddBlackIpRequest;
import top.uhyils.usher.pojo.DTO.request.GetLogIntervalByIpQuery;
import top.uhyils.usher.pojo.cqe.DefaultCQE;
import top.uhyils.usher.pojo.entity.BlackList;
import top.uhyils.usher.pojo.entity.UserSpiderBehavior;
import top.uhyils.usher.repository.BlackListRepository;
import top.uhyils.usher.repository.TraceDetailRepository;
import top.uhyils.usher.service.BlackListService;

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

    @Autowired
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
