package top.uhyils.usher.pojo.DTO;

import org.apache.commons.lang3.builder.ToStringBuilder;
import top.uhyils.usher.pojo.DTO.base.IdDTO;

/**
 * 设备指令表表(AiDeviceInstruction)表 对外数据传输载体
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2024年11月13日 20时55分
 */
public class AiDeviceInstructionDTO extends IdDTO {

    private static final long serialVersionUID = -1L;

    /**
     * 设备指令编号
     */
    private String deviceInstructionNo;

    /**
     * 指令内容
     */
    private String context;

    /**
     * 指令唯一标识
     */
    private String uniqueMark;

    /**
     * 所属设备
     */
    private Long deviceId;

    /**
     * 指令注释
     */
    private String notes;

    public String getUniqueMark() {
        return uniqueMark;
    }

    public void setUniqueMark(String uniqueMark) {
        this.uniqueMark = uniqueMark;
    }

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

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .append("deviceInstructionNo", getDeviceInstructionNo())
            .append("context", getContext())
            .append("deviceId", getDeviceId())
            .append("notes", getNotes())
            .toString();
    }

}
