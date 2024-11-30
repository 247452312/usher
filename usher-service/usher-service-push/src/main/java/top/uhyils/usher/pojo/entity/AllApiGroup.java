package top.uhyils.usher.pojo.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import top.uhyils.usher.assembler.PushMsgAssembler;
import top.uhyils.usher.facade.UserFacade;
import top.uhyils.usher.pojo.DO.ApiDO;
import top.uhyils.usher.pojo.DO.ApiSubscribeDO;
import top.uhyils.usher.pojo.DTO.PushMsgDTO;
import top.uhyils.usher.pojo.entity.base.AbstractEntity;
import top.uhyils.usher.repository.ApiGroupRepository;
import top.uhyils.usher.repository.ApiRepository;
import top.uhyils.usher.repository.ApiSubscribeRepository;
import top.uhyils.usher.repository.PushMsgRepository;
import top.uhyils.usher.util.Asserts;

/**
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年09月03日 08时32分
 */
public class AllApiGroup extends AbstractEntity {

    private final List<ApiGroup> groups;


    public AllApiGroup(ApiGroupRepository repository) {
        this.groups = repository.findAll();
    }

    public void fillApi(ApiRepository apiRepository) {
        List<Api> apis = apiRepository.findAll();
        Map<Long, List<Api>> groupIdApiMap = apis.stream().collect(Collectors.groupingBy(t -> t.toData().map(ApiDO::getApiGroupId).orElseThrow(Asserts::throwOptionalException)));
        for (ApiGroup group : groups) {
            if (!groupIdApiMap.containsKey(group.getUnique().orElseThrow(Asserts::throwOptionalException))) {
                continue;
            }
            List<Api> groupApis = groupIdApiMap.get(group.getUnique().orElseThrow(Asserts::throwOptionalException));
            group.forceFillApi(groupApis);
        }
    }

    public void fillSubscribe(ApiSubscribeRepository subscribeRepository, String cron) {
        List<ApiSubscribe> subscribes = subscribeRepository.findByCron(cron);
        Map<Long, List<ApiSubscribe>> groupIdSubscribeMap = subscribes.stream().collect(Collectors.groupingBy(t -> t.toData().map(ApiSubscribeDO::getApiGroupId).orElseThrow(Asserts::throwOptionalException)));
        for (ApiGroup group : groups) {
            if (!groupIdSubscribeMap.containsKey(group.getUnique().orElseThrow(Asserts::throwOptionalException))) {
                continue;
            }
            List<ApiSubscribe> groupSubscribes = groupIdSubscribeMap.get(group.getUnique().orElseThrow(Asserts::throwOptionalException));
            group.forceFillSubscribe(groupSubscribes);
        }
    }

    public void sendMsgToUser(UserFacade userFacade, PushMsgRepository msgRepository, PushMsgAssembler msgAssembler) {
        List<PushMsgDTO> pushMsgDTOS = sendMsgToUser(userFacade);
        List<PushMsg> pushMsgs = msgAssembler.listDTOToEntity(pushMsgDTOS);
        msgRepository.save(pushMsgs);
    }

    public List<PushMsgDTO> sendMsgToUser(UserFacade userFacade) {
        List<PushMsgDTO> result = new ArrayList<>();
        for (ApiGroup group : groups) {
            List<PushMsgDTO> pushMsgDTOS = group.sendMsgToUser(userFacade);
            result.addAll(pushMsgDTOS);
        }
        return result;
    }
}
