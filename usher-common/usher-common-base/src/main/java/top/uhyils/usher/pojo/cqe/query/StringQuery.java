package top.uhyils.usher.pojo.cqe.query;

import top.uhyils.usher.pojo.cqe.query.base.AbstractQuery;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年12月06日 17时27分
 */
public class StringQuery extends AbstractQuery {

    private String value;

    public StringQuery() {
    }

    public StringQuery(String value) {
        this.value = value;
    }

    public static StringQuery build(String uniqueMark) {
        return new StringQuery(uniqueMark);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
