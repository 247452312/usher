package team.opentech.usher.repository;

import team.opentech.usher.pojo.DO.ContentDO;
import team.opentech.usher.pojo.entity.Content;
import team.opentech.usher.repository.base.BaseEntityRepository;

/**
 * 公共参数(Content)表 数据仓库层
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月27日 08时32分17秒
 */
public interface ContentRepository extends BaseEntityRepository<ContentDO, Content> {


    /**
     * 根据名称获取
     *
     * @param honeInfo
     *
     * @return
     */
    Content getByName(String honeInfo);
}
