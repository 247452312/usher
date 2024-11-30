package top.uhyils.usher.pojo.DO;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import top.uhyils.usher.pojo.DO.base.BaseDO;

/**
 * 设备指令表(AiDeviceInstruction)表 数据库实体类
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2024年11月13日 20时55分
 */
@TableName(value = "sys_ai_device_instruction")
public class AiDeviceInstructionDO extends BaseDO {

    private static final long serialVersionUID = -1L;

    /**
     * 设备指令编号
     */
    @TableField
    private String deviceInstructionNo;

    /**
     * 指令内容
     */
    @TableField
    private String context;

    /**
     * 所属设备
     */
    @TableField
    private Long deviceId;

    /**
     * 指令注释
     */
    @TableField
    private String notes;


    public void setDeviceInstructionNo(String deviceInstructionNo) {
        this.deviceInstructionNo = deviceInstructionNo;
    }

    public String getDeviceInstructionNo() {
        return deviceInstructionNo;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getContext() {
        return context;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getNotes() {
        return notes;
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
