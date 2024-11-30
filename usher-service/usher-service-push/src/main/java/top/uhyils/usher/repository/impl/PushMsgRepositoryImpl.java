package top.uhyils.usher.repository.impl;

import top.uhyils.usher.annotation.Repository;
import top.uhyils.usher.assembler.PushMsgAssembler;
import top.uhyils.usher.dao.PushMsgDao;
import top.uhyils.usher.pojo.DO.PushMsgDO;
import top.uhyils.usher.pojo.DTO.PushMsgDTO;
import top.uhyils.usher.pojo.entity.PushMsg;
import top.uhyils.usher.repository.PushMsgRepository;
import top.uhyils.usher.repository.base.AbstractRepository;


/**
 * 推送日志表(PushMsg)表 仓库实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年09月02日 19时47分04秒
 */
@Repository
public class PushMsgRepositoryImpl extends AbstractRepository<PushMsg, PushMsgDO, PushMsgDao, PushMsgDTO, PushMsgAssembler> implements PushMsgRepository {

    protected PushMsgRepositoryImpl(PushMsgAssembler convert, PushMsgDao dao) {
        super(convert, dao);
    }


}
