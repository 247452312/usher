package team.opentech.usher.service.impl;

import team.opentech.usher.annotation.ReadWriteMark;
import team.opentech.usher.assembler.PushPageMsgAssembler;
import team.opentech.usher.pojo.DO.PushPageMsgDO;
import team.opentech.usher.pojo.DTO.PushPageMsgDTO;
import team.opentech.usher.pojo.entity.PushPageMsg;
import team.opentech.usher.repository.PushPageMsgRepository;
import team.opentech.usher.service.PushPageMsgService;
import org.springframework.stereotype.Service;

/**
 * 推送日志信息表(PushPageMsg)表 内部服务实现类
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年09月02日 19时47分09秒
 */
@Service
@ReadWriteMark(tables = {"sys_push_page_msg"})
public class PushPageMsgServiceImpl extends AbstractDoService<PushPageMsgDO, PushPageMsg, PushPageMsgDTO, PushPageMsgRepository, PushPageMsgAssembler> implements PushPageMsgService {

    public PushPageMsgServiceImpl(PushPageMsgAssembler assembler, PushPageMsgRepository repository) {
        super(assembler, repository);
    }


}
