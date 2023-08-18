package team.opentech.usher.pojo.entity;

import team.opentech.usher.annotation.Default;
import team.opentech.usher.pojo.DO.ContentDO;
import team.opentech.usher.pojo.entity.base.AbstractDoEntity;

/**
 * 公共参数(Content)表 数据库实体类
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2021年08月27日 08时32分20秒
 */
public class Content extends AbstractDoEntity<ContentDO> {

    @Default
    public Content(ContentDO data) {
        super(data);
    }

}
