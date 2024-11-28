package team.opentech.usher.ustream;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年11月28日 10时47分
 */
public class UObjAndArrayStream<K, V> extends UArrayStream<V> {

    private final K obj;

    public UObjAndArrayStream(K obj) {
        this.obj = obj;
    }

    public UObjAndArrayStream(K obj, Iterable<V> list) {
        this.obj = obj;
        for (V v : list) {
            add(v);
        }
    }

    public K getObj() {
        return obj;
    }

}
