package top.uhyils.usher.pojo.DTO.cqe.event;

import java.util.List;
import top.uhyils.usher.pojo.cqe.event.base.AbstractParentEvent;
import top.uhyils.usher.pojo.cqe.event.base.BaseEvent;
import top.uhyils.usher.pojo.entity.User;

/**
 * 这个类不要异步发送
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年12月18日 17时36分
 */
public class UserLoginParentEvent extends AbstractParentEvent {

    private User user;

    public UserLoginParentEvent(User user) {
        super();
        this.user = user;
    }

    @Override
    protected List<BaseEvent> transToBaseEvent0() {
        return null;
    }
}
