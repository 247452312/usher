package top.uhyils.usher.pojo.entity;

import top.uhyils.usher.annotation.Default;
import top.uhyils.usher.pojo.DO.PushPageMsgDO;
import top.uhyils.usher.pojo.entity.base.AbstractDoEntity;
import top.uhyils.usher.repository.PushPageMsgRepository;

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
