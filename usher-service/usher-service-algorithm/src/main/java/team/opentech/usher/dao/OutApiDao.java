package team.opentech.usher.dao;

import team.opentech.usher.dao.base.DefaultDao;
import team.opentech.usher.pojo.DO.OutApiDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 开放api(OutApi)表 数据库访问层
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年09月09日 20时58分10秒
 */
@Mapper
public interface OutApiDao extends DefaultDao<OutApiDO> {


}
