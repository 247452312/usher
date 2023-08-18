package team.opentech.usher.repository.impl;

import team.opentech.usher.annotation.Repository;
import team.opentech.usher.assembler.PushMsgAssembler;
import team.opentech.usher.dao.PushMsgDao;
import team.opentech.usher.pojo.DO.PushMsgDO;
import team.opentech.usher.pojo.DTO.PushMsgDTO;
import team.opentech.usher.pojo.entity.PushMsg;
import team.opentech.usher.repository.PushMsgRepository;
import team.opentech.usher.repository.base.AbstractRepository;


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
