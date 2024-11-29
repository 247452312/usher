package team.opentech.usher.protocol.rpc.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import team.opentech.usher.pojo.DTO.DictDTO;
import team.opentech.usher.pojo.DTO.DictItemDTO;
import team.opentech.usher.pojo.DTO.base.Page;
import team.opentech.usher.pojo.DTO.request.GetByCodeQuery;
import team.opentech.usher.pojo.DTO.request.GetByItemArgsQuery;
import team.opentech.usher.pojo.DTO.response.LastPlanDTO;
import team.opentech.usher.pojo.DTO.response.QuickStartDTO;
import team.opentech.usher.pojo.DTO.response.VersionInfoDTO;
import team.opentech.usher.pojo.cqe.DefaultCQE;
import team.opentech.usher.pojo.cqe.command.ChangeCommand;
import team.opentech.usher.pojo.cqe.command.IdCommand;
import team.opentech.usher.pojo.cqe.command.base.AddCommand;
import team.opentech.usher.pojo.cqe.query.IdQuery;
import team.opentech.usher.pojo.cqe.query.base.BaseArgQuery;
import team.opentech.usher.pojo.cqe.query.demo.Arg;
import team.opentech.usher.pojo.cqe.query.demo.Limit;
import team.opentech.usher.pojo.cqe.query.demo.Order;
import team.opentech.usher.pojo.entity.type.Code;
import team.opentech.usher.protocol.rpc.DictProvider;
import team.opentech.usher.protocol.rpc.base.BaseDefaultProvider;
import team.opentech.usher.rpc.annotation.RpcService;
import team.opentech.usher.service.BaseDoService;
import team.opentech.usher.service.DictService;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年05月27日 16时28分
 */
@RpcService
public class DictProviderImpl extends BaseDefaultProvider<DictDTO> implements DictProvider {


    @Autowired
    private DictService service;

    @Override
    public Boolean insertItem(AddCommand<DictItemDTO> request) {
        DictItemDTO dto = request.getDto();
        return service.insertItem(dto);

    }

    @Override
    public List<DictItemDTO> getItemByDictId(IdQuery request) {
        return service.getItemByDictId(request.getId());

    }

    @Override
    public Boolean updateItem(ChangeCommand<DictItemDTO> request) {
        DictItemDTO dto = request.getDto();
        BaseArgQuery order = request.getOrder();
        return service.updateItem(dto, order.getArgs());
    }

    @Override
    public Boolean deleteItem(IdCommand request) {
        return service.deleteItem(request.getId());

    }

    @Override
    public Boolean cleanDictItem(IdCommand request) {
        return service.cleanDictItem(request.getId());

    }

    @Override
    public DictItemDTO getItemById(IdQuery request) {
        return service.getItemById(request.getId());

    }

    @Override
    public Page<DictItemDTO> getByItemArgs(GetByItemArgsQuery request) {
        List<Arg> args = request.getArgs();
        Order order = request.getOrder();
        Limit limit = request.getLimit();
        return service.getByItemArgs(request.getDictId(), args, order, limit);

    }

    @Override
    public VersionInfoDTO getVersionInfoResponse(DefaultCQE request) {
        return service.getVersionInfoResponse();

    }

    @Override
    public LastPlanDTO getLastPlanResponse(DefaultCQE request) {
        return service.getLastPlanResponse();

    }

    @Override
    public List<String> getAllMenuIcon(DefaultCQE request) {
        return service.getAllMenuIcon();

    }

    @Override
    public List<DictItemDTO> getByCode(GetByCodeQuery request) {
        Code code = new Code(request.getCode());
        return service.getByCode(code);

    }

    @Override
    public QuickStartDTO getQuickStartResponse(DefaultCQE request) {
        return service.getQuickStartResponse();
    }

    @Override
    protected BaseDoService<DictDTO> getService() {
        return service;
    }
}
