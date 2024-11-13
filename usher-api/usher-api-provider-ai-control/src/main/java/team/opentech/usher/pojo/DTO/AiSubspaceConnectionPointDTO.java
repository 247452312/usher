package team.opentech.usher.pojo.DTO;

import org.apache.commons.lang3.builder.ToStringBuilder;
import team.opentech.usher.pojo.DTO.base.IdDTO;

/**
 * 子空间连通点表(AiSubspaceConnectionPoint)表 对外数据传输载体
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2024年11月13日 09时38分
 */
public class AiSubspaceConnectionPointDTO extends IdDTO {

    private static final long serialVersionUID = -1L;

    private Long spaceId;

    /**
     * 连通点坐标
     */
    private String pointCoordinate;

    public void setSpaceId(Long spaceId) {
        this.spaceId = spaceId;
    }

    public Long getSpaceId() {
        return spaceId;
    }

    public void setPointCoordinate(String pointCoordinate) {
        this.pointCoordinate = pointCoordinate;
    }

    public String getPointCoordinate() {
        return pointCoordinate;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .append("spaceId", getSpaceId())
            .append("pointCoordinate", getPointCoordinate())
            .toString();
    }

}
