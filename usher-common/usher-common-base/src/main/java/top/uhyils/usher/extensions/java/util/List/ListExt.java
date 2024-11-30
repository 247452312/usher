package top.uhyils.usher.extensions.java.util.List;

import java.util.List;
import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.This;
import top.uhyils.usher.ustream.UStream;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年11月28日 11时27分
 */
@Extension
public class ListExt {

    public static <E> UStream<E> ustream(@This List<E> list) {
        return UStream.of(list);
    }
}
