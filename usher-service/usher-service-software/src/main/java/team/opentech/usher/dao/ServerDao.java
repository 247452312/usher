package team.opentech.usher.dao;

import team.opentech.usher.dao.base.DefaultDao;
import team.opentech.usher.pojo.DO.ServerDO;
import java.util.ArrayList;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年06月12日 13时34分
 */
@Mapper
public interface ServerDao extends DefaultDao<ServerDO> {

    /**
     * 获取所有的服务器信息
     *
     * @return 所有服务器信息(只有id和name)
     */
    ArrayList<ServerDO> getServersIdAndName();

    /**
     * 根据服务器id获取名称
     *
     * @param id id
     *
     * @return 名称
     */
    String getNameById(Long id);
}
