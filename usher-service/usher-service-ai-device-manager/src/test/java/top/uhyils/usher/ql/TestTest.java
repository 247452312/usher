package top.uhyils.usher.ql;

import com.ql.util.express.DefaultContext;
import com.ql.util.express.ExpressRunner;
import com.ql.util.express.IExpressContext;
import java.util.Objects;
import java.util.function.Supplier;
import org.junit.jupiter.api.Test;
import top.uhyils.usher.ustream.UStream;
import top.uhyils.usher.util.Asserts;
import top.uhyils.usher.util.LogUtil;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年12月06日 09时42分
 */
class TestTest {

    private static Integer exe(ExpressRunner runner) throws Exception {
        DefaultContext<String, Object> context = new DefaultContext<>();
        context.put("a", 1);
        context.put("b", 2);
        context.put("c", 3);
        String express = "a + b * c";
        return (Integer) runner.execute(express, context, null, true, false);
    }

    private static Integer exe2(ExpressRunner runner) throws Exception {
        DefaultContext<String, Object> context = new DefaultContext<>();
        String express = "sum=0;for(i=0;i<1000;i=i+1){sum=7;};return sum;";
        return (Integer) runner.execute(express, context, null, true, false);
    }

    @Test
    void testSpeed() throws Exception {
        // 结果:
        //for循环内部创建引擎用时:22.624秒
        //for循环外部创建引擎用时:0.009秒
        // 结论: 引擎不可重复创建
        long time1 = System.currentTimeMillis();
        int i1 = 10000;
        for (int i = 0; i < i1; i++) {
            ExpressRunner runner = new ExpressRunner();
            exe(runner);
        }
        long time2 = System.currentTimeMillis();
        System.out.println("for循环内部创建引擎用时:" + (time2 - time1) / 1000.0 + "秒");

        ExpressRunner runner = new ExpressRunner();
        for (int i = 0; i < i1; i++) {
            exe(runner);
        }
        long time3 = System.currentTimeMillis();
        System.out.println("for循环外部创建引擎用时:" + (time3 - time2) / 1000.0 + "秒");

    }

    @Test
    void testConcurrent() throws Exception {
        // 结果:
        // 执行成功
        // stream并发用时:1.451秒
        // for循环外部创建引擎用时:2.435秒
        // 结论: 同一个引擎并发执行没问题 但是疑似对效率影响较大 理论上不需要1.451秒这么多
        int i1 = 10000;
        long time = System.currentTimeMillis();

        ExpressRunner runner = new ExpressRunner();
        UStream<Supplier<Integer>> s = t -> {
            for (int i = 0; i < i1; i++) {
                t.accept(() -> {
                    try {
                        return exe2(runner);
                    } catch (Exception e) {
                        LogUtil.error(this, e);
                        return null;
                    }
                });
            }
        };
        int count = s.parallel().map(Supplier::get).filter(Objects::nonNull).filter(t -> t == 7).count();
        Asserts.assertTrue(count == i1, "并发执行ql表达式");
        long time2 = System.currentTimeMillis();
        System.out.println("stream并发用时:" + (time2 - time) / 1000.0 + "秒");

        for (int i = 0; i < i1; i++) {
            exe2(runner);
        }
        long time3 = System.currentTimeMillis();
        System.out.println("for循环外部创建引擎用时:" + (time3 - time2) / 1000.0 + "秒");
    }

    @Test
    void testConcurrent2() throws Exception {
        // 结果:
        // 执行成功
        // stream并发pool用时:1.105秒
        // for循环pool引擎用时:2.533秒
        // 结论: 池化并发时效率稍高于单引擎
        // 最终结论 池!
        int i1 = 10000;
        long time = System.currentTimeMillis();

        QlPool pool = new QlPool(10);

        UStream<Supplier<Integer>> s = t -> {
            for (int i = 0; i < i1; i++) {
                t.accept(() -> {
                    try {
                        IExpressContext<String, Object> context = new DefaultContext<>();
                        String express = "sum=0;for(i=0;i<1000;i=i+1){sum=7;};return sum;";
                        return (Integer) pool.execute(express, context);
                    } catch (Exception e) {
                        LogUtil.error(this, e);
                        return null;
                    }
                });
            }
        };
        int count = s.parallel().map(Supplier::get).filter(Objects::nonNull).filter(t -> t == 7).count();
        Asserts.assertTrue(count == i1, "并发执行ql表达式");
        long time2 = System.currentTimeMillis();
        System.out.println("stream并发pool用时:" + (time2 - time) / 1000.0 + "秒");

        for (int i = 0; i < i1; i++) {
            IExpressContext<String, Object> context = new DefaultContext<>();
            String express = "sum=0;for(i=0;i<1000;i=i+1){sum=7;};return sum;";
            pool.execute(express, context);
        }
        long time3 = System.currentTimeMillis();
        System.out.println("for循环pool引擎用时:" + (time3 - time2) / 1000.0 + "秒");
    }
}
