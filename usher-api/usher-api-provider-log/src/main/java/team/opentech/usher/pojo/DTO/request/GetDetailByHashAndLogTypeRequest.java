package team.opentech.usher.pojo.DTO.request;


import team.opentech.usher.pojo.cqe.DefaultCQE;

/**
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月06日 21时36分
 */
public class GetDetailByHashAndLogTypeRequest extends DefaultCQE {

    /**
     * hash
     */
    private String hash;

    /**
     * 日志类型
     */
    private Integer logType;


    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Integer getLogType() {
        return logType;
    }

    public void setLogType(Integer logType) {
        this.logType = logType;
    }
}
