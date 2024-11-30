package top.uhyils.usher.pojo.entity;

import java.util.List;
import java.util.Objects;
import top.uhyils.usher.annotation.Default;
import top.uhyils.usher.enums.PushTypeEnum;
import top.uhyils.usher.pojo.DO.ApiGroupDO;
import top.uhyils.usher.pojo.DO.ApiSubscribeDO;
import top.uhyils.usher.pojo.DTO.PushMsgDTO;
import top.uhyils.usher.pojo.DTO.UserDTO;
import top.uhyils.usher.pojo.entity.base.AbstractDoEntity;
import top.uhyils.usher.repository.ApiSubscribeRepository;
import top.uhyils.usher.util.Asserts;
import top.uhyils.usher.util.CollectionUtil;
import top.uhyils.usher.util.PushUtils;

/**
 * api订阅表(ApiSubscribe)表 数据库实体类
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2021年09月02日 19时46分53秒
 */
public class ApiSubscribe extends AbstractDoEntity<ApiSubscribeDO> {

    @Default
    public ApiSubscribe(ApiSubscribeDO data) {
        super(data);
    }

    public ApiSubscribe() {
        super(new ApiSubscribeDO());
    }

    public ApiSubscribe(Long id) {
        super(id, new ApiSubscribeDO());
    }

    public ApiSubscribe(Long id, ApiSubscribeRepository rep) {
        super(id, new ApiSubscribeDO());
        completion(rep);
    }

    public void checkRepeat(ApiSubscribeRepository rep) {
        List<ApiSubscribe> subscribes = rep.findByGroupAndUserId(toData().map(ApiSubscribeDO::getApiGroupId).orElseThrow(Asserts::throwOptionalException), toData().map(ApiSubscribeDO::getUserId).orElseThrow(Asserts::throwOptionalException));
        Asserts.assertTrue(CollectionUtil.isEmpty(subscribes), "不能同时订阅同一个api两次");
    }

    public PushMsgDTO sendMsg(ApiGroup apiGroup, UserDTO userDTO, String sendContent) {
        switch (Objects.requireNonNull(PushTypeEnum.prase(toData().map(ApiSubscribeDO::getType).orElseThrow(Asserts::throwOptionalException)))) {
            case PAGE:
                return PushUtils.pagePush(userDTO, "my系统,订阅消息-" + apiGroup.toData().map(ApiGroupDO::getName).orElseThrow(Asserts::throwOptionalException), sendContent);
            case EMAIL:
                return PushUtils.emailPush(userDTO, "my系统,订阅邮件-" + apiGroup.toData().map(ApiGroupDO::getName).orElseThrow(Asserts::throwOptionalException), sendContent);
            default:
                break;
        }
        return null;
    }

    public PushMsgDTO sendMsg(UserDTO userDTO, String title, String sendContent, PushTypeEnum pushType) {
        switch (pushType) {
            case PAGE:
                return PushUtils.pagePush(userDTO, title, sendContent);
            case EMAIL:
                return PushUtils.emailPush(userDTO, title, sendContent);
            default:
                break;
        }
        return null;
    }

}
