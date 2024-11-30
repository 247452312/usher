package top.uhyils.usher.pojo.cqe;

import top.uhyils.usher.pojo.cqe.command.base.AbstractCommand;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年11月21日 08时39分
 */
public class ChangeDeviceCommand extends AbstractCommand {

    private Long id;

    /**
     * 设备名称
     */
    private String name;

    /**
     * 所属子空间
     */
    private Long subspaceId;


    /**
     * 设备类型 1-传感器 2-控制器
     */
    private Integer type;

    /**
     * 子类型 详情见枚举
     */
    private Integer subtype;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Integer getSubtype() {
        return subtype;
    }

    public void setSubtype(Integer subtype) {
        this.subtype = subtype;
    }
}
