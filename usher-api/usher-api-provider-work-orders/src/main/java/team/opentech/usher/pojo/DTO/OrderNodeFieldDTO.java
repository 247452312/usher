package team.opentech.usher.pojo.DTO;


import team.opentech.usher.pojo.DTO.base.IdDTO;

/**
 * 工单节点属性样例表(OrderNodeField)表 对外数据传输载体
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月31日 19时59分25秒
 */
public class OrderNodeFieldDTO extends IdDTO {

    private static final long serialVersionUID = -36796391268463397L;


    /**
     * 节点id
     */
    private Long baseOrderNodeId;

    /**
     * 属性名称
     */
    private String name;

    /**
     * 属性备注
     */
    private String desc;

    /**
     * 默认值
     */
    private String defaultValue;

    /**
     * 是否可以为空
     */
    private Integer empty;

    /**
     * 是否可编辑
     */
    private Integer edit;

    /**
     * 字段类型(1->编辑框 2->单选框 3->多选框 4->下拉框 5->文本框)
     */
    private Integer type;

    /**
     * 数值类型 1->字符串 2->数值 3->只允许英文 4->email 5->日期)
     */
    private Integer valueType;

    /**
     * 数据来源
     */
    private Integer dataSources;

    /**
     * 如果是单选,多选,下拉框,那么具体的值关联id
     */
    private Long relationId;


    public Long getBaseOrderNodeId() {
        return baseOrderNodeId;
    }

    public void setBaseOrderNodeId(Long baseOrderNodeId) {
        this.baseOrderNodeId = baseOrderNodeId;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }


    public Integer getEmpty() {
        return empty;
    }

    public void setEmpty(Integer empty) {
        this.empty = empty;
    }


    public Integer getEdit() {
        return edit;
    }

    public void setEdit(Integer edit) {
        this.edit = edit;
    }


    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }


    public Integer getValueType() {
        return valueType;
    }

    public void setValueType(Integer valueType) {
        this.valueType = valueType;
    }


    public Integer getDataSources() {
        return dataSources;
    }

    public void setDataSources(Integer dataSources) {
        this.dataSources = dataSources;
    }


    public Long getRelationId() {
        return relationId;
    }

    public void setRelationId(Long relationId) {
        this.relationId = relationId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OrderNodeFieldDTO that = (OrderNodeFieldDTO) o;

        if (getBaseOrderNodeId() != null ? !getBaseOrderNodeId().equals(that.getBaseOrderNodeId()) : that.getBaseOrderNodeId() != null) {
            return false;
        }
        if (getName() != null ? !getName().equals(that.getName()) : that.getName() != null) {
            return false;
        }
        if (getDesc() != null ? !getDesc().equals(that.getDesc()) : that.getDesc() != null) {
            return false;
        }
        if (getDefaultValue() != null ? !getDefaultValue().equals(that.getDefaultValue()) : that.getDefaultValue() != null) {
            return false;
        }
        if (getEmpty() != null ? !getEmpty().equals(that.getEmpty()) : that.getEmpty() != null) {
            return false;
        }
        if (getEdit() != null ? !getEdit().equals(that.getEdit()) : that.getEdit() != null) {
            return false;
        }
        if (getType() != null ? !getType().equals(that.getType()) : that.getType() != null) {
            return false;
        }
        if (getValueType() != null ? !getValueType().equals(that.getValueType()) : that.getValueType() != null) {
            return false;
        }
        if (getDataSources() != null ? !getDataSources().equals(that.getDataSources()) : that.getDataSources() != null) {
            return false;
        }
        return getRelationId() != null ? getRelationId().equals(that.getRelationId()) : that.getRelationId() == null;
    }

    @Override
    public int hashCode() {
        int result = getBaseOrderNodeId() != null ? getBaseOrderNodeId().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getDesc() != null ? getDesc().hashCode() : 0);
        result = 31 * result + (getDefaultValue() != null ? getDefaultValue().hashCode() : 0);
        result = 31 * result + (getEmpty() != null ? getEmpty().hashCode() : 0);
        result = 31 * result + (getEdit() != null ? getEdit().hashCode() : 0);
        result = 31 * result + (getType() != null ? getType().hashCode() : 0);
        result = 31 * result + (getValueType() != null ? getValueType().hashCode() : 0);
        result = 31 * result + (getDataSources() != null ? getDataSources().hashCode() : 0);
        result = 31 * result + (getRelationId() != null ? getRelationId().hashCode() : 0);
        return result;
    }
}
