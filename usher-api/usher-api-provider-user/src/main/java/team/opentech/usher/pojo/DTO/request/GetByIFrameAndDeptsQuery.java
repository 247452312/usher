package team.opentech.usher.pojo.DTO.request;

import team.opentech.usher.pojo.cqe.command.base.AbstractCommand;

/**
 * 获取权限菜单的请求
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年05月28日 13时53分
 */
public class GetByIFrameAndDeptsQuery extends AbstractCommand {

    private Integer iFrame;

    public Integer getiFrame() {
        return iFrame;
    }

    public void setiFrame(Integer iFrame) {
        this.iFrame = iFrame;
    }
}
