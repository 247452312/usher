package team.opentech.usher.bus.model;


import java.util.List;
import team.opentech.usher.pojo.cqe.event.base.AbstractParentEvent;
import team.opentech.usher.pojo.cqe.event.base.BaseEvent;

/**
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年10月10日 15时26分
 */
public class BEvent extends AbstractParentEvent {


    @Override
    protected List<BaseEvent> transToBaseEvent0() {
        return null;
    }
}
