package team.opentech.usher.mq.pojo;

import java.io.Serializable;

/**
 * 回调信息
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年09月02日 19时46分
 */
public class BackInfo implements Serializable {

    /**
     * 调用是否成功
     */
    private Boolean success;


    /**
     * 失败信息
     */
    private String msg;


    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
