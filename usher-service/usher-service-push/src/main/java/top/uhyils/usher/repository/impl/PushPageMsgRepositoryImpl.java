package top.uhyils.usher.repository.impl;

import top.uhyils.usher.annotation.Repository;
import top.uhyils.usher.assembler.PushPageMsgAssembler;
import top.uhyils.usher.dao.PushPageMsgDao;
import top.uhyils.usher.pojo.DO.PushPageMsgDO;
import top.uhyils.usher.pojo.DTO.PushPageMsgDTO;
import top.uhyils.usher.pojo.entity.PushPageMsg;
import top.uhyils.usher.repository.PushPageMsgRepository;
import top.uhyils.usher.repository.base.AbstractRepository;


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
