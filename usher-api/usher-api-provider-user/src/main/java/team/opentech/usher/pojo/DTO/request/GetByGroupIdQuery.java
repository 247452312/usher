package team.opentech.usher.pojo.DTO.request;

import team.opentech.usher.pojo.cqe.DefaultCQE;
import team.opentech.usher.pojo.cqe.query.base.BaseQuery;

/**
 * 根据groupId获取动态代码
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2022年02月14日 19时18分
 */
public class GetByGroupIdQuery extends DefaultCQE implements BaseQuery {

    /**
     * 代码组id
     */
    private Integer groupId;

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }
}
