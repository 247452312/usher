package team.opentech.usher.pojo.entity;

import team.opentech.usher.annotation.Default;
import team.opentech.usher.mq.content.RocketMqContent;
import team.opentech.usher.mq.pojo.mqinfo.JvmUniqueMark;
import team.opentech.usher.pojo.DO.LogMonitorJvmStatusDO;
import team.opentech.usher.pojo.entity.base.AbstractDoEntity;
import team.opentech.usher.pojo.entity.type.Identifier;
import team.opentech.usher.repository.LogMonitorJvmStatusRepository;
import team.opentech.usher.repository.LogMonitorRepository;
import team.opentech.usher.util.Asserts;

/**
 * JVM状态子表(LogMonitorJvmStatus)表 数据库实体类
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2021年08月29日 16时58分53秒
 */
public class LogMonitorJvmStatus extends AbstractDoEntity<LogMonitorJvmStatusDO> {

    private JvmUniqueMark unique;

    @Default
    public LogMonitorJvmStatus(LogMonitorJvmStatusDO data) {
        super(data);
    }

    public LogMonitorJvmStatus(LogMonitorJvmStatusDO dO, JvmUniqueMark unique) {
        super(dO);
        this.unique = unique;
    }

    /**
     * 修改结束时间为假想时间
     *
     * @param rep
     */
    public void changeEndTimeLag(LogMonitorRepository rep) {
        long realEndTime = (long) (data.getTime() + RocketMqContent.OUT_TIME * 60 * 1000 * RocketMqContent.OUT_TIME_PRO);
        Asserts.assertTrue(data.getFid() != null);
        rep.changeEndTimeLag(this, realEndTime);
    }

    public void fillFid(LogMonitorRepository repository) {
        if (fid() != null) {
            return;
        }
        Asserts.assertTrue(unique != null, "服务状态缺少唯一标示");
        Identifier idByUnique = repository.getIdByUnique(unique);
        data.setFid(idByUnique.getId());

    }

    public Long fid() {
        return data.getFid();
    }

    public void addSelf(LogMonitorJvmStatusRepository rep) {
        rep.save(this);
    }
}
