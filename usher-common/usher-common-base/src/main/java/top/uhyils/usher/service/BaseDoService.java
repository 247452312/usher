package top.uhyils.usher.service;

import java.util.List;
import top.uhyils.usher.pojo.DTO.base.IdDTO;
import top.uhyils.usher.pojo.DTO.base.Page;
import top.uhyils.usher.pojo.cqe.query.demo.Arg;
import top.uhyils.usher.pojo.cqe.query.demo.Limit;
import top.uhyils.usher.pojo.cqe.query.demo.Order;

/**
 * 代表有数据库对象映射的service,带有一些默认方法
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月25日 20时35分
 */
public interface BaseDoService<E extends IdDTO> extends BaseService {

    /**
     * 插入
     *
     * @param addCommand
     *
     * @return
     */
    Long add(E addCommand);

    /**
     * 根据id 删除
     *
     * @param id
     *
     * @return
     */
    Integer remove(Long id);

    /**
     * 根据条件删除
     *
     * @param args
     *
     * @return
     */
    Integer remove(List<Arg> args);

    /**
     * 查询
     *
     * @param order
     */
    Page<E> query(List<Arg> args, Order order, Limit limit);

    /**
     * 查询
     *
     * @param ids
     */
    List<E> query(List<Long> ids);

    /**
     * 查询
     *
     * @param args
     * @param order
     */
    List<E> queryNoPage(List<Arg> args, Order order);

    /**
     * 查询
     *
     * @param args
     */
    List<E> queryNoPage(List<Arg> args);

    /**
     * 根据id获取DTO
     *
     * @param id id
     *
     * @return
     */
    E query(Long id);

    /**
     * 修改
     *
     * @param changeCommand
     * @param args
     *
     * @return
     */
    Integer update(E changeCommand, List<Arg> args);


    /**
     * 数量
     *
     * @param args
     *
     * @return
     */
    Long count(List<Arg> args);
}
