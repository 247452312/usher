package top.uhyils.usher.dao;

import org.apache.ibatis.annotations.Mapper;
import top.uhyils.usher.dao.base.DefaultDao;
import top.uhyils.usher.pojo.DO.AiDeviceRealTimeDO;


/**
 * 设备实时状态表(AiDeviceRealTime)表 mapper
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2024年12月06日 13时57分
 */
@Mapper
public interface AiDeviceRealTimeDao extends DefaultDao<AiDeviceRealTimeDO> {

}
