package team.opentech.usher.pojo.cqe.event.base;

import team.opentech.usher.pojo.cqe.DefaultCQE;
import team.opentech.usher.util.IdUtil;
import team.opentech.usher.util.SpringUtil;

/**
 * 抽象事件
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月26日 08时24分
 */
public abstract class AbstractEvent extends DefaultCQE implements BaseEvent {


    protected AbstractEvent() {
        IdUtil bean = SpringUtil.getBean(IdUtil.class);
        if (bean == null) {
            bean = new IdUtil();
        }
        setUnique(bean.newId());
    }


}
