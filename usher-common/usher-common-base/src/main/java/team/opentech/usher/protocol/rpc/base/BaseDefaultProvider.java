package team.opentech.usher.protocol.rpc.base;

import team.opentech.usher.pojo.DTO.base.IdDTO;
import team.opentech.usher.pojo.DTO.base.Page;
import team.opentech.usher.pojo.cqe.command.ChangeCommand;
import team.opentech.usher.pojo.cqe.command.IdCommand;
import team.opentech.usher.pojo.cqe.command.RemoveCommand;
import team.opentech.usher.pojo.cqe.command.base.AddCommand;
import team.opentech.usher.pojo.cqe.query.BlackQuery;
import team.opentech.usher.pojo.cqe.query.IdQuery;
import team.opentech.usher.pojo.cqe.query.IdsQuery;
import team.opentech.usher.pojo.cqe.query.base.BaseArgQuery;
import team.opentech.usher.pojo.entity.type.Identifier;
import team.opentech.usher.service.BaseDoService;
import team.opentech.usher.util.LogUtil;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年05月05日 17时57分
 */
public abstract class BaseDefaultProvider<T extends IdDTO> implements DTOProvider<T> {

    @Override
    public Page<T> query(BlackQuery query) {
        return getService().query(query.getArgs(), query.getOrder(), query.getLimit());
    }

    @Override
    public List<T> queryNoPage(BlackQuery query) {
        return getService().queryNoPage(query.getArgs(), query.getOrder());
    }

    @Override
    public T queryById(IdQuery query) {
        return getService().query(new Identifier(query.getId()));
    }

    /**
     * 根据id查询
     *
     * @param query id
     *
     * @return 单条
     */
    @Override
    public List<T> queryByIds(IdsQuery query) {
        List<Long> ids = query.getIds();
        List<T> result = null;
        try {
            result = getService().query(ids.stream().map(Identifier::new).collect(Collectors.toList()));
        } catch (Exception e) {
            LogUtil.error(this, e);
        }
        return result;
    }

    @Override
    public Long add(AddCommand<T> addCommand) {
        return getService().add(addCommand.getDto());
    }

    @Override
    public Integer change(ChangeCommand<T> changeCommand) {
        return getService().update(changeCommand.getDto(), changeCommand.getOrder().getArgs());
    }

    @Override
    public Integer remove(RemoveCommand removeCommand) {
        BaseArgQuery order = removeCommand.getOrder();
        return getService().remove(order.getArgs());
    }

    @Override
    public Integer remove(IdCommand id) {
        return getService().remove(new Identifier(id.getId()));
    }

    @Override
    public Long count(BlackQuery order) {
        return getService().count(order.getArgs());
    }

    /**
     * 获取service
     *
     * @return
     */
    protected abstract BaseDoService<T> getService();
}
