package top.uhyils.usher.pojo.cqe.query;

import java.util.List;
import top.uhyils.usher.pojo.cqe.DefaultCQE;
import top.uhyils.usher.pojo.cqe.query.base.BaseQuery;


/**
 * idOrder
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月25日 08时44分
 */
public class IdsQuery extends DefaultCQE implements BaseQuery {

    private List<Long> ids;

    public IdsQuery(List<Long> ids) {
        super();
        this.ids = ids;
    }

    public IdsQuery() {
        super();
    }

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }

}
