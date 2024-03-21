package team.opentech.usher.core;


import java.util.Random;
import org.junit.jupiter.api.Test;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年03月19日 14时45分
 */
class AbstractIndividualTest {


    @Test
    public void testRandom() {
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            System.out.printf(random.nextInt(10) + " ");
        }
    }
}
