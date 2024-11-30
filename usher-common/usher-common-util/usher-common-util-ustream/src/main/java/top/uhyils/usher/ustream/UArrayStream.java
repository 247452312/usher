package top.uhyils.usher.ustream;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年11月28日 09时01分
 */
public class UArrayStream<T> extends ArrayList<T> implements UStream<T> {

    @Override
    public void consume(Consumer<T> consumer) {
        forEach(consumer);
    }

    @Override
    public Stream<T> stream() {
        return UStream.super.stream();
    }
}
