package team.opentech.usher.pojo.entity;

import team.opentech.usher.annotation.Default;
import team.opentech.usher.pojo.DO.PushPageMsgDO;
import team.opentech.usher.pojo.entity.base.AbstractDoEntity;
import team.opentech.usher.repository.PushPageMsgRepository;

/**
 * 推送日志信息表(PushPageMsg)表 数据库实体类
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2021年09月02日 19时47分08秒
 */
public class PushPageMsg extends AbstractDoEntity<PushPageMsgDO> {

    @Default
    public PushPageMsg(PushPageMsgDO data) {
        super(data);
    }

    public PushPageMsg(Long id) {
        super(id, new PushPageMsgDO());
    }

    public PushPageMsg(Long id, PushPageMsgRepository rep) {
        super(id, new PushPageMsgDO());
        completion(rep);
    }

}
