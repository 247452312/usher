package team.opentech.usher.repository.impl;

import team.opentech.usher.annotation.Repository;
import team.opentech.usher.assembler.PushPageMsgAssembler;
import team.opentech.usher.dao.PushPageMsgDao;
import team.opentech.usher.pojo.DO.PushPageMsgDO;
import team.opentech.usher.pojo.DTO.PushPageMsgDTO;
import team.opentech.usher.pojo.entity.PushPageMsg;
import team.opentech.usher.repository.PushPageMsgRepository;
import team.opentech.usher.repository.base.AbstractRepository;


/**
 * 推送日志信息表(PushPageMsg)表 仓库实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年09月02日 19时47分09秒
 */
@Repository
public class PushPageMsgRepositoryImpl extends AbstractRepository<PushPageMsg, PushPageMsgDO, PushPageMsgDao, PushPageMsgDTO, PushPageMsgAssembler> implements PushPageMsgRepository {

    protected PushPageMsgRepositoryImpl(PushPageMsgAssembler convert, PushPageMsgDao dao) {
        super(convert, dao);
    }


}
