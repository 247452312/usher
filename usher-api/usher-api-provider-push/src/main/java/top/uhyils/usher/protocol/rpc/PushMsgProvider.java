package top.uhyils.usher.protocol.rpc;

import top.uhyils.usher.pojo.DTO.PushMsgDTO;
import top.uhyils.usher.pojo.DTO.request.CronRequest;
import top.uhyils.usher.pojo.DTO.request.PushMsgToSomeoneRequest;
import top.uhyils.usher.protocol.rpc.base.DTOProvider;

/**
 * 推送日志表(PushMsg)表 Rpc对外访问层
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年09月02日 19时47分05秒
 */
public interface PushMsgProvider extends DTOProvider<PushMsgDTO> {


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

