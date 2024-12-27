package top.uhyils.usher.pojo.DO;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
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
     * 指令唯一标识
     */
    @TableField
    private String uniqueMark;

    /**
     * 指令注释
     */
    @TableField
    private String notes;

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

    public String getUniqueMark() {
        return uniqueMark;
    }

    public void setUniqueMark(String uniqueMark) {
        this.uniqueMark = uniqueMark;
    }
}
