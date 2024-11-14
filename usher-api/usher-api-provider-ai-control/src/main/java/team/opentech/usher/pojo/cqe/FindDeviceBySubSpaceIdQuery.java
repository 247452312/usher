package team.opentech.usher.pojo.cqe;

import team.opentech.usher.pojo.cqe.query.base.AbstractQuery;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年11月14日 18时37分
 */
public class FindDeviceBySubSpaceIdQuery extends AbstractQuery {

    /**
     * 空间id
     */
    private Long spaceId;


    public Long getSpaceId() {
        return spaceId;
    }

    public void setSpaceId(Long spaceId) {
        this.spaceId = spaceId;
    }
}
