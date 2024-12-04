package top.uhyils.usher.pojo.cqe;

import top.uhyils.usher.pojo.cqe.command.base.AbstractCommand;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年12月04日 19时43分
 */
public class FindMsgCommand extends AbstractCommand {

    /**
     * 唯一标示
     */
    private String uniqueMark;

    /**
     * 获取信息时携带的入参
     */
    private Object request;

    public String getUniqueMark() {
        return uniqueMark;
    }

    public void setUniqueMark(String uniqueMark) {
        this.uniqueMark = uniqueMark;
    }

    public Object getRequest() {
        return request;
    }

    public void setRequest(Object request) {
        this.request = request;
    }
}
