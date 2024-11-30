package top.uhyils.usher.pojo.entity;

import com.alibaba.fastjson.JSON;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import top.uhyils.usher.BaseTest;
import top.uhyils.usher.pojo.DO.AlgorithmDO;
import top.uhyils.usher.pojo.DTO.request.CellAlgorithmRequest;
import top.uhyils.usher.pojo.DTO.response.CellAlgorithmResponse;
import top.uhyils.usher.service.AlgorithmService;
import top.uhyils.usher.util.Asserts;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2021年12月08日 09时38分
 */
public class AlgorithmTest extends BaseTest {

    private static final String s = "public class Algorithm {\n" +
                                    "    public int cell(int a, int b) {\n" +
                                    "        return a + b;\n" +
                                    "    }\n" +
                                    "}";

    @Autowired
    private AlgorithmService service;

    @Test
    public void cellB() {
        CellAlgorithmRequest request = new CellAlgorithmRequest();
        request.setAlgorithmId(1L);
        request.setRequestBody(new Object[]{1, 2});
        CellAlgorithmResponse cellAlgorithmResponse = service.cellAlgorithm(request);
        Object cell = cellAlgorithmResponse.getResult();
        Asserts.assertTrue(cell instanceof Integer && (Integer) cell == 3);
    }

    @Test
    void cell() {
        AlgorithmDO data = new AlgorithmDO();
        data.setId(123123L);
        data.setName("测试算法");
        data.setNeedFile(false);
        Map<String, String> algorithmMap = new HashMap<>(1);
        algorithmMap.put("Algorithm.java", s);
        String body1 = JSON.toJSONString(algorithmMap);
        data.setBody(body1);
        Algorithm algorithm = new Algorithm(data);
        Object cell = algorithm.cell(1, 1);
        Asserts.assertTrue(cell instanceof Integer && (Integer) cell == 2);
    }
}
