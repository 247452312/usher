package top.uhyils.usher.pojo.DO;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import top.uhyils.usher.pojo.DO.base.BaseDO;

/**
 * 子空间(AiSubspace)表 数据库实体类
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2024年11月13日 09时38分
 */
@TableName(value = "sys_ai_subspace")
public class AiSubspaceDO extends BaseDO {

    private static final long serialVersionUID = -1L;

    /**
     * 名称
     */
    @TableField
    private String name;

    /**
     * 所属独立空间
     */
    @TableField
    private Long spaceId;

    /**
     * 边缘坐标,三维坐标,每三个点确定一个面
     */
    @TableField
    private String edgeCoordinates;

    /**
     * 原点相对独立空间原点坐标
     */
    @TableField
    private String originRelativeCoordinates;


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setSpaceId(Long spaceId) {
        this.spaceId = spaceId;
    }

    public Long getSpaceId() {
        return spaceId;
    }

    public void setEdgeCoordinates(String edgeCoordinates) {
        this.edgeCoordinates = edgeCoordinates;
    }

    public String getEdgeCoordinates() {
        return edgeCoordinates;
    }

    public void setOriginRelativeCoordinates(String originRelativeCoordinates) {
        this.originRelativeCoordinates = originRelativeCoordinates;
    }

    public String getOriginRelativeCoordinates() {
        return originRelativeCoordinates;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .append("name", getName())
            .append("spaceId", getSpaceId())
            .append("edgeCoordinates", getEdgeCoordinates())
            .append("originRelativeCoordinates", getOriginRelativeCoordinates())
            .toString();
    }
}
