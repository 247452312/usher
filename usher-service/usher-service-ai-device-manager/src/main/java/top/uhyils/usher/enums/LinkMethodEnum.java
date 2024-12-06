package top.uhyils.usher.enums;

import com.alibaba.fastjson.JSONObject;
import java.util.Objects;
import java.util.function.BiFunction;
import top.uhyils.usher.pojo.DTO.AiDeviceRealTimeDTO;
import top.uhyils.usher.pojo.entity.link.Link;
import top.uhyils.usher.pojo.entity.link.http.HttpLink;
import top.uhyils.usher.pojo.link.HttpLinkInfo;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年12月06日 14时15分
 */
public enum LinkMethodEnum {
    HTTP(AiDeviceLinkTypeEnum.HTTP, "HTTP链接", (s, realTime) -> new HttpLink(realTime.getAddress(), JSONObject.parseObject(s, HttpLinkInfo.class))),
    ;

    private final AiDeviceLinkTypeEnum code;

    private final String name;

    private final BiFunction<String, AiDeviceRealTimeDTO, Link> makeLinkFunction;

    LinkMethodEnum(AiDeviceLinkTypeEnum code, String name, BiFunction<String, AiDeviceRealTimeDTO, Link> makeLinkFunction) {
        this.code = code;
        this.name = name;
        this.makeLinkFunction = makeLinkFunction;
    }

    public static LinkMethodEnum getByCode(AiDeviceLinkTypeEnum type) {
        for (LinkMethodEnum value : values()) {
            if (Objects.equals(value.code, type)) {
                return value;
            }
        }
        return null;
    }

    /**
     * 初始化一个link
     */
    public Link makeLink(String paramJson, AiDeviceRealTimeDTO realTimeDTO) {
        return makeLinkFunction.apply(paramJson, realTimeDTO);
    }

    public AiDeviceLinkTypeEnum getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
