package top.uhyils.usher.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.uhyils.usher.annotation.ReadWriteMark;
import top.uhyils.usher.assembler.PushMsgAssembler;
import top.uhyils.usher.enums.PushTypeEnum;
import top.uhyils.usher.facade.UserFacade;
import top.uhyils.usher.pojo.DO.PushMsgDO;
import top.uhyils.usher.pojo.DTO.PushMsgDTO;
import top.uhyils.usher.pojo.DTO.UserDTO;
import top.uhyils.usher.pojo.DTO.request.CronRequest;
import top.uhyils.usher.pojo.DTO.request.PushMsgToSomeoneRequest;
import top.uhyils.usher.pojo.entity.AllApiGroup;
import top.uhyils.usher.pojo.entity.ApiSubscribe;
import top.uhyils.usher.pojo.entity.PushMsg;
import top.uhyils.usher.repository.ApiGroupRepository;
import top.uhyils.usher.repository.ApiRepository;
import top.uhyils.usher.repository.ApiSubscribeRepository;
import top.uhyils.usher.repository.PushMsgRepository;
import top.uhyils.usher.service.PushMsgService;
import top.uhyils.usher.util.Asserts;
import top.uhyils.usher.util.LogUtil;

/**
 * 推送日志表(PushMsg)表 内部服务实现类
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年09月02日 19时47分05秒
 */
@Service
@ReadWriteMark(tables = {"sys_push_msg"})
public class PushMsgServiceImpl extends AbstractDoService<PushMsgDO, PushMsg, PushMsgDTO, PushMsgRepository, PushMsgAssembler> implements PushMsgService {

    @Autowired
    private ApiGroupRepository apiGroupRepository;

    @Autowired
    private ApiRepository apiRepository;

    @Autowired
    private ApiSubscribeRepository subscribeRepository;

    @Autowired
    private UserFacade userFacade;

    @Autowired
    private PushMsgRepository msgRepository;

    @Autowired
    private PushMsgAssembler msgAssembler;

    public PushMsgServiceImpl(PushMsgAssembler assembler, PushMsgRepository repository) {
        super(assembler, repository);
    }

    @Override
    public Boolean push(CronRequest request) {
        AllApiGroup apiGroups = new AllApiGroup(apiGroupRepository);
        apiGroups.fillApi(apiRepository);
        apiGroups.fillSubscribe(subscribeRepository, request.getCron());
        apiGroups.sendMsgToUser(userFacade, msgRepository, msgAssembler);
        LogUtil.info(this, "定时推送任务结束: " + request.getCron());
        return true;
    }

    @Override
    public Boolean pushMsgToSomeone(PushMsgToSomeoneRequest request) {
        PushTypeEnum type = PushTypeEnum.prase(request.getType());
        Asserts.assertTrue(type != null, "类型不正确");
        UserDTO user = userFacade.getById(request.getUserId());
        ApiSubscribe apiSubscribe = new ApiSubscribe();
        PushMsgDTO pushMsgDTO = apiSubscribe.sendMsg(user, request.getTitle(), request.getMsg(), type);
        PushMsg pushMsg = msgAssembler.toEntity(pushMsgDTO);
        msgRepository.save(pushMsg);
        return true;
    }
}
