package top.uhyils.usher.extensions.java.util.Map;

import java.util.Map;
import java.util.function.Supplier;
import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.This;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年11月25日 13时55分
 */
@Extension
public class MapExt {

    public static <K, V> V getOrPutAndGet(@This Map<K, V> thiz, K key, Supplier<V> value) {
        if (thiz.containsKey(key)) {
            return thiz.get(key);
        }
        thiz.put(key, value.get());
        return thiz.get(key);
    }

}
