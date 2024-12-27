package top.uhyils.usher.pojo.entity;

import top.uhyils.usher.annotation.Default;
import top.uhyils.usher.pojo.DO.TraceInfoDO;
import top.uhyils.usher.pojo.entity.base.AbstractDoEntity;

/**
 * (TraceInfo)表 数据库实体类
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2021年08月29日 16时58分55秒
 */
public class TraceInfo extends AbstractDoEntity<TraceInfoDO> {

    @Default
    public TraceInfo(TraceInfoDO data) {
        super(data);
    }

}
