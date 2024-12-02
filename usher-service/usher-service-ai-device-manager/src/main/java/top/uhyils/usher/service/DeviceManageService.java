package top.uhyils.usher.service;

import top.uhyils.usher.pojo.cqe.ExecuteInstructionCommand;

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
    Boolean executeInstruction(ExecuteInstructionCommand command);
}
