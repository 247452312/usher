package top.uhyils.usher.pojo.cqe;

import top.uhyils.usher.pojo.cqe.command.base.AbstractCommand;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年12月18日 16时17分
 */
public class RandomCreateTokenCommand extends AbstractCommand {

    /**
     * 时效
     */
    private Integer validityCode;

    /**
     * 描述
     */
    private String describe;

    public Integer getValidityCode() {
        return validityCode;
    }

    public void setValidityCode(Integer validityCode) {
        this.validityCode = validityCode;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }
}
