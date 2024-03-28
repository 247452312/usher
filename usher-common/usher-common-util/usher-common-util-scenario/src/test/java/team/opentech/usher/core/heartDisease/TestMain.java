package team.opentech.usher.core.heartDisease;

import java.io.FileNotFoundException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import team.opentech.usher.FitnessHandler;
import team.opentech.usher.Individual;
import team.opentech.usher.annotation.NotNull;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年03月26日 09时27分
 */
public class TestMain {


    @NotNull
    private static List<Float[]> getFileData(String path) throws FileNotFoundException {
        DataReader dataReader = new DataReader();
        String fileData = dataReader.read(path);
        String[] person = fileData.split("\\n");

        List<Float[]> result = new ArrayList<>();
        for (String s : person) {
            String[] split = s.trim().split(" ");
            if (split.length < 66) {
                break;
            }
            Float[] item = new Float[15];
            item[0] = Float.valueOf(split[0].trim());
            item[1] = Float.valueOf(split[0].trim());
            item[2] = Float.valueOf(split[0].trim());
            item[3] = Float.valueOf(split[0].trim());
            item[4] = Float.valueOf(split[0].trim());
            item[5] = Float.valueOf(split[0].trim());
            item[6] = Float.valueOf(split[0].trim());
            item[7] = Float.valueOf(split[0].trim());
            item[8] = Float.valueOf(split[0].trim());
            item[9] = Float.valueOf(split[0].trim());
            item[10] = Float.valueOf(split[0].trim());
            item[11] = Float.valueOf(split[0].trim());
            item[12] = Float.valueOf(split[0].trim());
            item[13] = Float.valueOf(split[0].trim());
            item[14] = Float.valueOf(split[0].trim());
            result.add(item);
        }
        return result;
    }

    @Test
    void testMain() throws FileNotFoundException {
        List<Float[]> fileData = getFileData("C:\\Users\\Lenovo\\Downloads\\heart+disease\\new.data");
        // 数据清洗
        fileData = fileData.stream().filter(this::filter).collect(Collectors.toList());
        Map<Float[], Float> transDataMap = new HashMap<>();
        for (Float[] fileDatum : fileData) {
            transDataMap.put(fileDatum, fileDatum[fileDatum.length - 1]);
        }
        Float[][] testData = transDataMap.keySet().toArray(new Float[0][]);

        FitnessHandler<Float[], Float> fitnessHandler = new TestHeartHistoryDataFunctionFitnessHandler(transDataMap);

        TestHeartFunctionPopulation testPopulation = new TestHeartFunctionPopulation(new Properties(), fitnessHandler);
        testPopulation.init();
        int lCount = 0;
        float minAbs = 0.0005F;
        NumberFormat instance = NumberFormat.getInstance();
        instance.setMaximumFractionDigits(8);
        for (int i = 0; i < 1000; i++) {
            testPopulation.iteration();

            List<Individual<Float[], Float>> topPercentage = fitnessHandler.findTopPercentage(testPopulation.allIndividuals(), 0.1F, 10);
            float result = 1 - fitnessHandler.fitnessByMean(topPercentage, testData);
            System.out.printf("第%d次迭代,   \t适应度为:%s", i, instance.format(result));
            System.out.println();
            if (minAbs > result) {
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


    private boolean filter(Float[] data) {
        for (Float datum : data) {
            if (datum == -9) {
                return false;
            }
        }
        return true;
    }

}
