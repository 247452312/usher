package team.opentech.usher.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.opentech.usher.annotation.ReadWriteMark;
import team.opentech.usher.assembler.RelegationAssembler;
import team.opentech.usher.facade.ServiceControlFacade;
import team.opentech.usher.pojo.DO.RelegationDO;
import team.opentech.usher.pojo.DTO.RelegationDTO;
import team.opentech.usher.pojo.DTO.base.Page;
import team.opentech.usher.pojo.cqe.event.CheckAndAddRelegationEvent;
import team.opentech.usher.pojo.cqe.query.demo.Arg;
import team.opentech.usher.pojo.cqe.query.demo.Limit;
import team.opentech.usher.pojo.cqe.query.demo.Order;
import team.opentech.usher.pojo.entity.Relegation;
import team.opentech.usher.repository.RelegationRepository;
import team.opentech.usher.service.RelegationService;

/**
 * 接口降级策略(Relegation)表 内部服务实现类
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年09月27日 09时33分24秒
 */
@Service
@ReadWriteMark(tables = {"sys_relegation"})
public class RelegationServiceImpl extends AbstractDoService<RelegationDO, Relegation, RelegationDTO, RelegationRepository, RelegationAssembler> implements RelegationService {

    @Autowired
    private ServiceControlFacade facade;


    public RelegationServiceImpl(RelegationAssembler assembler, RelegationRepository repository) {
        super(assembler, repository);
    }

    @Override
    public void checkAndAddRelegation(CheckAndAddRelegationEvent event) {
        Relegation relegation = new Relegation(
            event.getTraceDetailDTO().getType(),
            event.getTraceDetailDTO().getOtherOne(),
            event.getTraceDetailDTO().getOtherTwo());

        relegation.validate();

        boolean repeat = relegation.checkRepeat(rep);
        if (repeat) {
            return;
        }
        // 设置默认值
        relegation.initDefault();
        // 保存
        relegation.saveSelf(rep);

    }

    @Override
    public Boolean demotion(String serviceName, String methodName) {
        Relegation relegation = new Relegation(serviceName, methodName);
        return relegation.demotion(facade);
    }

    @Override
    public Boolean recover(String serviceName, String methodName) {
        Relegation relegation = new Relegation(serviceName, methodName);
        return relegation.recover(facade);
    }

    @Override
    public Page<RelegationDTO> query(List<Arg> args, Order order, Limit limit) {
        Page<RelegationDTO> query = super.query(args, order, limit);
        List<RelegationDTO> list = query.getList();
        facade.fillDisable(list);
        return query;
    }

    @Override
    public List<RelegationDTO> query(List<Long> ids) {
        List<RelegationDTO> query = super.query(ids);
        facade.fillDisable(query);
        return query;
    }

    @Override
    public List<RelegationDTO> queryNoPage(List<Arg> args, Order order) {
        List<RelegationDTO> dtos = super.queryNoPage(args, order);
        facade.fillDisable(dtos);
        return dtos;
    }

    @Override
    public RelegationDTO query(Long id) {
        RelegationDTO query = super.query(id);
        facade.fillDisable(query);
        return query;
    }
}
