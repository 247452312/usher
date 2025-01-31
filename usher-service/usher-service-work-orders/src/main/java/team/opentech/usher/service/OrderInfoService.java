package team.opentech.usher.service;


import team.opentech.usher.pojo.DTO.InitOrderDTO;
import team.opentech.usher.pojo.DTO.OrderInfoDTO;
import team.opentech.usher.pojo.cqe.command.CommitOrderCommand;
import team.opentech.usher.pojo.cqe.command.FrozenOrderCommand;
import team.opentech.usher.pojo.cqe.command.IdCommand;
import team.opentech.usher.pojo.cqe.command.RecallOrderCommand;
import team.opentech.usher.pojo.cqe.command.RestartOrderCommand;
import team.opentech.usher.pojo.cqe.event.AgreeRecallOrderEvent;
import team.opentech.usher.pojo.cqe.event.ApprovalOrderEvent;
import team.opentech.usher.pojo.cqe.query.GetAllOrderQuery;
import java.util.List;

/**
 * 工单基础信息样例表(OrderInfo)表 内部服务接口
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月31日 19时59分14秒
 */
public interface OrderInfoService extends BaseDoService<OrderInfoDTO> {

    /**
     * 复制基础工单到工单
     *
     * @param request 工单本体
     *
     * @return 插入后的id
     */
    InitOrderDTO initOrder(IdCommand request);

    /**
     * 根据类型获取全部工单
     *
     * @param request
     *
     * @return
     */
    List<OrderInfoDTO> getAllOrder(GetAllOrderQuery request);


    /**
     * 提交工单
     *
     * @param request 提交工单请求
     *
     * @return 工单
     */
    Boolean commitOrder(CommitOrderCommand request);

    /**
     * 撤回工单
     *
     * @param request
     *
     * @return 是否发送信息到审批人成功
     */
    Boolean recallOrder(RecallOrderCommand request);


    /**
     * 同意撤回工单
     *
     * @param request
     *
     * @return
     */
    Boolean agreeRecallOrder(AgreeRecallOrderEvent request);


    /**
     * 冻结工单(审批人才能操作)
     *
     * @param request
     *
     * @return
     */
    Boolean frozenOrder(FrozenOrderCommand request);

    /**
     * 重启工单(对应冻结)
     *
     * @param request
     *
     * @return
     */
    Boolean restartOrder(RestartOrderCommand request);


    /**
     * 审批转移工单
     *
     * @param request
     *
     * @return
     */
    Boolean approvalOrder(ApprovalOrderEvent request);

}
