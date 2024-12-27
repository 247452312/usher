package top.uhyils.usher.pojo.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import top.uhyils.usher.annotation.Default;
import top.uhyils.usher.context.LoginInfoHelper;
import top.uhyils.usher.facade.UserFacade;
import top.uhyils.usher.pojo.DO.ApiGroupDO;
import top.uhyils.usher.pojo.DO.ApiSubscribeDO;
import top.uhyils.usher.pojo.DTO.PushMsgDTO;
import top.uhyils.usher.pojo.DTO.UserDTO;
import top.uhyils.usher.pojo.DTO.base.IdDTO;
import top.uhyils.usher.pojo.entity.base.AbstractDoEntity;
import top.uhyils.usher.repository.ApiGroupRepository;
import top.uhyils.usher.repository.ApiRepository;
import top.uhyils.usher.repository.ApiSubscribeRepository;
import top.uhyils.usher.util.ApiUtils;
import top.uhyils.usher.util.Asserts;

/**
 * api组表(ApiGroup)表 数据库实体类
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2021年09月02日 19时46分49秒
 */
public class ApiGroup extends AbstractDoEntity<ApiGroupDO> {

    private List<Api> apis;

    private List<ApiSubscribe> subscribes;

    @Default
    public ApiGroup(ApiGroupDO data) {
        super(data);
    }

    public ApiGroup(Long id) {
        super(id, new ApiGroupDO());
    }

    public ApiGroup(Long id, ApiGroupRepository rep) {
        super(id, new ApiGroupDO());
        completion(rep);
    }


    public void fillApi(ApiRepository apiRepository) {
        if (apis != null) {
            return;
        }
        this.apis = apiRepository.findByGroupId(getUnique().orElseThrow(() -> Asserts.makeException("未找到唯一标识")));
    }

    public void forceFillSubscribe(List<ApiSubscribe> subscribes) {
        Asserts.assertTrue(subscribes != null, "入参list不能为null");
        this.subscribes = subscribes;
    }

    public void fillSubscribe(ApiSubscribeRepository repository) {
        if (subscribes != null) {
            return;
        }
        this.subscribes = repository.findByGroupId(getUnique().orElseThrow(Asserts::throwOptionalException));
    }

    public void forceFillApi(List<Api> apis) {
        this.apis = apis;
    }

    public String callApi() {
        return callApi(LoginInfoHelper.doGet());
    }

    public String callApi(UserDTO user) {
        Asserts.assertTrue(this.apis != null, "api为空");
        Map<String, String> parameter = new HashMap<>(16);
        ApiUtils.callApi(apis, user, parameter);
        String resultFormat = toData().map(ApiGroupDO::getResultFormat).orElseThrow(Asserts::throwOptionalException);
        resultFormat = ApiUtils.replaceString(parameter, resultFormat);
        return resultFormat;
    }

    public void removeSelf(ApiGroupRepository rep) {
        rep.remove(getUnique().orElseThrow(Asserts::throwOptionalException));
    }

    public Integer removeApis(ApiRepository rep) {
        return rep.removeApiByGroup(this);
    }

    public List<PushMsgDTO> sendMsgToUser(UserFacade userFacade) {
        Asserts.assertTrue(subscribes != null, "没有初始化订阅用户");
        List<UserDTO> byIds = userFacade.getByIds(subscribes.stream().map(t -> t.getUnique().orElseThrow(Asserts::throwOptionalException)).collect(Collectors.toList()));
        return sendMsgToUser(byIds);
    }

    public List<PushMsgDTO> sendMsgToUser(List<UserDTO> users) {
        Map<Long, UserDTO> idUserMap = users.stream().collect(Collectors.toMap(IdDTO::getId, t -> t));

        List<PushMsgDTO> msgList = new ArrayList<>(subscribes.size());
        for (ApiSubscribe subscribe : subscribes) {

            ApiSubscribeDO apiSubscribeDO = subscribe.toData().orElseThrow(Asserts::throwOptionalException);
            Long userId = apiSubscribeDO.getUserId();
            if (!idUserMap.containsKey(userId)) {
                continue;
            }
            UserDTO userDTO = idUserMap.get(userId);
            String sendContent = callApi(userDTO);

            PushMsgDTO pushMsgDTO = subscribe.sendMsg(this, userDTO, sendContent);
            if (pushMsgDTO != null) {
                msgList.add(pushMsgDTO);
            }
        }
        return msgList;

    }
}
