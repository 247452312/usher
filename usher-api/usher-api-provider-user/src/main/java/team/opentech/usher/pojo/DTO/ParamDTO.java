package team.opentech.usher.pojo.DTO;

import team.opentech.usher.pojo.DTO.base.IdDTO;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 系统参数表表(Param)表 对外数据传输载体
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2022年06月17日 15时53分
 */
public class ParamDTO extends IdDTO {

    private static final long serialVersionUID = -1L;

    /**
     * 系统参数key
     */
    private String key;

    /**
     * 描述
     */
    private String desc;

    /**
     * 系统参数值
     */
    private String value;

    /**
     * 用户id,如果用户id为-1则代表是全局参数
     */
    private Long userId;

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .append("key", getKey())
            .append("desc", getDesc())
            .append("value", getValue())
            .append("userId", getUserId())
            .toString();
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

}
