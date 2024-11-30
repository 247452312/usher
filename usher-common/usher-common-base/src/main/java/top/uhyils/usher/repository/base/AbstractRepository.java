package top.uhyils.usher.repository.base;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import top.uhyils.usher.assembler.AbstractAssembler;
import top.uhyils.usher.dao.base.DefaultDao;
import top.uhyils.usher.enums.OrderSymbolEnum;
import top.uhyils.usher.enums.Symbol;
import top.uhyils.usher.pojo.DO.base.BaseDO;
import top.uhyils.usher.pojo.DTO.base.IdDTO;
import top.uhyils.usher.pojo.DTO.base.Page;
import top.uhyils.usher.pojo.cqe.query.demo.Arg;
import top.uhyils.usher.pojo.cqe.query.demo.Column;
import top.uhyils.usher.pojo.cqe.query.demo.ColumnName;
import top.uhyils.usher.pojo.cqe.query.demo.Limit;
import top.uhyils.usher.pojo.cqe.query.demo.Order;
import top.uhyils.usher.pojo.entity.base.AbstractDoEntity;
import top.uhyils.usher.pojo.entity.base.IdEntity;
import top.uhyils.usher.util.Asserts;
import top.uhyils.usher.util.CollectionUtil;


/**
 * repository模板
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月22日 15时52分
 */
public abstract class AbstractRepository<EN extends AbstractDoEntity<DO>, DO extends BaseDO, DAO extends DefaultDao<DO>, DTO extends IdDTO, ASSEM extends AbstractAssembler<DO, EN, DTO>> extends ServiceImpl<DAO, DO> implements BaseEntityRepository<DO, EN> {

    /**
     * 转换器
     */
    protected final ASSEM assembler;

    protected final DAO dao;

    protected AbstractRepository(ASSEM convert, DAO dao) {
        this.assembler = convert;
        this.dao = dao;
    }

    @Override
    public Long save(EN entity) {
        if (entity.notHaveId()) {
            entity.perInsert();
            DO aDo = assembler.toDo(entity);
            dao.insert(aDo);
            entity.upId();
            return aDo.getId();
        }
        boolean canUpdate = entity.canUpdate();
        if (!canUpdate) {
            DO aDo = assembler.toDo(entity);
            return aDo.getId();
        }
        entity.perUpdate();
        DO aDo = assembler.toDo(entity);
        dao.updateById(aDo);
        return aDo.getId();
    }

    @Override
    public List<Long> save(List<EN> entities) {
        return entities.stream().map(this::save).collect(Collectors.toList());
    }


    @Override
    public <E extends IdEntity> EN find(E query) {
        Optional<Long> id = query.getUnique();
        Asserts.assertTrue(id.isPresent(), "单个查询中不存在id");
        return find(id.get());
    }

    /**
     * 根据id查询
     *
     * @param ids 主键ids
     *
     * @return
     */
    @Override
    public <E extends Long> List<EN> find(List<E> ids) {
        if (CollectionUtil.isEmpty(ids)) {
            return new ArrayList<>();
        }
        List<DO> byIds = dao.selectBatchIds(ids);
        return byIds.stream().map(assembler::toEntity).collect(Collectors.toList());
    }

    @Override
    public <E extends Long> EN find(E id) {
        DO byId = dao.selectById(id);
        return assembler.toEntity(byId);
    }

    @Override
    public List<EN> findNoPage(List<Arg> args, Order order) {
        List<DO> result;
        QueryWrapper<DO> queryWrapper = Symbol.makeWrapper(args);
        fillWrapperOrder(order, queryWrapper);
        result = dao.selectList(queryWrapper);
        return result.stream().map(assembler::toEntity).collect(Collectors.toList());
    }

    @Override
    public List<EN> findNoPage(List<Arg> args) {
        return findNoPage(args, null);
    }

    @Override
    public Page<EN> find(List<Arg> args, Order order, Limit limit) {
        Asserts.assertTrue(limit != null, "分页参数不能为空");
        if (args == null) {
            args = new ArrayList<>();
        }
        QueryWrapper<DO> queryWrapper = Symbol.makeWrapper(args);
        fillWrapperOrder(order, queryWrapper);
        com.baomidou.mybatisplus.extension.plugins.pagination.Page<DO> page = makePage(limit);
        com.baomidou.mybatisplus.extension.plugins.pagination.Page<DO> doPage = dao.selectPage(page, queryWrapper);
        List<EN> ens = assembler.listToEntity(doPage.getRecords());
        return Page.build(ens, limit, doPage.getTotal());
    }


    @Override
    public int remove(List<EN> entities) {
        return entities.stream().mapToInt(this::remove).sum();
    }

    @Override
    public int remove(EN entity) {
        DO aDo = assembler.toDo(entity);
        aDo.changeToDelete();
        aDo.preUpdate();
        return dao.updateById(aDo);
    }

    @Override
    public <E extends Long> int remove(E... ids) {
        List<EN> ens = find(Arrays.asList(ids));

        List<DO> updateDos = ens.stream().peek(AbstractDoEntity::perUpdate).map(AbstractDoEntity::toData).map(Optional::get).collect(Collectors.toList());
        return dao.updateBatch(updateDos);
    }

    @Override
    public int remove(List<Arg> args, Limit limit) {
        List<EN> noPage = findNoPage(args, null);
        return remove(noPage);
    }

    @Override
    public <E extends Long> int removeByIds(List<E> ids) {
        List<EN> ens = find(ids);
        List<DO> updateDos = ens.stream().peek(AbstractDoEntity::perUpdate).map(AbstractDoEntity::toData).map(Optional::get).collect(Collectors.toList());
        return dao.updateBatch(updateDos);
    }

    @Override
    public int change(EN entity, List<Arg> args) {
        QueryWrapper<DO> queryWrapper = Symbol.makeWrapper(args);
        DO dataAndValidate = entity.toDataAndValidate();
        return dao.update(dataAndValidate, queryWrapper);
    }

    @Override
    public long count(List<Arg> args) {
        QueryWrapper<DO> queryWrapper = Symbol.makeWrapper(args);
        return dao.selectCount(queryWrapper);
    }

    /**
     * 填充order
     *
     * @param order
     * @param queryWrapper
     */
    protected void fillWrapperOrder(Order order, QueryWrapper<DO> queryWrapper) {
        if (order == null || order.getColumns() == null) {
            return;
        }
        for (Column column : order.getColumns()) {
            ColumnName columnName = column.getColumnName();
            OrderSymbolEnum symbol = column.getSymbol();
            switch (symbol) {
                case ASC:
                    queryWrapper.orderByAsc(columnName.getName());
                    break;
                case DESC:
                    queryWrapper.orderByDesc(columnName.getName());
                    break;
                default:
                    Asserts.throwException("排序符号不正确");
            }
        }
    }


    /**
     * 获取分页入参
     *
     * @param limit
     *
     * @return
     */
    protected com.baomidou.mybatisplus.extension.plugins.pagination.Page<DO> makePage(Limit limit) {
        if (limit == null || Boolean.FALSE.equals(limit.getPage())) {
            return new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>();
        }
        return new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(limit.getNumber(), limit.getSize());
    }


}
