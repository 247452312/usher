package team.opentech.usher.pojo.entity;

import team.opentech.usher.annotation.Default;
import team.opentech.usher.pojo.DO.TraceDetailDO;
import team.opentech.usher.pojo.entity.base.AbstractDoEntity;

/**
 * (TraceDetail)表 数据库实体类
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2021年08月29日 16时58分54秒
 */
public class TraceDetail extends AbstractDoEntity<TraceDetailDO> {

    @Default
    public TraceDetail(TraceDetailDO data) {
        super(data);
    }

}
