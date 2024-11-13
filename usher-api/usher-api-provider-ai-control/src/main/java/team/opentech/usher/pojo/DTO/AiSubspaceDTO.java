package team.opentech.usher.pojo.DTO;

import org.apache.commons.lang3.builder.ToStringBuilder;
import team.opentech.usher.pojo.DTO.base.IdDTO;

/**
 * 子空间表(AiSubspace)表 对外数据传输载体
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2024年11月13日 09时38分
 */
public class AiSubspaceDTO extends IdDTO {

    private static final long serialVersionUID = -1L;

    /**
     * 名称
     */
    private String name;

    /**
     * 所属独立空间
     */
    private Long spaceId;

    /**
     * 边缘坐标
     */
    private String edgeCoordinates;

    /**
     * 原点相对独立空间原点坐标
     */
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
