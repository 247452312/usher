package top.uhyils.usher.protocol.rpc;

import top.uhyils.usher.pojo.cqe.ExecuteInstructionCommand;
import top.uhyils.usher.protocol.rpc.base.BaseProvider;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年12月02日 16时59分
 */
public interface DeviceManageProvider extends BaseProvider {


    /**
     * 执行指令
     *
     * @param command
     *
     * @return
     */
    Object executeInstruction(ExecuteInstructionCommand command);

}
