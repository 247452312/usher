package top.uhyils.usher.pojo.DTO.request;


import top.uhyils.usher.pojo.cqe.query.base.AbstractArgQuery;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年06月30日 09时16分
 */
public class GetByArgsAndGroupQuery extends AbstractArgQuery {

    /**
     * 组id
     */
    private Long groupId;

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }
}
