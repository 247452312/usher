package top.uhyils.usher.pojo.DO;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import top.uhyils.usher.pojo.DO.base.BaseDO;

/**
 * 子空间连通点(AiSubspaceConnectionPoint)表 数据库实体类
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2024年11月13日 09时38分
 */
@TableName(value = "sys_ai_subspace_connection_point")
public class AiSubspaceConnectionPointDO extends BaseDO {

    private static final long serialVersionUID = -1L;

    @TableField
    private Long spaceId;

    /**
     * 连通点坐标
     */
    @TableField
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
