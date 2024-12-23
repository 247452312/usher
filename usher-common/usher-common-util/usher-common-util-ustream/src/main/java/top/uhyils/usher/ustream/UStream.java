package top.uhyils.usher.ustream;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.StringJoiner;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * 核心流, 其实就是由一个Consumer(操作)构成的懒加载的操作链接
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年11月26日 16时27分
 */
public interface UStream<T> {


    static <T> UStream<T> of(T... t) {
        return consumer -> {
            for (T value : t) {
                consumer.accept(value);
            }
        };
    }

    static <E, T> UStream<Entry<E, T>> of(Map<E, T> map) {
        return consumer -> map.entrySet().forEach(consumer);
    }

    static <T> UStream<T> of(List<T> t) {
        return t::forEach;
    }

    static void stop() {
        throw StopException.INSTANCE;
    }

    /**
     * 将UStream 转换为stream
     *
     * @return
     */
    default Stream<T> stream() {
        Iterator<T> iterator = new Iterator<T>() {

            @Override
            public boolean hasNext() {
                throw new NoSuchElementException();
            }

            @Override
            public T next() {
                throw new NoSuchElementException();
            }

            @Override
            public void forEachRemaining(Consumer<? super T> action) {
                consume(action::accept);
            }
        };
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator, Spliterator.ORDERED), false);
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
    default <E> UStream<E> map(Function<T, E> function) {
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
    default <E> UStream<E> map(BiFunction<Integer, T, E> function) {
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
    default <E> UStream<E> mapNotNull(Function<T, E> function) {
        return map(function).filter(Objects::nonNull);
    }

    /**
     * 元素转换为流之后平铺
     *
     * @param function
     *
     * @return
     */
    default <E> UStream<E> flatMap(Function<T, UStream<E>> function) {
        return t -> consume(e -> function.apply(e).consume(t));
    }

    /**
     * 流操作, 对每个元素进行操作,返回自身
     *
     * @param consumer
     *
     * @return
     */
    default UStream<T> peek(Consumer<T> consumer) {
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
    default UStream<T> filter(Function<T, Boolean> consumer) {
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
    default <E, R> UStream<R> zip(Iterable<E> iterable, BiFunction<T, E, R> function) {
        return t -> {
            Iterator<E> iterator = iterable.iterator();
            consumeTillStop(e -> {
                if (iterator.hasNext()) {
                    t.accept(function.apply(e, iterator.next()));
                } else {
                    stop();
                }
            });
        };
    }

    /**
     * 监听截停{@link StopException}
     *
     * @param consumer
     */
    default void consumeTillStop(Consumer<T> consumer) {
        try {
            consume(consumer);
        } catch (StopException ignore) {
        }
    }

    /**
     * 过滤流
     *
     * @param consumer
     *
     * @return
     */
    default UStream<T> filter(BiFunction<Integer, T, Boolean> consumer) {
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
    default UStream<T> takeWhile(Function<T, Boolean> function) {
        return t -> {
            AtomicBoolean atomicBoolean = new AtomicBoolean(true);
            consumeTillStop(e -> {
                if (!atomicBoolean.get()) {
                    stop();
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
     * 顺序返回符合条件的值,一旦条件不符合立即停止继续判断
     *
     * @return
     */
    default UStream<T> takeWhile(BiFunction<Integer, T, Boolean> function) {
        return t -> {
            AtomicBoolean atomicBoolean = new AtomicBoolean(true);
            AtomicInteger integer = new AtomicInteger(0);
            consumeTillStop(e -> {
                if (!atomicBoolean.get()) {
                    stop();
                }
                int andIncrement = integer.getAndIncrement();
                if (Boolean.FALSE.equals(function.apply(andIncrement, e))) {
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
    default UStream<T> dropWhile(Function<T, Boolean> function) {
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
    default IntUStream mapToInt(Function<T, Integer> consumer) {
        return t -> consume(e -> t.accept(consumer.apply(e)));
    }

    /**
     * 获取流中第一个元素
     *
     * @return
     */
    default Optional<T> findFirst() {
        UObject<T> tempObj = new UObject<>();
        AtomicInteger i = new AtomicInteger(0);
        consumeTillStop(t -> {
            i.incrementAndGet();
            if (i.get() == 1) {
                tempObj.set(t);
            } else {
                stop();
            }

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
        consumeTillStop(t -> {
            // 如果有一个匹配,则停止匹配
            if (Boolean.TRUE.equals(result.get())) {
                stop();
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
        consumeTillStop(t -> {
            // 如果有一个匹配,则停止匹配
            if (Boolean.FALSE.equals(result.get())) {
                stop();
            }
            Boolean matchSuccess = function.apply(t);
            if (Boolean.TRUE.equals(matchSuccess)) {
                result.set(Boolean.FALSE);
            }
        });
        return result.get();
    }

    /**
     * 合并所有元素
     *
     * @param function
     *
     * @return
     */
    default <E> E reduce(E startValue, BiFunction<E, T, E> function) {
        UObject<E> result = new UObject<>(startValue);
        consume(t -> result.set(function.apply(result.get(), t)));
        return result.get();
    }

    /**
     * 跳过前N个
     *
     * @return
     */
    default UStream<T> skip(long n) {
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
    default UStream<T> limit(long n) {
        return t -> {
            AtomicInteger index = new AtomicInteger(0);
            consumeTillStop(e -> {
                index.incrementAndGet();
                if (index.get() <= n) {
                    t.accept(e);
                } else {
                    stop();
                }
            });
        };
    }

    /**
     * 并发流
     *
     * @return
     */
    default UStream<T> parallel() {
        ForkJoinPool pool = ForkJoinPool.commonPool();
        return c -> map(t -> pool.submit(() -> c.accept(t))).cache().consume(ForkJoinTask::join);
    }

    /**
     * 将并发流转为顺序流 顺序保持并发前的顺序
     *
     * @return
     */
    default UStream<T> cache() {
        List<T> arraySeq = new ArrayList<>();
        consume(arraySeq::add);
        return UStream.of(arraySeq);
    }

    /**
     * 取最大值
     *
     * @return
     */
    default T max(Comparator<T> comparator) {
        UObject<T> result = new UObject<>();
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
        UObject<T> result = new UObject<>();
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
    default UStream<T> sorted(Comparator<T> comparator) {
        return t -> {
            List<T> list = new ArrayList<>();
            UStream.this.consume(list::add);
            list.sort(comparator);
            list.forEach(t);
        };
    }

    /**
     * 分组
     *
     * @return
     */
    default <E> Map<E, List<T>> groupBy(Function<T, E> comparator) {
        Map<E, List<T>> result = new HashMap<>();
        consume(t -> {
            E key = comparator.apply(t);
            if (!result.containsKey(key)) {
                result.put(key, new ArrayList<>());
            }
            result.get(key).add(t);
        });
        return result;
    }

    /**
     * 分组
     *
     * @return
     */
    default <E> Map<E, UStream<T>> groupByToUStream(Function<T, E> function) {
        Map<E, List<UStream<T>>> consumers = new HashMap<>();

        consume(t -> {
            E key = function.apply(t);
            if (!consumers.containsKey(key)) {
                consumers.put(key, new ArrayList<>());
            }
            consumers.get(key).add(consumer -> consumer.accept(t));
        });
        Map<E, UStream<T>> result = new HashMap<>();
        for (E key : consumers.keySet()) {
            List<UStream<T>> uStreams = consumers.get(key);
            UStream<T> tuStream = UStream.of(uStreams).flatMap(t -> t);
            result.put(key, tuStream);
        }

        return result;
    }


    /**
     * 反向流
     *
     * @return
     */
    default UStream<T> reverse() {
        return t -> {
            List<T> list = new ArrayList<>();
            UStream.this.consume(list::add);
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
    default UStream<T> distinct() {
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

    default <K, V> Map<K, V> toMap(Function<T, K> keyMap, Function<T, V> valueMap, BiFunction<V, V, V> chose) {
        Map<K, V> map = new HashMap<>();
        consume(e -> {
            K key = keyMap.apply(e);
            V value = valueMap.apply(e);
            if (map.containsKey(key)) {
                V target = chose.apply(map.get(key), value);
                map.put(key, target);
            } else {
                map.put(key, value);
            }
        });
        return map;
    }

    default <K, V> Map<K, V> toMap(Function<T, K> keyMap, Function<T, V> valueMap) {
        Map<K, V> map = new HashMap<>();
        consume(e -> {
            K key = keyMap.apply(e);
            V value = valueMap.apply(e);
            if (map.containsKey(key)) {
                throw new RuntimeException("错误,ustream toMap时key不能重复");
            } else {
                map.put(key, value);
            }
        });
        return map;
    }

    /**
     * join
     *
     * @param charSequence
     *
     * @return
     */
    default String join(CharSequence charSequence) {
        return join(charSequence, T::toString);
    }

    /**
     * join
     *
     * @return
     */
    default String join() {
        return join("", T::toString);
    }

    /**
     * join
     *
     * @param charSequence
     *
     * @return
     */
    default String join(CharSequence charSequence, Function<T, String> function) {
        StringJoiner joiner = new StringJoiner(charSequence);
        consume(t -> joiner.add(function.apply(t)));
        return joiner.toString();
    }
}
