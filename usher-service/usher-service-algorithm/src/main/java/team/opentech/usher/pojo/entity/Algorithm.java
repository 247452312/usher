package team.opentech.usher.pojo.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import team.opentech.usher.annotation.Default;
import team.opentech.usher.enums.LanguageTypeEnum;
import team.opentech.usher.exception.AlgorithmException;
import team.opentech.usher.pojo.DO.AlgorithmDO;
import team.opentech.usher.pojo.entity.base.AbstractDoEntity;
import team.opentech.usher.pojo.entity.type.Identifier;
import team.opentech.usher.repository.AlgorithmRepository;
import team.opentech.usher.util.Asserts;
import team.opentech.usher.util.LogUtil;
import team.opentech.usher.util.compiler.JavaStringCompiler;
import team.opentech.usher.util.python.PythonUtil;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;

/**
 * 算法表(Algorithm)表 数据库实体类
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2021年09月09日 20时58分06秒
 */
public class Algorithm extends AbstractDoEntity<AlgorithmDO> {

    /**
     * 启动类
     */
    private static final String MAIN_CLASS_NAME = "Algorithm";

    /**
     * 启动方法名称
     */
    private static final String MAIN_METHOD_NAME = "cell";

    @Default
    public Algorithm(AlgorithmDO data) {
        super(data);
    }

    public Algorithm(Long id) {
        super(id, new AlgorithmDO());
    }

    public Algorithm(Long id, AlgorithmRepository rep) {
        super(id, new AlgorithmDO());
        completion(rep);
    }

    public Algorithm(Identifier id) {
        super(id, new AlgorithmDO());
    }

    /**
     * 执行方法
     *
     * @param requestBody
     *
     * @return
     */
    public Object cell(Object... requestBody) {
        AlgorithmDO algorithmDO = toData().orElseThrow(Asserts::throwOptionalException);
        Integer languageType = algorithmDO.getLanguageType();
        LanguageTypeEnum languageTypeEnum = LanguageTypeEnum.parse(languageType);
        switch (languageTypeEnum) {
            case JAVA:
                return cellJava(requestBody);
            case PYTHON:
                return cellPython(requestBody);
            default:
                Asserts.throwException("暂不支持语言类型");
                return null;
        }
    }

    /**
     * 执行java
     *
     * @param requestBody
     *
     * @return
     */
    private Object cellJava(Object[] requestBody) {
        AlgorithmDO algorithmDO = toData().orElseThrow(Asserts::throwOptionalException);
        String body = algorithmDO.getBody();
        JSONObject jsonObject = JSON.parseObject(body);

        JavaStringCompiler javaStringCompiler = new JavaStringCompiler();
        HashMap<String, String> fileSourceMap = new HashMap<>(jsonObject.size());
        for (Entry<String, Object> objectEntry : jsonObject.entrySet()) {
            String fileName = objectEntry.getKey();
            Object fileBodyStr = objectEntry.getValue();
            fileSourceMap.put(fileName, fileBodyStr.toString());
        }

        Object invoke;
        try {
            Map<String, byte[]> compile = javaStringCompiler.compile(fileSourceMap);
            Optional<String> firstMainFileName = compile.keySet().stream().filter(t -> t.endsWith(MAIN_CLASS_NAME)).findFirst();
            if (!firstMainFileName.isPresent()) {
                throw new AlgorithmException("不存在指定入口类:" + MAIN_CLASS_NAME);
            }
            Class<?> c = javaStringCompiler.loadClass(firstMainFileName.get(), compile);
            Object o = c.newInstance();
            Optional<Method> first = Arrays.stream(c.getDeclaredMethods()).filter(t -> Objects.equals(t.getName(), MAIN_METHOD_NAME)).findFirst();
            if (!first.isPresent()) {
                throw new AlgorithmException("不存在指定入口方法:" + MAIN_METHOD_NAME);
            }
            Method declaredMethod = first.get();
            declaredMethod.setAccessible(Boolean.TRUE);
            invoke = declaredMethod.invoke(o, requestBody);
            LogUtil.info("动态执行算法:" + algorithmDO.getName());
            LogUtil.info("结果为:\n" + invoke.toString());
        } catch (Exception e) {
            LogUtil.error(this, e);
            throw new AlgorithmException(e);
        }
        return invoke;
    }

    /**
     * 执行python
     *
     * @param requestBody
     *
     * @return
     */
    private Object cellPython(Object[] requestBody) {
        AlgorithmDO algorithmDO = toData().orElseThrow(Asserts::throwOptionalException);
        String body = algorithmDO.getBody();
        try {
            return PythonUtil.executePython(body, "__main__", requestBody);
        } catch (IOException e) {
            LogUtil.error(this, e);
            return null;
        }
    }

}
