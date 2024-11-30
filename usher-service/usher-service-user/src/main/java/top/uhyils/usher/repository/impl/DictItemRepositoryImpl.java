package top.uhyils.usher.repository.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import top.uhyils.usher.annotation.Repository;
import top.uhyils.usher.assembler.DictItemAssembler;
import top.uhyils.usher.dao.DictItemDao;
import top.uhyils.usher.pojo.DO.DictItemDO;
import top.uhyils.usher.pojo.DTO.DictItemDTO;
import top.uhyils.usher.pojo.DTO.base.Page;
import top.uhyils.usher.pojo.cqe.query.demo.Arg;
import top.uhyils.usher.pojo.cqe.query.demo.Limit;
import top.uhyils.usher.pojo.cqe.query.demo.Order;
import top.uhyils.usher.pojo.entity.Dict;
import top.uhyils.usher.pojo.entity.DictItem;
import top.uhyils.usher.repository.DictItemRepository;
import top.uhyils.usher.repository.base.AbstractRepository;
import top.uhyils.usher.util.Asserts;


/**
 * 数据字典子项(DictItem)表 仓库实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月27日 08时32分42秒
 */
@Repository
public class DictItemRepositoryImpl extends AbstractRepository<DictItem, DictItemDO, DictItemDao, DictItemDTO, DictItemAssembler> implements DictItemRepository {

    protected DictItemRepositoryImpl(DictItemAssembler convert, DictItemDao dao) {
        super(convert, dao);
    }


    @Override
    public List<DictItem> findItemByDictId(Dict dictId) {
        ArrayList<DictItemDO> byDictId = dao.getByDictId(dictId.getUnique().orElseThrow(() -> Asserts.makeException("字典项查询失败")));
        return byDictId.stream().map(assembler::toEntity).collect(Collectors.toList());
    }

    @Override
    public List<DictItem> findItemByDictCode(String itemCode) {
        ArrayList<DictItemDO> byCode = dao.getByCode(itemCode);
        return byCode.stream().map(assembler::toEntity).collect(Collectors.toList());
    }

    @Override
    public Page<DictItem> find(Long dictId, List<Arg> args, Order order, Limit limit) {
        return null;
    }

}
