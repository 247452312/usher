package top.uhyils.usher.dao;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Mapper;
import top.uhyils.usher.dao.base.DefaultDao;
import top.uhyils.usher.pojo.DO.OrderBaseInfoDO;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年11月09日 10时11分
 */
@Mapper
public interface OrderBaseInfoDao extends DefaultDao<OrderBaseInfoDO> {


    /**
     * 获取全部的基础工单
     *
     * @return
     */
    ArrayList<OrderBaseInfoDO> getAllBaseOrderIdAndName();
}
