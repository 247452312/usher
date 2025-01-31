package team.opentech.usher.pojo.DTO.request;

import team.opentech.usher.pojo.cqe.DefaultCQE;


/**
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月04日 09时09分
 */
public class GetTraceDetailByHashCodeRequest extends DefaultCQE {

    /**
     * hash
     */
    private String hashCode;

    /**
     * 类型
     */
    private Integer type;

    public String getHashCode() {
        return hashCode;
    }

    public void setHashCode(String hashCode) {
        this.hashCode = hashCode;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
