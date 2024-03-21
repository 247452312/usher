package team.opentech.usher.core;

import java.util.Properties;
import java.util.Random;
import org.junit.jupiter.api.Test;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年03月20日 10时52分
 */
public class MainTest {


    @Test
    void scenarioMainTest() {
        TestFitnessHandler fitnessHandler = new TestFitnessHandler(8, 6);
        TestPopulation testPopulation = new TestPopulation(new Properties(), fitnessHandler);
        testPopulation.init();
        int lCount = 0;
        float minAbs = 0.005F;
        Random random = new Random();
        for (int i = 0; i < 1000; i++) {
            testPopulation.iteration();
            float x = random.nextFloat();
            float result = testPopulation.findResult(x);
            float realResult = fitnessHandler.findRealResult(x);
            float abs = Math.abs(result - realResult);
            System.out.printf("第%d次迭代, 与目标值差距为:%s%n", i, abs);
            System.out.println();
            if (minAbs > abs) {
                lCount++;
            } else {
                lCount = 0;
            }
            if (lCount > 10) {
                break;
            }
        }
        int i = 1;


    }

}
