package team.opentech.usher.dao;

import team.opentech.usher.dao.base.DefaultDao;
import team.opentech.usher.pojo.DO.BlackListDO;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

/**
 * (BlackList)表 数据库访问层
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年08月24日 06时40分49秒
 */
@Mapper
public interface BlackListDao extends DefaultDao<BlackListDO> {

    /**
     * 获取所有的ip黑名单
     *
     * @return ip黑名单
     */
    List<String> getAllIpBlackList();

}
