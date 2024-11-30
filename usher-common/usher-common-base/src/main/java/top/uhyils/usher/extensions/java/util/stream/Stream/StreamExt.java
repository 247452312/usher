package top.uhyils.usher.extensions.java.util.stream.Stream;

import java.util.stream.Stream;
import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.This;
import top.uhyils.usher.ustream.UStream;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年11月28日 11时25分
 */
@Extension
public class StreamExt {

    public static <T> UStream<T> ustream(@This Stream<T> stream) {
        return stream::forEach;
    }
}
