package top.uhyils.usher.dao;

import org.apache.ibatis.annotations.Mapper;
import top.uhyils.usher.dao.base.DefaultDao;
import top.uhyils.usher.pojo.DO.NetNodeInfoDetailDO;


/**
 * 网络调用独立可工作节点支持的语句类型(NetNodeInfoDetail)表 mapper
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2025年01月10日 15时22分
 */
@Mapper
public interface NetNodeInfoDetailDao extends DefaultDao<NetNodeInfoDetailDO> {

}
