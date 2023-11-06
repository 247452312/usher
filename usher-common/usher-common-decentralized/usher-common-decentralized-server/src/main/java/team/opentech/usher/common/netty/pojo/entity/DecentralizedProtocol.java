package team.opentech.usher.common.netty.pojo.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import java.util.Map;
import team.opentech.usher.common.content.UsherDecentralizedContent;
import team.opentech.usher.common.netty.enums.DecentralizedRequestTypeEnum;
import team.opentech.usher.common.util.DecentralizedProtocolUtil;
import team.opentech.usher.pojo.entity.base.AbstractEntity;
import team.opentech.usher.pojo.entity.type.Identifier;
import team.opentech.usher.util.Asserts;
import team.opentech.usher.util.ByteUtil;
import team.opentech.usher.util.LogUtil;
import team.opentech.usher.util.Pair;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年10月20日 11时51分
 */
public class DecentralizedProtocol extends AbstractEntity<Identifier> {


    /**
     * 业务类型
     */
    private final byte[] type;

    /**
     * header
     */
    private final Map<String, Object> header;

    /**
     * 请求类型,官方请求类型为{@link DecentralizedRequestTypeEnum} 也可以标识为自定义类型,可以不依照官方标识,但是不要占用官方标识中的类型code
     */
    private final int requestType;

    /**
     * body
     */
    private final byte[] body;

    private DecentralizedProtocol(byte[] type, byte[] header, byte[] body) {
        this(type, JSONObject.parseObject(new String(header, UsherDecentralizedContent.DEFAULT_CHARSET)), body);
    }

    private DecentralizedProtocol(byte[] type, Map<String, Object> header, byte[] body) {
        this.type = type;
        this.header = header;
        this.body = body;
        setUnique(Identifier.build((Long) header.get(UsherDecentralizedContent.ID_NAME)));
        this.requestType = (int) header.get(UsherDecentralizedContent.REQUEST_TYPE_NAME);
    }


    /**
     * 构建一个协议体
     *
     * @param titleBytes
     * @param headerAndBody
     *
     * @return
     */
    public static DecentralizedProtocol build(byte[] titleBytes, byte[] headerAndBody) {
        if (titleBytes != null && titleBytes.length == 6) {
            LogUtil.warn("去中心化分布式工具协议错误,title.length需要固定为6");
            return null;
        }
        if (ByteUtil.equalsFromStart(titleBytes, UsherDecentralizedContent.AGREEMENT_START, 2)) {
            LogUtil.warn("去中心化分布式工具协议错误,title去中心化集群标识不正确");
            return null;
        }
        Pair<byte[], byte[]> headerAndBodyBytes = DecentralizedProtocolUtil.subHeaderAndBody(headerAndBody);
        return new DecentralizedProtocol(ByteUtil.subByte(titleBytes, 2), headerAndBodyBytes.getKey(), headerAndBodyBytes.getValue());
    }

    /**
     * 构建
     *
     * @param simpleTitle
     * @param header
     * @param body
     *
     * @return
     */
    public static DecentralizedProtocol build(byte[] simpleTitle, Map<String, Object> header, byte[] body) {
        byte[] title = null;
        // 构建时只有4字节 说明没有携带前两个字节
        if (simpleTitle.length == 4) {
            title = new byte[6];
            System.arraycopy(UsherDecentralizedContent.AGREEMENT_START, 0, title, 0, UsherDecentralizedContent.AGREEMENT_START.length);
            System.arraycopy(simpleTitle, 0, title, UsherDecentralizedContent.AGREEMENT_START.length, simpleTitle.length);
        } else if (simpleTitle.length == 6) {
            title = simpleTitle;
        } else {
            Asserts.throwException("去中心化分布式工具协议错误,title标识字节混乱");
        }
        return new DecentralizedProtocol(title, header, body);

    }

    public byte[] toBytes() {
        String jsonString = JSON.toJSONString(header);
        byte[] headerBytes = jsonString.getBytes(UsherDecentralizedContent.DEFAULT_CHARSET);
        byte[] headerLengthBytes = DecentralizedProtocolUtil.lengthToBytes(headerBytes.length);
        int length = headerLengthBytes.length + headerBytes.length + body.length;
        byte[] lengthBytes = DecentralizedProtocolUtil.lengthToBytes(length);
        byte[] result = new byte[2 + 4 + lengthBytes.length + length];
        System.arraycopy(UsherDecentralizedContent.AGREEMENT_START, 0, result, 0, 2);
        System.arraycopy(type, 0, result, 2, 4);
        System.arraycopy(lengthBytes, 0, result, 6, lengthBytes.length);
        System.arraycopy(headerLengthBytes, 0, result, 6 + lengthBytes.length, headerLengthBytes.length);
        System.arraycopy(headerBytes, 0, result, 6 + lengthBytes.length + headerLengthBytes.length, headerBytes.length);
        System.arraycopy(body, 0, result, 6 + lengthBytes.length + headerLengthBytes.length + headerBytes.length, body.length);
        return result;
    }

    public String type() {
        return type;
    }

    public int requestType() {
        return requestType;
    }

    public String headerStr() {
        return JSON.toJSONString(header);
    }

    public String bodyStr() {
        return new String(body);
    }

    public <T> T body(Class<T> clazz) {
        return JSONObject.parseObject(body, clazz);
    }

    public String cacheKey() {
        Long id = getUnique().get().getId();
        return UsherDecentralizedContent.CACHE_PRE + id;
    }
}
