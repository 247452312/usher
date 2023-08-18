package team.opentech.usher.repository;

import team.opentech.usher.pojo.DO.DictItemDO;
import team.opentech.usher.pojo.DTO.base.Page;
import team.opentech.usher.pojo.cqe.query.demo.Arg;
import team.opentech.usher.pojo.cqe.query.demo.Limit;
import team.opentech.usher.pojo.cqe.query.demo.Order;
import team.opentech.usher.pojo.entity.Dict;
import team.opentech.usher.pojo.entity.DictItem;
import team.opentech.usher.pojo.entity.type.Identifier;
import team.opentech.usher.repository.base.BaseEntityRepository;
import java.util.List;

/**
 * 数据字典子项(DictItem)表 数据仓库层
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月27日 08时32分42秒
 */
public interface DictItemRepository extends BaseEntityRepository<DictItemDO, DictItem> {


    /**
     * 根据dictId获取子项
     *
     * @param dictId
     *
     * @return
     */
    List<DictItem> findItemByDictId(Dict dictId);

    /**
     * 根据itemCode获取所有item
     *
     * @param itemCode
     *
     * @return
     */
    List<DictItem> findItemByDictCode(String itemCode);

    /**
     * 根据条件查询
     *
     * @param dictId
     * @param args
     * @param order
     * @param limit
     *
     * @return
     */
    Page<DictItem> find(Identifier dictId, List<Arg> args, Order order, Limit limit);

}
