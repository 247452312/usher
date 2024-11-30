package top.uhyils.usher.pojo.cqe.query;

import java.util.Collections;
import top.uhyils.usher.enums.Symbol;
import top.uhyils.usher.pojo.cqe.query.base.AbstractArgQuery;
import top.uhyils.usher.pojo.cqe.query.demo.Arg;


/**
 * idOrder
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月25日 08时44分
 */
public class IdQuery extends AbstractArgQuery {

    private Long id;

    public IdQuery(Long id) {
        super(Collections.singletonList(Arg.as(IdQuery::getId, Symbol.EQ, id)));
        this.id = id;
    }

    public IdQuery() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
