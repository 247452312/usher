package team.opentech.usher.pojo.cqe;

import team.opentech.usher.pojo.cqe.command.base.AbstractCommand;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年11月21日 09时13分
 */
public class CreateDeviceInstructionCommand extends AbstractCommand {

    /**
     * 指令内容
     */
    private String context;

    /**
     * 所属设备
     */
    private Long deviceId;

    /**
     * 指令注释
     */
    private String notes;

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
