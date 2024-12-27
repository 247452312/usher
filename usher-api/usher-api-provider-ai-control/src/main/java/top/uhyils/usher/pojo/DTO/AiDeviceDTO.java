package top.uhyils.usher.pojo.DTO;

import org.apache.commons.lang3.builder.ToStringBuilder;
import top.uhyils.usher.pojo.DTO.base.IdDTO;

/**
 * 设备表表(AiDevice)表 对外数据传输载体
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2024年11月13日 20时55分
 */
public class AiDeviceDTO extends IdDTO {

    private static final long serialVersionUID = -1L;

    /**
     * 设备编号
     */
    private String deviceNo;

    /**
     * 设备唯一标示
     */
    private String uniqueMark;

    /**
     * 设备名称
     */
    private String name;

    /**
     * 所属子空间
     */
    private Long subspaceId;

    /**
     * 设备类型 1-传感器 2-控制器 3-混合
     */
    private Integer type;

    /**
     * 链接类型 具体见枚举
     */
    private Integer linkType;

    /**
     * 链接所需内容
     */
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
