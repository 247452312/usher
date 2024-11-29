package team.opentech.usher.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.opentech.usher.annotation.ReadWriteMark;
import team.opentech.usher.assembler.PushMsgAssembler;
import team.opentech.usher.enums.PushTypeEnum;
import team.opentech.usher.facade.UserFacade;
import team.opentech.usher.pojo.DO.PushMsgDO;
import team.opentech.usher.pojo.DTO.PushMsgDTO;
import team.opentech.usher.pojo.DTO.UserDTO;
import team.opentech.usher.pojo.DTO.request.CronRequest;
import team.opentech.usher.pojo.DTO.request.PushMsgToSomeoneRequest;
import team.opentech.usher.pojo.entity.AllApiGroup;
import team.opentech.usher.pojo.entity.ApiSubscribe;
import team.opentech.usher.pojo.entity.PushMsg;
import team.opentech.usher.repository.ApiGroupRepository;
import team.opentech.usher.repository.ApiRepository;
import team.opentech.usher.repository.ApiSubscribeRepository;
import team.opentech.usher.repository.PushMsgRepository;
import team.opentech.usher.service.PushMsgService;
import team.opentech.usher.util.Asserts;
import team.opentech.usher.util.LogUtil;

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
