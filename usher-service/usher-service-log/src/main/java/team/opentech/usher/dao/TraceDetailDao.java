package team.opentech.usher.dao;

import team.opentech.usher.dao.base.DefaultDao;
import team.opentech.usher.pojo.DO.TraceDetailDO;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2021年08月02日 08时14分
 */
@Mapper
public interface TraceDetailDao extends DefaultDao<TraceDetailDO> {


    /**
     * 获取指定ip用户访问的前size个时间
     *
     * @param ip
     * @param size
     *
     * @return
     */
    List<Long> getTimeByIp(@Param("ip") String ip, @Param("size") Integer size);
}
