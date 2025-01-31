package team.opentech.usher.dao;

import team.opentech.usher.dao.base.DefaultDao;
import team.opentech.usher.pojo.DO.JobDO;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;


/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年04月25日 13时03分
 */
@Mapper
public interface JobDao extends DefaultDao<JobDO> {

    /**
     * 获取全部
     *
     * @return
     */
    List<JobDO> getAll();

    /**
     * 暂停一个任务
     *
     * @param id
     *
     * @return
     */
    Integer pause(Long id);

    /**
     * 开启
     *
     * @param id id
     *
     * @return 个数
     */
    Integer start(Long id);
}
