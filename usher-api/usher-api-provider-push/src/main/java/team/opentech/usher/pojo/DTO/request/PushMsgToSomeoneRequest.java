package team.opentech.usher.pojo.DTO.request;

import team.opentech.usher.pojo.cqe.DefaultCQE;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年11月17日 07时30分
 */
public class PushMsgToSomeoneRequest extends DefaultCQE {

    /**
     * 接收人id
     */
    private Long userId;

    /**
     * 推送方式{@link team.opentech.usher.enums.PushTypeEnum}
     */
    private Integer type;

    /**
     * 推送标题
     */
    private String title;

    /**
     * 推送内容
     */
    private String msg;

    public PushMsgToSomeoneRequest() {
    }

    public PushMsgToSomeoneRequest(DefaultCQE request) {
        super(request);
    }

    public static PushMsgToSomeoneRequest build(DefaultCQE request, Long userId, Integer type, String title, String msg) {
        PushMsgToSomeoneRequest build = new PushMsgToSomeoneRequest(request);
        build.setUserId(userId);
        build.setType(type);
        build.setTitle(title);
        build.setMsg(msg);
        return build;

    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
