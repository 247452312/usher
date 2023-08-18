package team.opentech.usher.service;


import team.opentech.usher.pojo.DTO.ApiDTO;
import team.opentech.usher.pojo.DTO.base.Page;
import team.opentech.usher.pojo.cqe.query.demo.Limit;
import team.opentech.usher.pojo.cqe.query.demo.Order;

/**
 * api表(Api)表 内部服务接口
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年09月02日 19时46分46秒
 */
public interface ApiService extends BaseDoService<ApiDTO> {


    /**
     * 获取所有的指定组下的api
     *
     * @param groupId 组id
     * @param order   排序
     * @param limit   分页
     *
     * @return 所有的指定组下的api
     */
    Page<ApiDTO> getByArgsAndGroup(Long groupId, Order order, Limit limit);

}
