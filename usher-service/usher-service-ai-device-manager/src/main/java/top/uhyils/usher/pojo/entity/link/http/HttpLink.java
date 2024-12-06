package top.uhyils.usher.pojo.entity.link.http;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;
import top.uhyils.usher.pojo.entity.link.AbstractLink;
import top.uhyils.usher.pojo.link.HttpLinkInfo;
import top.uhyils.usher.util.HttpUtil;
import top.uhyils.usher.util.LogUtil;
import top.uhyils.usher.util.MapUtil;
import top.uhyils.usher.util.StringUtil;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年12月05日 15时04分
 */
public class HttpLink extends AbstractLink<Map, Object> {

    public static final String DEFAULT_PLACEHOLDER_PREFIX = "${";

    public static final String DEFAULT_PLACEHOLDER_SUFFIX = "}";

    public static final String DEFAULT_VALUE_SEPARATOR = ":";

    /**
     * 匹配大写字符
     */
    private static final Pattern CURLY_BRACES_PATTERN = Pattern.compile("\\$\\{(.*?)\\}");

    /**
     * 相关信息
     */
    private final HttpLinkInfo linkInfo;

    /**
     * 地址
     */
    private final String address;

    private ThrParamFunction<String, Map<String, String>, Map<String, Object>, Object> function;

    public HttpLink(String address, HttpLinkInfo linkInfo) {
        this.address = address;
        this.linkInfo = linkInfo;
        initFunction();
    }

    private static String repeatStr(String source, Map<String, String> replace) {
        if (StringUtils.isBlank(source)) {
            return source;
        }
        if (!(source.startsWith(DEFAULT_PLACEHOLDER_PREFIX) && source.endsWith(DEFAULT_PLACEHOLDER_SUFFIX))) {
            return source;
        }
        String substring = source.substring(DEFAULT_PLACEHOLDER_PREFIX.length(), source.length() - DEFAULT_PLACEHOLDER_SUFFIX.length());
        if (!substring.contains(DEFAULT_VALUE_SEPARATOR)) {
            return replace.get(substring);
        }
        String[] split = substring.split(DEFAULT_VALUE_SEPARATOR);

        return replace.getOrDefault(split[0], split[1]);
    }


    @Override
    public Object doRequestSync(Map replace) {
        try {
            Map<String, String> requestHeader = new HashMap<>();
            Map<String, Object> requestParams = new HashMap<>();

            Map<String, String> header = linkInfo.getHeader();
            Map<String, Object> params = linkInfo.getParams();

            for (Entry<String, String> entry : header.entrySet()) {
                requestHeader.put(replace(entry.getKey(), replace), replace(entry.getValue(), replace));
            }
            if (MapUtil.isNotEmpty(params)) {
                for (Entry<String, Object> entry : params.entrySet()) {
                    requestParams.put(replace(entry.getKey(), replace), replace(entry.getValue(), replace));
                }
            }
            return function.accept(address, requestHeader, requestParams);
        } catch (Exception e) {
            LogUtil.error(this, e);
        }
        return null;
    }

    @Override
    public String ip() {
        try {
            return InetAddress.getByName(address).getHostAddress();
        } catch (UnknownHostException e) {
            LogUtil.error(this, e);
            return null;
        }
    }

    @Override
    public void tryLink() {
        try {
            HttpUtil.ping(ip());
        } catch (Exception e) {
            LogUtil.error(this, e);
        }
    }

    @Override
    protected void doRequest(Map requestObj) {
        doRequestSync(requestObj);
    }

    @Override
    protected Class<Map> requestClass() {
        return Map.class;
    }


    protected String replace(String source, Map<String, String> replace) {
        if (!source.contains(DEFAULT_PLACEHOLDER_PREFIX) || !source.contains(DEFAULT_PLACEHOLDER_SUFFIX)) {
            return source;
        }
        Matcher matcher = CURLY_BRACES_PATTERN.matcher(source);
        while (matcher.find()) {
            String group = matcher.group(0);
            String target = repeatStr(group, replace);
            source = source.replace(group, target);
        }
        return source;
    }

    protected Object replace(Object source, Map<String, String> replace) {
        if (source instanceof JSONArray) {
            String target = replace(((JSONArray) source).toJSONString(), replace);
            return JSONArray.parseArray(target);
        } else if (source instanceof JSONObject) {
            String target = replace(((JSONObject) source).toJSONString(), replace);
            return JSONObject.parseObject(target);
        }
        return source;
    }

    /**
     * 初始化http请求类型,节约消耗
     */
    private void initFunction() {
        if (StringUtil.equalsIgnoreCase(linkInfo.getType(), "post")) {
            this.function = HttpUtil::sendHttpPost;
        } else if (StringUtil.equalsIgnoreCase(linkInfo.getType(), "get")) {
            this.function = (s, stringStringMap, stringObjectMap) -> HttpUtil.sendHttpGet(s, stringStringMap);
        }
    }

    @FunctionalInterface
    private interface ThrParamFunction<A, B, C, R> {

        R accept(A a, B b, C c) throws Exception;
    }

}
