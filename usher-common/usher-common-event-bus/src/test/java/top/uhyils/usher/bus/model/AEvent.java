package top.uhyils.usher.bus.model;

import java.util.List;
import top.uhyils.usher.pojo.cqe.event.base.AbstractParentEvent;
import top.uhyils.usher.pojo.cqe.event.base.BaseEvent;

/**
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年10月10日 15时26分
 */
public class AEvent extends AbstractParentEvent {

    @Override
    protected List<BaseEvent> transToBaseEvent0() {
        return null;
    }
}
