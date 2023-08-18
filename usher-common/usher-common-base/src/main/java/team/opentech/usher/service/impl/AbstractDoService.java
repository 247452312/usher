package team.opentech.usher.service.impl;

import team.opentech.usher.assembler.BaseEntityAssembler;
import team.opentech.usher.pojo.DO.base.BaseDO;
import team.opentech.usher.pojo.DTO.base.IdDTO;
import team.opentech.usher.pojo.DTO.base.Page;
import team.opentech.usher.pojo.cqe.query.demo.Arg;
import team.opentech.usher.pojo.cqe.query.demo.Limit;
import team.opentech.usher.pojo.cqe.query.demo.Order;
import team.opentech.usher.pojo.entity.base.AbstractDoEntity;
import team.opentech.usher.pojo.entity.type.Identifier;
import team.opentech.usher.repository.base.BaseEntityRepository;
import team.opentech.usher.service.BaseDoService;
import java.util.List;


/**
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月25日 20时36分
 */
public abstract class AbstractDoService<DO extends BaseDO, ENTITY extends AbstractDoEntity<DO>, DTO extends IdDTO, REP extends BaseEntityRepository<DO, ENTITY>, ASSEM extends BaseEntityAssembler<DO, ENTITY, DTO>> implements BaseDoService<DTO> {

    protected final ASSEM assem;

    protected final REP rep;

    protected AbstractDoService(ASSEM u, REP r) {
        this.assem = u;
        this.rep = r;
    }

    @Override
    public Long add(DTO dto) {
        ENTITY t = assem.toEntity(dto);
        Identifier save = rep.save(t);
        return save.getId();
    }

    @Override
    public Integer remove(Identifier id) {
        return rep.remove(id);
    }

    @Override
    public Integer remove(List<Arg> args) {
        return rep.remove(args, null);
    }

    @Override
    public Page<DTO> query(List<Arg> args, Order order, Limit limit) {
        Page<ENTITY> tPage = rep.find(args, order, limit);
        return assem.pageToDTO(tPage);
    }

    @Override
    public List<DTO> query(List<Identifier> ids) {
        List<ENTITY> entities = rep.find(ids);
        return assem.listEntityToDTO(entities);
    }

    @Override
    public List<DTO> queryNoPage(List<Arg> args, Order order) {
        List<ENTITY> noPage = rep.findNoPage(args, order);
        return assem.listEntityToDTO(noPage);
    }

    @Override
    public List<DTO> queryNoPage(List<Arg> args) {
        List<ENTITY> noPage = rep.findNoPage(args, null);
        return assem.listEntityToDTO(noPage);
    }

    @Override
    public DTO query(Identifier id) {
        ENTITY entity = rep.find(id);
        return assem.toDTO(entity);
    }

    @Override
    public Integer update(DTO dto, List<Arg> args) {
        ENTITY entity = assem.toEntity(dto);
        return rep.change(entity, args);
    }

    @Override
    public Long count(List<Arg> args) {
        return rep.count(args);
    }

}
