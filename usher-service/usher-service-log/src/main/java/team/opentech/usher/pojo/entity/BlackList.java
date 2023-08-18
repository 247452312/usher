package team.opentech.usher.pojo.entity;

import team.opentech.usher.annotation.Default;
import team.opentech.usher.pojo.DO.BlackListDO;
import team.opentech.usher.pojo.entity.base.AbstractDoEntity;

/**
 * 黑名单(BlackList)表 数据库实体类
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2021年08月29日 16时58分52秒
 */
public class BlackList extends AbstractDoEntity<BlackListDO> {

    @Default
    public BlackList(BlackListDO data) {
        super(data);
    }

}
