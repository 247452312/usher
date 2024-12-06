package top.uhyils.usher.pojo.DO;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import top.uhyils.usher.pojo.DO.base.BaseDO;

/**
 * 设备表(AiDevice)表 数据库实体类
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2024年11月13日 20时55分
 */
@TableName(value = "sys_ai_device")
public class AiDeviceDO extends BaseDO {

    private static final long serialVersionUID = -1L;

    /**
     * 设备编号
     */
    @TableField
    private String deviceNo;


    /**
     * 设备唯一标示
     */
    @TableField
    private String uniqueMark;

    /**
     * 设备名称
     */
    @TableField
    private String name;

    /**
     * 所属子空间
     */
    @TableField
    private Long subspaceId;

    /**
     * 设备类型 1-传感器 2-控制器
     */
    @TableField
    private Integer type;

    /**
     * 链接类型 具体见枚举
     */
    @TableField
    private Integer linkType;

    /**
     * 链接所需内容
     */
    @TableField
    private String linkContent;


    public String getUniqueMark() {
        return uniqueMark;
    }

    public void setUniqueMark(String uniqueMark) {
        this.uniqueMark = uniqueMark;
    }


    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getSubspaceId() {
        return subspaceId;
    }

    public void setSubspaceId(Long subspaceId) {
        this.subspaceId = subspaceId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getLinkType() {
        return linkType;
    }

    public void setLinkType(Integer linkType) {
        this.linkType = linkType;
    }

    public String getLinkContent() {
        return linkContent;
    }

    public void setLinkContent(String linkContent) {
        this.linkContent = linkContent;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .append("deviceNo", getDeviceNo())
            .append("name", getName())
            .append("subspaceId", getSubspaceId())
            .append("type", getType())
            .toString();
    }
}
