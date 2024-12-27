package top.uhyils.usher.dao;

import org.apache.ibatis.annotations.Mapper;
import top.uhyils.usher.dao.base.DefaultDao;
import top.uhyils.usher.pojo.DO.DictDO;


/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年04月25日 13时03分
 */
@Mapper
public interface DictDao extends DefaultDao<DictDO> {


    /**
     * 根据字典code获取字典id
     *
     * @param code code
     *
     * @return
     */
    Long getIdByCode(String code);
}
