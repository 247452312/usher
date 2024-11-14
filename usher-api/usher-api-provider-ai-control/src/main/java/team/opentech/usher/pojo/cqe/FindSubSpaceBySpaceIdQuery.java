package team.opentech.usher.pojo.cqe;

import team.opentech.usher.pojo.cqe.query.base.AbstractQuery;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年11月14日 18时33分
 */
public class FindSubSpaceBySpaceIdQuery extends AbstractQuery {


    /**
     * 空间id
     */
    private Long apaceId;

    public Long getApaceId() {
        return apaceId;
    }

    public void setApaceId(Long apaceId) {
        this.apaceId = apaceId;
    }
}
