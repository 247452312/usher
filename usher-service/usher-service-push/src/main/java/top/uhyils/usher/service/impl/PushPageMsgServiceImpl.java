package top.uhyils.usher.service.impl;

import org.springframework.stereotype.Service;
import top.uhyils.usher.annotation.ReadWriteMark;
import top.uhyils.usher.assembler.PushPageMsgAssembler;
import top.uhyils.usher.pojo.DO.PushPageMsgDO;
import top.uhyils.usher.pojo.DTO.PushPageMsgDTO;
import top.uhyils.usher.pojo.entity.PushPageMsg;
import top.uhyils.usher.repository.PushPageMsgRepository;
import top.uhyils.usher.service.PushPageMsgService;

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
