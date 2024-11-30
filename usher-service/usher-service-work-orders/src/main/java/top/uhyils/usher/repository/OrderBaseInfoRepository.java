package top.uhyils.usher.repository;

import java.util.List;
import top.uhyils.usher.pojo.DO.OrderBaseInfoDO;
import top.uhyils.usher.pojo.DTO.OrderBaseInfoDTO;
import top.uhyils.usher.pojo.entity.OrderBaseInfo;
import top.uhyils.usher.repository.base.BaseEntityRepository;

/**
 * 工单基础信息样例表(OrderBaseInfo)表 数据仓库层
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月31日 19时58分53秒
 */
public interface OrderBaseInfoRepository extends BaseEntityRepository<OrderBaseInfoDO, OrderBaseInfo> {


    /**
     * 获取基础工单(只包含内容和名称)
     *
     * @return
     */
    List<OrderBaseInfoDTO> getAllBaseOrderIdAndName();

}
