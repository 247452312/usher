package top.uhyils.usher.pojo.cqe.command;

import top.uhyils.usher.pojo.cqe.command.base.AbstractCommand;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年12月04日 19时24分
 */
public class StringCommand extends AbstractCommand {

    /**
     * 值
     */
    private String value;

    /**
     * 快捷创建
     */
    public static StringCommand build(String value) {
        StringCommand build = new StringCommand();
        build.setValue(value);
        return build;

    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
