package top.uhyils.usher.protocol.rpc;

import top.uhyils.usher.pojo.DTO.DealOrderNodeDTO;
import top.uhyils.usher.pojo.DTO.OrderNodeDTO;
import top.uhyils.usher.pojo.cqe.command.DealOrderNodeCommand;
import top.uhyils.usher.pojo.cqe.command.FailOrderNodeCommand;
import top.uhyils.usher.pojo.cqe.command.IdsCommand;
import top.uhyils.usher.pojo.cqe.command.IncapacityFailOrderNodeCommand;
import top.uhyils.usher.protocol.rpc.base.DTOProvider;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年11月09日 10时11分
 */
public interface OrderNodeProvider extends DTOProvider<OrderNodeDTO> {

    /**
     * 批量删除,删除工单时用
     *
     * @param request
     *
     * @return
     */
    Boolean deleteByIds(IdsCommand request);

    /**
     * 工单节点失败(主动将工单节点置为失败)(处理人员经过核实,客观上不能完成此操作,例:审批时客户填写不合格)
     *
     * @param request
     *
     * @return
     */
    Boolean failOrderNode(FailOrderNodeCommand request);

    /**
     * 处理工单节点
     *
     * @param request
     *
     * @return
     */
    DealOrderNodeDTO dealOrderNode(DealOrderNodeCommand request) throws Exception;

    /**
     * 工单节点(转交)失败(因处理人员无能力完成此节点,申请转交给其他人,则可以进行主动失败)
     *
     * @param request
     *
     * @return
     */
    Boolean incapacityFailOrderNode(IncapacityFailOrderNodeCommand request) throws Exception;
}
