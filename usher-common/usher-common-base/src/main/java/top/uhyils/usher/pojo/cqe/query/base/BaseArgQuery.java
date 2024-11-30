package top.uhyils.usher.pojo.cqe.query.base;

import java.util.List;
import top.uhyils.usher.pojo.cqe.query.demo.Arg;
import top.uhyils.usher.pojo.cqe.query.demo.Limit;
import top.uhyils.usher.pojo.cqe.query.demo.Order;

/**
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月31日 17时34分
 */
public interface BaseArgQuery extends BaseQuery {

    /**
     * 获取参数
     *
     * @return
     */
    List<Arg> getArgs();


    /**
     * 获取排序
     *
     * @return
     */
    Order getOrder();

    /**
     * 获取分页
     *
     * @return
     */
    Limit getLimit();
}
