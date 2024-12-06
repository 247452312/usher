package top.uhyils.usher.pojo.entity.link;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson2.JSON;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年12月05日 15时04分
 */
public abstract class AbstractLink<T, R> implements Link {

    private Consumer<String> consumer;


    @Override
    public void request(String request) {
        T requestObj = changeStrToObj(request, requestClass());
        doRequest(requestObj);
    }

    @Override
    public String requestSync(String request) {
        T requestObj = changeStrToObj(request, requestClass());
        R r = doRequestSync(requestObj);
        return JSON.toJSONString(r);
    }

    @Override
    public void setOnMessageFunction(Consumer<String> consumer) {
        this.consumer = consumer;
    }

    /**
     * 同步请求
     */
    protected abstract R doRequestSync(T requestObj);

    /**
     * 请求
     *
     * @param requestObj
     */
    protected abstract void doRequest(T requestObj);

    /**
     * 消息来临时调用
     */
    protected void onMessge(String msg) {
        consumer.accept(msg);
    }

    /**
     * 请求的类型
     *
     * @return
     */
    protected abstract Class<T> requestClass();

    /**
     * 修改str为对应类型的对象
     *
     * @param request
     *
     * @return
     */
    private <S> S changeStrToObj(String request, Class<S> clazz) {
        if (Integer.class.isAssignableFrom(clazz)) {
            Integer i = Integer.parseInt(request);
            return (S) i;
        } else if (Long.class.isAssignableFrom(clazz)) {
            Long l = Long.parseLong(request);
            return (S) l;
        } else if (Double.class.isAssignableFrom(clazz)) {
            Double v = Double.parseDouble(request);
            return (S) v;
        } else if (Float.class.isAssignableFrom(clazz)) {
            Float v = Float.parseFloat(request);
            return (S) v;
        } else if (Byte.class.isAssignableFrom(clazz)) {
            Byte b = Byte.parseByte(request);
            return (S) b;
        } else if (Character.class.isAssignableFrom(clazz)) {
            Character c = request.charAt(0);
            return (S) c;
        } else if (Boolean.class.isAssignableFrom(clazz)) {
            if (Objects.equals(request, "0")) {
                return (S) Boolean.FALSE;
            } else if (Objects.equals(request, "1")) {
                return (S) Boolean.TRUE;
            } else {
                Boolean b = Boolean.parseBoolean(request);
                return (S) b;
            }
        } else {
            return JSONObject.parseObject(request, clazz);
        }
    }
}
