package top.uhyils.usher.service;

import top.uhyils.usher.pojo.cqe.ExecuteInstructionCommand;
import top.uhyils.usher.pojo.cqe.FindMsgCommand;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年12月02日 17时23分
 */
public interface DeviceManageService extends BaseService {

    /**
     * 执行指令
     *
     * @param command
     *
     * @return
     */
    Object executeInstruction(ExecuteInstructionCommand command);

    /**
     * 主动从某个传感器上获取数据
     *
     * @param command
     *
     * @return
     */
    Object findMsg(FindMsgCommand command);
}
