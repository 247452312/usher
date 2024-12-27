package top.uhyils.usher.pojo.entity;

import top.uhyils.usher.annotation.Default;
import top.uhyils.usher.mq.content.RocketMqContent;
import top.uhyils.usher.mq.pojo.mqinfo.JvmUniqueMark;
import top.uhyils.usher.pojo.DO.LogMonitorJvmStatusDO;
import top.uhyils.usher.pojo.entity.base.AbstractDoEntity;
import top.uhyils.usher.repository.LogMonitorRepository;
import top.uhyils.usher.util.Asserts;
import top.uhyils.usher.util.LogUtil;

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
        if (data.getFid() == null) {
            LogUtil.warn("接收到状态消息后未找到正在运行的主类");
            return;
        }
        rep.changeEndTimeLag(this, realEndTime);
    }

    public void fillFid(LogMonitorRepository repository) {
        if (fid() != null) {
            return;
        }
        Asserts.assertTrue(unique != null, "服务状态缺少唯一标示");
        Long idByUnique = repository.getIdByUnique(unique);
        data.setFid(idByUnique);

    }

    public Long fid() {
        return data.getFid();
    }

}
