package top.uhyils.usher.pojo.cqe;

import top.uhyils.usher.pojo.cqe.command.base.AbstractCommand;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2025年01月13日 19时18分
 */
public class CompanyCreateCommand extends AbstractCommand {

    /**
     * 名字
     */
    private String name;

    /**
     * 联系人名称
     */
    private String personName;

    /**
     * 联系人电话
     */
    private String personPhone;

    /**
     * ak
     */
    private String ak;

    /**
     * sk
     */
    private String sk;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getPersonPhone() {
        return personPhone;
    }

    public void setPersonPhone(String personPhone) {
        this.personPhone = personPhone;
    }

    public String getAk() {
        return ak;
    }

    public void setAk(String ak) {
        this.ak = ak;
    }

    public String getSk() {
        return sk;
    }

    public void setSk(String sk) {
        this.sk = sk;
    }
}
