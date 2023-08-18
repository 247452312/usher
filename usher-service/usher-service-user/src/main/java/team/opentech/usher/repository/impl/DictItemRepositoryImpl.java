package team.opentech.usher.repository.impl;

import team.opentech.usher.annotation.Repository;
import team.opentech.usher.assembler.DictItemAssembler;
import team.opentech.usher.dao.DictItemDao;
import team.opentech.usher.pojo.DO.DictItemDO;
import team.opentech.usher.pojo.DTO.DictItemDTO;
import team.opentech.usher.pojo.DTO.base.Page;
import team.opentech.usher.pojo.cqe.query.demo.Arg;
import team.opentech.usher.pojo.cqe.query.demo.Limit;
import team.opentech.usher.pojo.cqe.query.demo.Order;
import team.opentech.usher.pojo.entity.Dict;
import team.opentech.usher.pojo.entity.DictItem;
import team.opentech.usher.pojo.entity.type.Identifier;
import team.opentech.usher.repository.DictItemRepository;
import team.opentech.usher.repository.base.AbstractRepository;
import team.opentech.usher.util.Asserts;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


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
        ArrayList<DictItemDO> byDictId = dao.getByDictId(dictId.getUnique().map(Identifier::getId).orElseThrow(() -> Asserts.makeException("字典项查询失败")));
        return byDictId.stream().map(assembler::toEntity).collect(Collectors.toList());
    }

    @Override
    public List<DictItem> findItemByDictCode(String itemCode) {
        ArrayList<DictItemDO> byCode = dao.getByCode(itemCode);
        return byCode.stream().map(assembler::toEntity).collect(Collectors.toList());
    }

    @Override
    public Page<DictItem> find(Identifier dictId, List<Arg> args, Order order, Limit limit) {
        return null;
    }

}
