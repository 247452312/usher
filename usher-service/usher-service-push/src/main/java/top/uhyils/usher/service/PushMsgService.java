package top.uhyils.usher.service;


import top.uhyils.usher.pojo.DTO.PushMsgDTO;
import top.uhyils.usher.pojo.DTO.request.CronRequest;
import top.uhyils.usher.pojo.DTO.request.PushMsgToSomeoneRequest;

/**
 * 推送日志表(PushMsg)表 内部服务接口
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年09月02日 19时47分04秒
 */
public interface PushMsgService extends BaseDoService<PushMsgDTO> {


    /**
     * 定时任务触发指定cron的推送
     *
     * @param request cron请求
     *
     * @return 是否成功
     */
    Boolean push(CronRequest request);

    /**
     * 主动向某人推送一条消息
     *
     * @param request
     *
     * @return
     */
    Boolean pushMsgToSomeone(PushMsgToSomeoneRequest request);

}
