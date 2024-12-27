package top.uhyils.usher.protocol.rpc;

import java.util.List;
import top.uhyils.usher.pojo.DTO.OrderBaseInfoDTO;
import top.uhyils.usher.pojo.cqe.DefaultCQE;
import top.uhyils.usher.pojo.cqe.query.IdQuery;
import top.uhyils.usher.protocol.rpc.base.DTOProvider;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年11月09日 10时11分
 */
public interface OrderBaseInfoProvider extends DTOProvider<OrderBaseInfoDTO> {

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
