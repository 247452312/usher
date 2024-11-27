package team.opentech.usher.ustream;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 核心流, 其实就是由一个Consumer(操作)构成的懒加载的操作链接
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年11月26日 16时27分
 */
public interface Ustream<T> {


    static <T> Ustream<T> of(T... t) {
        return consumer -> {
            for (T value : t) {
                consumer.accept(value);
            }
        };
    }

    static <T> Ustream<T> of(List<T> t) {
        return t::forEach;
    }

    /**
     * 核心执行 将一个新操作包装到之前的操作之后,也就是最外层
     *
     * @param consumer 对流中每个元素的操作
     */
    void consume(Consumer<T> consumer);

    /**
     * 映射
     *
     * @param function
     * @param <E>
     *
     * @return
     */
    default <E> Ustream<E> map(Function<T, E> function) {
        return t -> consume(e -> t.accept(function.apply(e)));
    }

    /**
     * 映射
     *
     * @param function
     * @param <E>
     *
     * @return
     */
    default <E> Ustream<E> map(BiFunction<Integer, T, E> function) {
        return t -> {
            AtomicInteger index = new AtomicInteger(0);
            consume(e -> t.accept(function.apply(index.getAndIncrement(), e)));
        };
    }

    /**
     * 映射
     *
     * @param <E>
     *
     * @return
     */
    default <E> Ustream<E> mapNotNull(Function<T, E> function) {
        return map(function).filter(Objects::nonNull);
    }

    /**
     * 元素转换为流之后平铺
     *
     * @param function
     *
     * @return
     */
    default <E> Ustream<E> flatMap(Function<T, Ustream<E>> function) {
        return t -> consume(e -> function.apply(e).consume(t));
    }

    /**
     * 流操作, 对每个元素进行操作,返回自身
     *
     * @param consumer
     *
     * @return
     */
    default Ustream<T> peek(Consumer<T> consumer) {
        return t -> consume(e -> {
            consumer.accept(e);
            t.accept(e);
        });
    }

    /**
     * 过滤流
     *
     * @param consumer
     *
     * @return
     */
    default Ustream<T> filter(Function<T, Boolean> consumer) {
        return t -> consume(e -> {
            if (Boolean.TRUE.equals(consumer.apply(e))) {
                t.accept(e);
            }
        });
    }

    /**
     * 合并流
     *
     * @param function
     *
     * @return
     */
    default <E, R> Ustream<R> zip(Iterable<E> iterable, BiFunction<T, E, R> function) {
        return t -> {
            Iterator<E> iterator = iterable.iterator();
            consume(e -> {
                if (iterator.hasNext()) {
                    t.accept(function.apply(e, iterator.next()));
                }
            });
        };
    }

    /**
     * 过滤流
     *
     * @param consumer
     *
     * @return
     */
    default Ustream<T> filter(BiFunction<Integer, T, Boolean> consumer) {
        return t -> {
            AtomicInteger integer = new AtomicInteger(0);
            consume(e -> {
                int andIncrement = integer.getAndIncrement();
                if (Boolean.TRUE.equals(consumer.apply(andIncrement, e))) {
                    t.accept(e);
                }
            });
        };
    }


    /**
     * 顺序返回符合条件的值,一旦条件不符合立即停止继续判断
     *
     * @return
     */
    default Ustream<T> takeWhile(Function<T, Boolean> function) {
        return t -> {
            AtomicBoolean atomicBoolean = new AtomicBoolean(true);
            consume(e -> {
                if (!atomicBoolean.get()) {
                    return;
                }
                if (Boolean.FALSE.equals(function.apply(e))) {
                    atomicBoolean.set(Boolean.FALSE);
                    return;
                }
                t.accept(e);
            });
        };
    }

    /**
     * 顺序删除符合条件的元素,一旦条件不符合立即停止删除,并返回剩余的元素
     *
     * @return
     */
    default Ustream<T> dropWhile(Function<T, Boolean> function) {
        return t -> {
            AtomicBoolean haveNode = new AtomicBoolean(false);
            consume(e -> {
                if (haveNode.get()) {
                    t.accept(e);
                    return;
                }
                if (Boolean.FALSE.equals(function.apply(e))) {
                    haveNode.set(Boolean.TRUE);
                    t.accept(e);
                }
            });
        };
    }


    /**
     * 整形过滤流
     *
     * @param consumer
     *
     * @return
     */
    default IntUstream mapToInt(Function<T, Integer> consumer) {
        return t -> consume(e -> t.accept(consumer.apply(e)));
    }


    /**
     * 获取流中第一个元素
     *
     * @return
     */
    default Optional<T> findFirst() {
        Uobject<T> tempObj = new Uobject<>();
        AtomicInteger i = new AtomicInteger(0);
        consume(t -> {
            if (i.get() == 0) {
                tempObj.set(t);
            }
            i.incrementAndGet();
        });
        return tempObj.opt();
    }


    /**
     * 全部匹配
     *
     * @param function
     *
     * @return
     */
    default Boolean allMatch(Function<T, Boolean> function) {
        AtomicBoolean result = new AtomicBoolean(Boolean.TRUE);
        consume(t -> {
            // 如果不匹配,则停止匹配
            if (Boolean.FALSE.equals(result.get())) {
                return;
            }
            Boolean matchSuccess = function.apply(t);
            if (Boolean.FALSE.equals(matchSuccess)) {
                result.set(Boolean.FALSE);
            }
        });
        return result.get();
    }

    /**
     * 存在匹配
     *
     * @param function
     *
     * @return
     */
    default Boolean anyMatch(Function<T, Boolean> function) {
        AtomicBoolean result = new AtomicBoolean(Boolean.FALSE);
        consume(t -> {
            // 如果有一个匹配,则停止匹配
            if (Boolean.TRUE.equals(result.get())) {
                return;
            }
            Boolean matchSuccess = function.apply(t);
            if (Boolean.TRUE.equals(matchSuccess)) {
                result.set(Boolean.TRUE);
            }
        });
        return result.get();
    }

    /**
     * 不存在匹配
     *
     * @param function
     *
     * @return
     */
    default Boolean noneMatch(Function<T, Boolean> function) {
        AtomicBoolean result = new AtomicBoolean(Boolean.TRUE);
        consume(t -> {
            // 如果有一个匹配,则停止匹配
            if (Boolean.FALSE.equals(result.get())) {
                return;
            }
            Boolean matchSuccess = function.apply(t);
            if (Boolean.TRUE.equals(matchSuccess)) {
                result.set(Boolean.FALSE);
            }
        });
        return result.get();
    }

    /**
     * 合并
     *
     * @param function
     *
     * @return
     */
    default <E> E reduce(E startValue, BiFunction<E, T, E> function) {
        Uobject<E> result = new Uobject<>(startValue);
        consume(t -> result.set(function.apply(result.get(), t)));
        return result.get();
    }

    /**
     * 跳过前N个
     *
     * @return
     */
    default Ustream<T> skip(long n) {
        return t -> {
            AtomicInteger index = new AtomicInteger(0);
            consume(e -> {
                if (index.get() >= n) {
                    t.accept(e);
                }
                index.incrementAndGet();
            });
        };
    }

    /**
     * 取前N个
     *
     * @return
     */
    default Ustream<T> limit(long n) {
        return t -> {
            AtomicInteger index = new AtomicInteger(0);
            consume(e -> {
                if (index.get() < n) {
                    t.accept(e);
                }
                index.incrementAndGet();
            });
        };
    }

    /**
     * 取最大值
     *
     * @return
     */
    default T max(Comparator<T> comparator) {
        Uobject<T> result = new Uobject<>();
        consume(t -> {
            T obj = result.get();
            if (obj == null || comparator.compare(t, obj) > 0) {
                result.set(t);
            }
        });
        return result.get();
    }

    /**
     * 取最小值
     *
     * @return
     */
    default T min(Comparator<T> comparator) {
        Uobject<T> result = new Uobject<>();
        consume(t -> {
            T obj = result.get();
            if (obj == null || comparator.compare(t, obj) < 0) {
                result.set(t);
            }
        });
        return result.get();
    }

    /**
     * 排序流
     *
     * @return
     */
    default Ustream<T> sorted(Comparator<T> comparator) {
        return t -> {
            List<T> list = new ArrayList<>();
            Ustream.this.consume(list::add);
            list.sort(comparator);
            list.forEach(t);
        };
    }

    /**
     * 反向流
     *
     * @return
     */
    default Ustream<T> reverse() {
        return t -> {
            List<T> list = new ArrayList<>();
            Ustream.this.consume(list::add);
            for (int size = list.size() - 1; size >= 0; size--) {
                t.accept(list.get(size));
            }
        };
    }


    /**
     * 结局输出流
     *
     * @return
     */
    default Object[] toArray() {
        return toList().toArray();
    }

    /**
     * 去重流
     *
     * @return
     */
    default Ustream<T> distinct() {
        return t -> {
            List<T> temp = new ArrayList<>();
            consume(e -> {
                if (!temp.contains(e)) {
                    temp.add(e);
                    t.accept(e);
                }
            });
        };
    }

    /**
     * 获取流中任意一个元素
     *
     * @return
     */
    default Optional<T> findAny() {
        return findFirst();
    }


    /**
     * 流元素数量, 注意 这一步结束流不会执行peek等操作
     *
     * @return
     */
    default int count() {
        AtomicInteger count = new AtomicInteger();
        consume(t -> count.incrementAndGet());
        return count.get();
    }

    default List<T> toList() {
        List<T> ts = new ArrayList<>();
        consume(ts::add);
        return ts;
    }
}
