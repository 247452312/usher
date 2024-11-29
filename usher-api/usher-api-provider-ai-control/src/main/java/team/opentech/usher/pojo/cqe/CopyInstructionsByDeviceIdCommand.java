package team.opentech.usher.pojo.cqe;

import team.opentech.usher.pojo.cqe.command.base.AbstractCommand;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年11月21日 09时13分
 */
public class CopyInstructionsByDeviceIdCommand extends AbstractCommand {

    /**
     * 目标设备id
     */
    private Long targetDeviceId;


    /**
     * 源设备id
     */
    private Long sourceDeviceId;

    public Long getTargetDeviceId() {
        return targetDeviceId;
    }

    public void setTargetDeviceId(Long targetDeviceId) {
        this.targetDeviceId = targetDeviceId;
    }

    public Long getSourceDeviceId() {
        return sourceDeviceId;
    }

    public void setSourceDeviceId(Long sourceDeviceId) {
        this.sourceDeviceId = sourceDeviceId;
    }
}
