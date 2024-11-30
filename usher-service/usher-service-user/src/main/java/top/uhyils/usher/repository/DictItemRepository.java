package top.uhyils.usher.repository;

import java.util.List;
import top.uhyils.usher.pojo.DO.DictItemDO;
import top.uhyils.usher.pojo.DTO.base.Page;
import top.uhyils.usher.pojo.cqe.query.demo.Arg;
import top.uhyils.usher.pojo.cqe.query.demo.Limit;
import top.uhyils.usher.pojo.cqe.query.demo.Order;
import top.uhyils.usher.pojo.entity.Dict;
import top.uhyils.usher.pojo.entity.DictItem;
import top.uhyils.usher.repository.base.BaseEntityRepository;

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
    Page<DictItem> find(Long dictId, List<Arg> args, Order order, Limit limit);

}
