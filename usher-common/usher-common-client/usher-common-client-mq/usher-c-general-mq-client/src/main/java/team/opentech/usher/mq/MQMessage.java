package team.opentech.usher.mq;

import com.alibaba.fastjson.JSONObject;
import java.io.Serializable;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import team.opentech.usher.util.IdUtil;
import team.opentech.usher.util.SpringUtil;

/**
 * mq消息体
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年09月02日 15时38分
 */
public class MQMessage implements Serializable {

    /**
     * 消息id
     */
    private final Long messageId;

    /**
     * 对应topic的名称
     */
    private final String topicName;

    /**
     * 消息tag
     */
    private final String tag;

    /**
     * 消息体
     */
    private final String body;

    /**
     * header信息
     */
    private final Map<String, Object> header;

    public MQMessage(Long messageId, String topicName, String tag, String body, Map<String, Object> header) {
        this.messageId = messageId;
        this.topicName = topicName;
        this.tag = tag;
        this.body = body;
        this.header = header;
    }

    public MQMessage(String topicName, String tag, String body, Map<String, Object> header) {
        this(SpringUtil.getBean(IdUtil.class).newId(), topicName, tag, body, header);
    }

    public MQMessage(String topicName, String tag, String body) {
        this(topicName, tag, body, new HashMap<>());
    }

    public MQMessage(String topicName, String body) {
        this(topicName, MQContent.MQ_DEFAULT_NAME, body);
    }

    public Long id() {
        return messageId;
    }

    /**
     * topic名称
     *
     * @return
     */
    public String topicName() {
        return topicName;
    }

    public Map<String, Object> header() {
        return header;
    }

    public Object header(String key) {
        return header.get(key);
    }

    public String body() {
        return body;
    }

    public Integer intBody() {
        return Integer.valueOf(body);
    }

    public Double doubleBody() {
        return Double.valueOf(body);
    }

    public Float floatBody() {
        return Float.valueOf(body);
    }

    public <T> T body(Class<T> clazz) {
        return JSONObject.parseObject(body, clazz);
    }

    public String tag() {
        return tag;
    }

    @Override
    public String toString() {
        return MessageFormat.format("tag:{0},body:{1}", tag, body);
    }

}
