package top.uhyils.usher.pool;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import top.uhyils.usher.util.Asserts;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2022年03月31日 15时05分
 */
class AbstractObjectPoolTest {

    @Test
    void getOrCreateObject1() {
        ObjectPool<Student> studentAbstractObjectPool = new AbstractObjectPool<Student>(10, Student.class) {

            @Override
            protected void emptyObj(Student student) {
                student.setId(null);
                student.setName(null);
            }
        };

        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Student student = studentAbstractObjectPool.getOrCreateObject();
            list.add(student.toString());
        }
        for (int i = 0; i < 10; i++) {
            Student student = studentAbstractObjectPool.getOrCreateObject();
            list.add(student.toString());
        }

        long count = list.stream().distinct().count();
        Asserts.assertTrue(count == 20, "");
    }

    @Test
    void getOrCreateObject2() throws InterruptedException {
        ObjectPool<Student> studentAbstractObjectPool = new AbstractObjectPool<Student>(10, Student.class) {

            @Override
            protected void emptyObj(Student student) {
                student.setId(null);
                student.setName(null);
            }
        };

        System.gc();
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Student student = studentAbstractObjectPool.getOrCreateObject();
            list.add(student.toString());
        }
        System.gc();
        Thread.sleep(100L);
        for (int i = 0; i < 10; i++) {
            Student student = studentAbstractObjectPool.getOrCreateObject();
            list.add(student.toString());
        }

        long count = list.stream().distinct().count();
        Asserts.assertTrue(count == 10, "");

    }

}
