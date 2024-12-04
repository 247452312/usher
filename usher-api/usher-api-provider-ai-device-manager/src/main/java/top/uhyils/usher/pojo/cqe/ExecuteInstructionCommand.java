package top.uhyils.usher.pojo.cqe;

import top.uhyils.usher.pojo.cqe.command.base.AbstractCommand;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年12月02日 17时15分
 */
public class ExecuteInstructionCommand extends AbstractCommand {

    /**
     * 设备指令编号
     */
    private String deviceInstructionNo;

    /**
     * 指令内容
     */
    private String context;

    /**
     * 唯一标示
     */
    private String uniqueMark;

    public String getDeviceInstructionNo() {
        return deviceInstructionNo;
    }

    public void setDeviceInstructionNo(String deviceInstructionNo) {
        this.deviceInstructionNo = deviceInstructionNo;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getUniqueMark() {
        return uniqueMark;
    }

    public void setUniqueMark(String uniqueMark) {
        this.uniqueMark = uniqueMark;
    }
}
