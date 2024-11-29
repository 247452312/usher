package team.opentech.usher.ustream;

import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年11月28日 10时58分
 */
public class UGroupStream<K, T> extends UArrayStream<UObjAndArrayStream<K, T>> {

    public UGroupStream(Map<K, UArrayStream<T>> maps) {
        for (Entry<K, UArrayStream<T>> entry : maps.entrySet()) {
            add(new UObjAndArrayStream<>(entry.getKey(), entry.getValue()));
        }
    }

    @Override
    public void consume(Consumer<UObjAndArrayStream<K, T>> consumer) {
        super.consume(consumer);
    }


    public UStream<T> flatMapBySortKey(Comparator<K> comparator) {
        UArrayStream<UObjAndArrayStream<K, T>> list = new UArrayStream<>();
        consume(list::add);
        return list.sorted((o1, o2) -> comparator.compare(o1.getObj(), o2.getObj())).flatMap(t -> t);

    }
}
