package top.uhyils.usher.ustream;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年11月26日 16时29分
 */
class UStreamTest {


    @Test
    void peek() {
        UStream<UObject<Integer>> integerUStream = UStream.of(UObject.of(1));
        List<UObject<Integer>> list = integerUStream.peek(t -> t.set(t.get() + 1)).toList();
        UObject<Integer> integerUObject = list.get(0);
        Integer i = integerUObject.get();
        assert i == 2;

        integerUStream = UStream.of(UObject.of(1));
        int count = integerUStream.peek(t -> t.set(t.get() + 1)).count();
        assert count == 1;
        assert integerUStream.toList().get(0).get() == 2;

    }

    @Test
    void filter() {
        UStream<Integer> integerUStream = UStream.of(1, 2, 3, 4);
        List<Integer> list = integerUStream.filter(t -> t > 2).toList();
        assert list.size() == 2;
        assert list.get(0) == 3;
    }

    @Test
    void mapToInt() {
        UStream<Integer> integerUStream = UStream.of(1, 2, 3, 4);
        Integer max = integerUStream.mapToInt(t -> t).max();
        assert max == 4;
    }

    @Test
    void findFirst() {
        UStream<Integer> integerUStream = UStream.of(1, 2, 3, 4);
        Optional<Integer> first = integerUStream.findFirst();
        assert first.isPresent();
        assert first.get() == 1;
    }

    @Test
    void allMatch() {
        UStream<Integer> integerUStream = UStream.of(1, 2, 3, 4);
        Boolean b = integerUStream.allMatch(t -> t > 2);
        assert Objects.equals(b, Boolean.FALSE);
        b = integerUStream.allMatch(t -> t > 0);
        assert Objects.equals(b, Boolean.TRUE);
    }

    @Test
    void anyMatch() {
        UStream<Integer> integerUStream = UStream.of(1, 2, 3, 4);
        Boolean b = integerUStream.anyMatch(t -> t > 2);
        assert Objects.equals(b, Boolean.TRUE);
        b = integerUStream.anyMatch(t -> t < 0);
        assert Objects.equals(b, Boolean.FALSE);
    }

    @Test
    void noneMatch() {
        UStream<Integer> integerUStream = UStream.of(1, 2, 3, 4);
        Boolean b = integerUStream.noneMatch(t -> t > 2);
        assert Objects.equals(b, Boolean.FALSE);
        b = integerUStream.noneMatch(t -> t < 0);
        assert Objects.equals(b, Boolean.TRUE);
    }

    @Test
    void reduce() {
        UStream<Integer> integerUStream = UStream.of(1, 2, 3, 4);
        Integer reduce = integerUStream.reduce(0, Integer::sum);
        assert reduce == 10;
    }

    @Test
    void skip() {
        UStream<Integer> integerUStream = UStream.of(1, 2, 3, 4);
        Integer reduce = integerUStream.skip(1).reduce(0, Integer::sum);
        assert reduce == 9;
    }

    @Test
    void limit() {
        UStream<Integer> integerUStream = UStream.of(1, 2, 3, 4);
        Integer reduce = integerUStream.limit(2).reduce(0, Integer::sum);
        assert reduce == 3;
    }

    @Test
    void max() {
        UStream<Integer> integerUStream = UStream.of(1, 2, 3, 4);
        Integer reduce = integerUStream.max(Integer::compareTo);
        assert reduce == 4;
    }

    @Test
    void min() {
        UStream<Integer> integerUStream = UStream.of(1, 2, 3, 4);
        Integer reduce = integerUStream.min(Integer::compareTo);
        assert reduce == 1;
    }

    @Test
    void sorted() {
        UStream<Integer> integerUStream = UStream.of(2, 1, 4, 3);
        List<Integer> reduce = integerUStream.sorted(Integer::compareTo).toList();
        assert reduce.size() == 4;
        assert reduce.get(0) == 1;
        assert reduce.get(1) == 2;
        assert reduce.get(2) == 3;
        assert reduce.get(3) == 4;
    }

    @Test
    void reverse() {
        UStream<Integer> integerUStream = UStream.of(2, 1, 4, 3);
        List<Integer> reduce = integerUStream.reverse().toList();
        assert reduce.size() == 4;
        assert reduce.get(0) == 3;
        assert reduce.get(1) == 4;
        assert reduce.get(2) == 1;
        assert reduce.get(3) == 2;
    }

    @Test
    void toArray() {
        UStream<Integer> integerUStream = UStream.of(2, 1, 4, 3);
        Object[] array = integerUStream.toArray();
        assert array.length == 4;
        assert Objects.equals(array[0], 2);
        assert Objects.equals(array[1], 1);
        assert Objects.equals(array[2], 4);
        assert Objects.equals(array[3], 3);
    }

    @Test
    void distinct() {
        UStream<Integer> integerUStream = UStream.of(2, 2, 4, 3);
        List<Integer> reduce = integerUStream.distinct().toList();
        assert reduce.size() == 3;
        assert reduce.get(0) == 2;
        assert reduce.get(1) == 4;
        assert reduce.get(2) == 3;
    }

    @Test
    void count() {
        UStream<Integer> integerUStream = UStream.of(2, 2, 4, 3);
        int count = integerUStream.count();
        assert count == 4;
    }

    @Test
    void map() {
        UStream<String> stringUStream = UStream.of("123", "456");
        List<String> collect = stringUStream.map(t -> t.substring(0, 1)).toList();
        assert collect.size() == 2;
        assert Objects.equals(collect.get(0), "1");
        assert Objects.equals(collect.get(1), "4");
    }

    @Test
    void flatMap() {
        UStream<List<String>> target = UStream.of(Arrays.asList("1", "2"), Arrays.asList("3", "4"));
        List<String> list = target.flatMap(UStream::of).toList();
        assert list.size() == 4;
        assert Objects.equals(list.get(0), "1");
        assert Objects.equals(list.get(1), "2");
        assert Objects.equals(list.get(2), "3");
        assert Objects.equals(list.get(3), "4");
    }


    @Test
    void toMap1() {
        UStream<Integer> integerUStream = UStream.of(2, 2, 4, 3);
        Map<Integer, Integer> map = integerUStream.toMap(t -> t, t -> t + 1, (v1, v2) -> v1);
        assert map.containsKey(2);
        assert map.containsKey(3);
        assert map.containsKey(4);
        assert map.size() == 3;
        assert map.get(2) == 3;
    }

    @Test
    void toMap2() {
        UStream<Integer> integerUStream = UStream.of(1, 2, 4, 3);
        Map<Integer, Integer> map = integerUStream.toMap(t -> t, t -> t + 1);
        assert map.containsKey(1);
        assert map.containsKey(2);
        assert map.containsKey(3);
        assert map.containsKey(4);
        assert map.size() == 4;
        assert map.get(2) == 3;
    }

    @Test
    void findAny() {
        List<Integer> list = Arrays.asList(1, 2, 4, 3);
        UStream<Integer> integerUStream = UStream.of(list);
        Optional<Integer> any = integerUStream.findAny();
        assert any.isPresent();
    }

    @Test
    void dropWhile() {
        List<Integer> list = Arrays.asList(1, 2, 4, 3);
        UStream<Integer> integerUStream = UStream.of(list);

        List<Integer> list1 = integerUStream.dropWhile(t -> t < 3).toList();
        assert list1.size() == 2;
        assert list1.get(0) == 4;
        assert list1.get(1) == 3;
    }

    @Test
    void takeWhile() {
        List<Integer> list = Arrays.asList(1, 2, 4, 3);
        UStream<Integer> integerUStream = UStream.of(list);
        List<Integer> list1 = integerUStream.takeWhile(t -> t < 3).toList();
        assert list1.size() == 2;
        assert list1.get(0) == 1;
        assert list1.get(1) == 2;
    }

    @Test
    void filter2() {
        List<Integer> list = Arrays.asList(1, 2, 4, 3);
        UStream<Integer> integerUStream = UStream.of(list);
        List<Integer> list1 = integerUStream.filter((index, t) -> index < 2).toList();
        assert list1.size() == 2;
        assert list1.get(0) == 1;
        assert list1.get(1) == 2;
    }

    @Test
    void zip() {
        List<Integer> list = Arrays.asList(1, 2, 4, 3);
        UStream<Integer> integerUStream = UStream.of(list);
        List<Float> ss = integerUStream.zip(Arrays.asList(1.1F, 1.2F), Float::sum).toList();
        assert ss.size() == 2;
        assert ss.get(0) == 2.1F;
        assert ss.get(1) == 3.2F;
    }

    @Test
    void map2() {
        List<Integer> list = Arrays.asList(1, 2, 4, 3);
        UStream<Integer> integerUStream = UStream.of(list);
        List<Integer> list1 = integerUStream.map((index, t) -> index).toList();
        assert list1.size() == 4;
        assert list1.get(0) == 0;
        assert list1.get(1) == 1;
        assert list1.get(2) == 2;
        assert list1.get(3) == 3;
    }

    @Test
    void mapNotNull() {
        List<Integer> list = Arrays.asList(null, 2, 4, 3);
        UStream<Integer> integerUStream = UStream.of(list);
        List<Integer> list1 = integerUStream.mapNotNull(t -> t).toList();
        assert list1.size() == 3;
        assert list1.get(0) == 2;
        assert list1.get(1) == 4;
        assert list1.get(2) == 3;
    }

    @Test
    void parallel() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 101; i++) {
            list.add(i);
        }
        UStream<Integer> integerUStream = UStream.of(list);
        int sum = integerUStream.parallel().mapToInt(t -> t).sum();
        assert sum == 5050;

    }

    @Test
    void join() {
        List<Integer> list = Arrays.asList(1, 2, 4, 3);
        UStream<Integer> integerUStream = UStream.of(list);
        String join = integerUStream.join(",");
        assert Objects.equals(join, "1,2,4,3");

    }

    @Test
    void stream() {
        List<Integer> list = Arrays.asList(1, 2, 4, 3);
        UStream<Integer> integerUStream = UStream.of(list);
        List<Integer> collect = integerUStream.stream().collect(Collectors.toList());
        assert collect.size() == 4;
        assert collect.get(0) == 1;
        assert collect.get(1) == 2;
        assert collect.get(2) == 4;
        assert collect.get(3) == 3;
    }

    @Test
    void groupBy() {
        List<Integer> list = Arrays.asList(1, 2, 4, 3);
        UStream<Integer> integerUStream = UStream.of(list);
        Map<Boolean, List<Integer>> booleanListMap = integerUStream.groupBy(t -> t < 3);
        assert booleanListMap.size() == 2;
        assert booleanListMap.get(true).size() == 2;
        assert booleanListMap.get(false).size() == 2;
        assert booleanListMap.get(true).get(0) == 1;
        assert booleanListMap.get(true).get(1) == 2;
        assert booleanListMap.get(false).get(0) == 4;
        assert booleanListMap.get(false).get(1) == 3;
    }

    @Test
    void groupByT() {
        List<Integer> list = Arrays.asList(1, 4, 2, 3);
        UStream<Integer> integerUStream = UStream.of(list);
        Map<Integer, UStream<Integer>> uObjAndArrayStreams = integerUStream.groupByToUStream(t -> t < 3 ? 1 : 2);
        List<Integer> keys = new ArrayList<>(uObjAndArrayStreams.keySet());
        keys.sort(Integer::compareTo);
        List<Integer> list1 = UStream.of(keys).flatMap(uObjAndArrayStreams::get).toList();
        assert list1.size() == 4;
        assert list1.get(0) == 1;
        assert list1.get(1) == 2;
        assert list1.get(2) == 4;
        assert list1.get(3) == 3;

    }

}
