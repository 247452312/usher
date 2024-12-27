package top.uhyils.usher.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import top.uhyils.usher.dao.base.DefaultDao;
import top.uhyils.usher.pojo.DO.BlackListDO;

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
