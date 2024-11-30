package top.uhyils.usher.pojo.entity.type;

import java.util.List;

/**
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年09月19日 13时47分
 */
public class Identifiers implements BaseType, Comparable<Identifiers> {

    private List<Long> ids;

    public Identifiers(List<Long> ids) {
        this.ids = ids;
    }

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }

    @Override
    public int compareTo(Identifiers o) {
        if (o == null || o.ids == null) {
            return -1;
        }
        if (ids.size() > o.ids.size()) {
            return 1;
        } else if (ids.size() < o.ids.size()) {
            return -1;
        } else {
            long sum = ids.stream().mapToLong(t -> t).sum();
            long oSum = o.ids.stream().mapToLong(t -> t).sum();
            return Long.compare(sum, oSum);
        }

    }
}
