package top.uhyils.usher.service;


import java.util.List;
import top.uhyils.usher.pojo.DTO.OrderBaseInfoDTO;
import top.uhyils.usher.pojo.cqe.DefaultCQE;
import top.uhyils.usher.pojo.cqe.query.IdQuery;

/**
 * 工单基础信息样例表(OrderBaseInfo)表 内部服务接口
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月31日 19时58分54秒
 */
public interface OrderBaseInfoService extends BaseDoService<OrderBaseInfoDTO> {

    /**
     * 获取全部的基础工单(id与名称)
     *
     * @param request
     *
     * @return
     */
    List<OrderBaseInfoDTO> getAllBaseOrderIdAndName(DefaultCQE request);

    /**
     * 获取一个工单的所有信息
     *
     * @param request
     *
     * @return
     */
    OrderBaseInfoDTO getOneOrder(IdQuery request);


}
