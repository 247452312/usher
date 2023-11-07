package team.opentech.usher.common.content;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * 去中心化集群常量
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年10月19日 08时56分
 */
public class UsherDecentralizedContent {


    /**
     * 默认编码类型
     */
    public static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    /*header中name*/

    /**
     * id的名称
     */
    public static final String ID_NAME = "UNIQUE";

    /**
     * header唯一标示key
     */
    public static final String HEADER_UNIQUE_KEY = "unique";

    /**
     * 请求类型名称
     */
    public static final String REQUEST_TYPE_NAME = "request_type";


    /*header中name end*/

    /**
     * 缓存头
     */
    public static final String CACHE_PRE = "DECENTRALIZED_ID:";

    /**
     * 协议标志(对应36进制的dct Decentralized cluster tools[去中心化集群]的简称)
     */
    private static final int AGREEMENT_START_INT = 0x439d;

    /**
     * 协议标志,两字节占用
     */
    public static final byte[] AGREEMENT_START = new byte[]{
        (byte) (AGREEMENT_START_INT >> 8 & 0xff),
        (byte) (AGREEMENT_START_INT & 0xff)
    };


}
