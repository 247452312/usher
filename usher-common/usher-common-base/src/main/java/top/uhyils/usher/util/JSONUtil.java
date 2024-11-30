package top.uhyils.usher.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年08月24日 17时35分
 */
public final class JSONUtil {

    private JSONUtil() {
        throw new RuntimeException("JSONUtil不允许新建");
    }

    /**
     * 递归匹配
     *
     * @param jsonObject json
     * @param fieldName  需要获取的字符串名称
     *
     * @return
     */
    public static Object recursiveMatch(JSONObject jsonObject, String fieldName) {
        return recursiveMatch(jsonObject, fieldName.split("\\."));
    }

    /**
     * 递归匹配
     *
     * @param jsonObject json
     * @param split      需要获取的字符串以"."分割
     *
     * @return
     */
    public static Object recursiveMatch(JSONObject jsonObject, String[] split) {
        Object item = jsonObject;
        for (int i = 0; i < split.length; i++) {
            String s = split[i];
            s = StringUtil.toCamelCase(s);
            if (item instanceof JSONObject) {
                item = ((JSONObject) item).get(s);
            } else if (item instanceof JSONArray) {
                Asserts.assertTrue(StringUtil.isDigit(s));
                int index = Integer.parseInt(s);
                item = ((JSONArray) item).get(index);
            } else {
                Asserts.throwException("未知的json格式");
            }
        }
        return item;

    }

}
