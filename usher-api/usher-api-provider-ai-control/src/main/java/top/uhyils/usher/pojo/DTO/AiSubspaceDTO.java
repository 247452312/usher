package top.uhyils.usher.pojo.DTO;

import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import top.uhyils.usher.pojo.DTO.base.IdDTO;

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
     * 边缘坐标,三维坐标,每三个点确定一个面
     */
    private List<EdgeCoordinaties> edgeCoordinates;

    /**
     * 原点相对独立空间原点坐标
     */
    private Point3D originRelativeCoordinates;

    /**
     * 连通点
     */
    private List<AiSubspaceConnectionPointDTO> connectionPointDTOS;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getSpaceId() {
        return spaceId;
    }

    public void setSpaceId(Long spaceId) {
        this.spaceId = spaceId;
    }

    public List<EdgeCoordinaties> getEdgeCoordinates() {
        return edgeCoordinates;
    }

    public void setEdgeCoordinates(List<EdgeCoordinaties> edgeCoordinates) {
        this.edgeCoordinates = edgeCoordinates;
    }

    public Point3D getOriginRelativeCoordinates() {
        return originRelativeCoordinates;
    }

    public void setOriginRelativeCoordinates(Point3D originRelativeCoordinates) {
        this.originRelativeCoordinates = originRelativeCoordinates;
    }

    public List<AiSubspaceConnectionPointDTO> getConnectionPointDTOS() {
        return connectionPointDTOS;
    }

    public void setConnectionPointDTOS(List<AiSubspaceConnectionPointDTO> connectionPointDTOS) {
        this.connectionPointDTOS = connectionPointDTOS;
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
