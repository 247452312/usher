package top.uhyils.usher.dao;

import org.apache.ibatis.annotations.Mapper;
import top.uhyils.usher.dao.base.DefaultDao;
import top.uhyils.usher.pojo.DO.AiSpaceUserLinkDO;


/**
 * 独立空间-用户关联表(AiSpaceUserLink)表 mapper
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2024年11月13日 09时38分
 */
@Mapper
public interface AiSpaceUserLinkDao extends DefaultDao<AiSpaceUserLinkDO> {

}
