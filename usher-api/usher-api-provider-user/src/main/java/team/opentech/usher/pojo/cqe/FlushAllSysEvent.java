package team.opentech.usher.pojo.cqe;

import team.opentech.usher.pojo.cqe.event.base.AbstractParentEvent;
import team.opentech.usher.pojo.cqe.event.base.BaseEvent;
import java.util.List;

/**
 * 刷新所有系统参数
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2022年06月17日 16时22分
 */
public class FlushAllSysEvent extends AbstractParentEvent {

    @Override
    protected List<BaseEvent> transToBaseEvent0() {
        return null;
    }
}