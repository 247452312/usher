package team.opentech.usher.ustream;


import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.junit.jupiter.api.Test;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年11月26日 16时29分
 */
class UstreamTest {


    @Test
    void peek() {
        Ustream<Uobject<Integer>> integerUstream = Ustream.of(Uobject.of(1));
        List<Uobject<Integer>> list = integerUstream.peek(t -> t.set(t.get() + 1)).toList();
        Uobject<Integer> integerUobject = list.get(0);
        Integer i = integerUobject.get();
        assert i == 2;

        integerUstream = Ustream.of(Uobject.of(1));
        int count = integerUstream.peek(t -> t.set(t.get() + 1)).count();
        assert count == 1;
        assert integerUstream.toList().get(0).get() == 2;

    }

    @Test
    void filter() {
        Ustream<Integer> integerUstream = Ustream.of(1, 2, 3, 4);
        List<Integer> list = integerUstream.filter(t -> t > 2).toList();
        assert list.size() == 2;
        assert list.get(0) == 3;
    }

    @Test
    void mapToInt() {
        Ustream<Integer> integerUstream = Ustream.of(1, 2, 3, 4);
        Integer max = integerUstream.mapToInt(t -> t).max();
        assert max == 4;
    }

    @Test
    void findFirst() {
        Ustream<Integer> integerUstream = Ustream.of(1, 2, 3, 4);
        Optional<Integer> first = integerUstream.findFirst();
        assert first.isPresent();
        assert first.get() == 1;
    }

    @Test
    void allMatch() {
        Ustream<Integer> integerUstream = Ustream.of(1, 2, 3, 4);
        Boolean b = integerUstream.allMatch(t -> t > 2);
        assert Objects.equals(b, Boolean.FALSE);
        b = integerUstream.allMatch(t -> t > 0);
        assert Objects.equals(b, Boolean.TRUE);
    }

    @Test
    void anyMatch() {
        Ustream<Integer> integerUstream = Ustream.of(1, 2, 3, 4);
        Boolean b = integerUstream.anyMatch(t -> t > 2);
        assert Objects.equals(b, Boolean.TRUE);
        b = integerUstream.anyMatch(t -> t < 0);
        assert Objects.equals(b, Boolean.FALSE);
    }

    @Test
    void noneMatch() {
        Ustream<Integer> integerUstream = Ustream.of(1, 2, 3, 4);
        Boolean b = integerUstream.noneMatch(t -> t > 2);
        assert Objects.equals(b, Boolean.FALSE);
        b = integerUstream.noneMatch(t -> t < 0);
        assert Objects.equals(b, Boolean.TRUE);
    }

    @Test
    void reduce() {
        Ustream<Integer> integerUstream = Ustream.of(1, 2, 3, 4);
        Integer reduce = integerUstream.reduce(0, Integer::sum);
        assert reduce == 10;
    }

    @Test
    void skip() {
        Ustream<Integer> integerUstream = Ustream.of(1, 2, 3, 4);
        Integer reduce = integerUstream.skip(1).reduce(0, Integer::sum);
        assert reduce == 9;
    }

    @Test
    void limit() {
        Ustream<Integer> integerUstream = Ustream.of(1, 2, 3, 4);
        Integer reduce = integerUstream.limit(2).reduce(0, Integer::sum);
        assert reduce == 3;
    }

    @Test
    void max() {
        Ustream<Integer> integerUstream = Ustream.of(1, 2, 3, 4);
        Integer reduce = integerUstream.max(Integer::compareTo);
        assert reduce == 4;
    }

    @Test
    void min() {
        Ustream<Integer> integerUstream = Ustream.of(1, 2, 3, 4);
        Integer reduce = integerUstream.min(Integer::compareTo);
        assert reduce == 1;
    }

    @Test
    void sorted() {
        Ustream<Integer> integerUstream = Ustream.of(2, 1, 4, 3);
        List<Integer> reduce = integerUstream.sorted(Integer::compareTo).toList();
        assert reduce.size() == 4;
        assert reduce.get(0) == 1;
        assert reduce.get(1) == 2;
        assert reduce.get(2) == 3;
        assert reduce.get(3) == 4;
    }

    @Test
    void reverse() {
        Ustream<Integer> integerUstream = Ustream.of(2, 1, 4, 3);
        List<Integer> reduce = integerUstream.reverse().toList();
        assert reduce.size() == 4;
        assert reduce.get(0) == 3;
        assert reduce.get(1) == 4;
        assert reduce.get(2) == 1;
        assert reduce.get(3) == 2;
    }

    @Test
    void toArray() {
        Ustream<Integer> integerUstream = Ustream.of(2, 1, 4, 3);
        Object[] array = integerUstream.toArray();
        assert array.length == 4;
        assert Objects.equals(array[0], 2);
        assert Objects.equals(array[1], 1);
        assert Objects.equals(array[2], 4);
        assert Objects.equals(array[3], 3);
    }

    @Test
    void distinct() {
        Ustream<Integer> integerUstream = Ustream.of(2, 2, 4, 3);
        List<Integer> reduce = integerUstream.distinct().toList();
        assert reduce.size() == 3;
        assert reduce.get(0) == 2;
        assert reduce.get(1) == 4;
        assert reduce.get(2) == 3;
    }

    @Test
    void count() {
        Ustream<Integer> integerUstream = Ustream.of(2, 2, 4, 3);
        int count = integerUstream.count();
        assert count == 4;
    }

    @Test
    void map() {
        Ustream<String> stringUstream = Ustream.of("123", "456");
        List<String> collect = stringUstream.map(t -> t.substring(0, 1)).toList();
        assert collect.size() == 2;
        assert Objects.equals(collect.get(0), "1");
        assert Objects.equals(collect.get(1), "4");
    }

    @Test
    void flatMap() {
        Ustream<List<String>> target = Ustream.of(Arrays.asList("1", "2"), Arrays.asList("3", "4"));
        List<String> list = target.flatMap(Ustream::of).toList();
        assert list.size() == 4;
        assert Objects.equals(list.get(0), "1");
        assert Objects.equals(list.get(1), "2");
        assert Objects.equals(list.get(2), "3");
        assert Objects.equals(list.get(3), "4");

    }
}
