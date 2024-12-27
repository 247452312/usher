package top.uhyils.usher.dao;

import org.apache.ibatis.annotations.Mapper;
import top.uhyils.usher.dao.base.DefaultDao;
import top.uhyils.usher.pojo.DO.RelegationDO;

/**
 * 接口降级策略(Relegation)表 数据库访问层
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年09月27日 09时33分22秒
 */
@Mapper
public interface RelegationDao extends DefaultDao<RelegationDO> {


}
