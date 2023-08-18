package team.opentech.usher.pojo.entity;

import team.opentech.usher.annotation.Default;
import team.opentech.usher.pojo.DO.TraceLogDO;
import team.opentech.usher.pojo.entity.base.AbstractDoEntity;

/**
 * (TraceLog)表 数据库实体类
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2021年08月29日 16时58分55秒
 */
public class TraceLog extends AbstractDoEntity<TraceLogDO> {

    @Default
    public TraceLog(TraceLogDO data) {
        super(data);
    }

}
