package team.opentech.usher.pojo.cqe;

import java.util.List;
import team.opentech.usher.pojo.DTO.AiSubspaceConnectionPointDTO;
import team.opentech.usher.pojo.DTO.EdgeCoordinaties;
import team.opentech.usher.pojo.DTO.Point3D;
import team.opentech.usher.pojo.cqe.command.base.AbstractCommand;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年11月19日 10时56分
 */
public class ChangeSubSpaceCommand extends AbstractCommand {

    /**
     * 子空间id
     */
    private Long subspaceId;


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

    public Long getSubspaceId() {
        return subspaceId;
    }

    public void setSubspaceId(Long subspaceId) {
        this.subspaceId = subspaceId;
    }

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
}
