package team.opentech.usher.pojo.cqe;

import team.opentech.usher.pojo.cqe.command.base.AbstractCommand;

/**
 * 创建独立空间
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年11月14日 15时46分
 */
public class SpaceCreateCommand extends AbstractCommand {

    /**
     * 名称
     */
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
