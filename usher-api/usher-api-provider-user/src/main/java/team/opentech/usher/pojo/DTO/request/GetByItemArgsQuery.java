package team.opentech.usher.pojo.DTO.request;

import team.opentech.usher.pojo.cqe.query.base.AbstractArgQuery;

/**
 * 分页查询,多一个字典id
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年06月17日 07时07分
 */
public class GetByItemArgsQuery extends AbstractArgQuery {

    /**
     * 字典id
     */
    private Long dictId;

    public Long getDictId() {
        return dictId;
    }

    public void setDictId(Long dictId) {
        this.dictId = dictId;
    }
}
