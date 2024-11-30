package top.uhyils.usher.genetic.builder;

import com.alibaba.fastjson.JSON;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import top.uhyils.usher.annotation.NotNull;
import top.uhyils.usher.enums.DimensionalityReductionTypeEnum;
import top.uhyils.usher.enums.StandardizationTypeEnum;
import top.uhyils.usher.genetic.InitGenetic;
import top.uhyils.usher.genetic.UsedGenetic;
import top.uhyils.usher.genetic.core.heartDisease.DataReader;
import top.uhyils.usher.genetic.model.GeneticModel;
import top.uhyils.usher.util.Asserts;
import top.uhyils.usher.util.CollectionUtil;
import top.uhyils.usher.util.Pair;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年04月17日 11时05分
 */
class InitGeneticTest {


    @NotNull
    private static Pair<double[][], double[]> getFileData(String path) throws FileNotFoundException {
        DataReader dataReader = new DataReader();
        String fileData = dataReader.read(path);
        List<String[]> collect = Arrays.stream(fileData.split("name")).map(t -> t.replace("\n", " ")).map(t -> t.trim().split(" ")).collect(Collectors.toList());

        double[][] datas = new double[collect.size()][13];
        double[] results = new double[collect.size()];
        for (int i = 0; i < collect.size(); i++) {
            String[] split = collect.get(i);
            if (split.length < 58) {
                break;
            }
            datas[i][0] = Double.parseDouble(split[2].trim());
            datas[i][1] = Double.parseDouble(split[3].trim());
            datas[i][2] = Double.parseDouble(split[8].trim());
            datas[i][3] = Double.parseDouble(split[9].trim());
            datas[i][4] = Double.parseDouble(split[11].trim());
            datas[i][5] = Double.parseDouble(split[15].trim());
            datas[i][6] = Double.parseDouble(split[18].trim());
            datas[i][7] = Double.parseDouble(split[31].trim());
            datas[i][8] = Double.parseDouble(split[37].trim());
            datas[i][9] = Double.parseDouble(split[39].trim());
            datas[i][10] = Double.parseDouble(split[40].trim());
            datas[i][11] = Double.parseDouble(split[43].trim());
            datas[i][12] = Double.parseDouble(split[50].trim());
            results[i] = Double.parseDouble(split[57].trim()) >= 1 ? 1 : 0;
        }

        return new Pair<>(datas, results);
    }

    @Test
    void testTrain() throws FileNotFoundException {
        Pair<double[][], double[]> fileData = getFileData("D:\\share\\data\\heart+disease\\new.data");
        // 数据清洗
        fileData = clean(fileData);
        Properties config = new Properties();
        config.setProperty("scenario.population.K", "1000");
        config.setProperty("scenario.population.init-percentage", "0.2");
        config.setProperty("scenario.population.init-cross-percentage", "0.4");
        config.setProperty("scenario.population.init-variation-percentage", "0.2");
        config.setProperty("scenario.population.learning-rate", "0.02");

        TraningBuilder builder = new TraningBuilder();
        InitGenetic initGenetic = builder.allData(fileData.getKey(), fileData.getValue())
                                         .buildGenetic(config);
        initGenetic.setDimensionalityReductionConfig(DimensionalityReductionTypeEnum.PCA);
        initGenetic.setExtractedConfig(StandardizationTypeEnum.Z_SCORE, false);

        //        double v = initGenetic.train(5000, true);
        double v = initGenetic.trainAndLearnByTrainData(500, true);
        System.out.println("执行了500次之后的遗传算法适应度为:" + v);

        GeneticModel model = initGenetic.exportModel();
        System.out.println(JSON.toJSONString(model));
        double[][] key = fileData.getKey();
        Double[] array = Arrays.stream(key[key.length - 1]).boxed().toArray(Double[]::new);
        Double test = initGenetic.test(array);

        Pair<double[][], double[]> testData = builder.getTestData();
        UsedGenetic usedGenetic = new TraningBuilder().testData(testData.getKey(), testData.getValue())
                                                      .buildGenetic(model);
        Double test2 = usedGenetic.test(array);
        Asserts.assertTrue(Objects.equals(test, test2), "GeneticModel 导出导入行为不一致 导出前值:" + test + " 导出后值:" + test2);
    }

    private boolean filter(double v) {
        return v != -9;
    }

    /**
     * 当前值中是否不存在-9
     *
     * @param data
     *
     * @return
     */
    private boolean filter(double[] data) {
        for (double datum : data) {
            if (datum == -9) {
                return false;
            }
        }
        return true;
    }

    @NotNull
    private Pair<double[][], double[]> clean(Pair<double[][], double[]> fileData) {
        if (CollectionUtil.isEmpty(fileData.getKey())) {
            return fileData;
        }
        // 过滤掉存在值为-9 的数据
        double[][] params = fileData.getKey();
        double[] result = fileData.getValue();
        List<Integer> removeIndex = new ArrayList<>();
        for (int i = 0; i < params.length; i++) {
            if (!filter(params[i]) || !filter(result[i])) {
                removeIndex.add(i);
            }
        }
        double[][] realParams = new double[params.length - removeIndex.size()][];
        double[] realResults = new double[params.length - removeIndex.size()];
        int realIndex = 0;
        for (int i = 0; i < params.length; i++) {
            if (removeIndex.contains(i)) {
                continue;
            }
            realParams[realIndex] = params[i];
            realResults[realIndex] = result[i];
            realIndex++;
        }
        params = realParams;
        result = realResults;

        return new Pair<>(params, result);
    }

}
