package team.opentech.usher.pojo.DTO;

import team.opentech.usher.pojo.DTO.base.IdDTO;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 厂商表表(Company)表 对外数据传输载体
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2022年09月09日 15时45分
 */
public class CompanyDTO extends IdDTO {

    private static final long serialVersionUID = -1L;

    /**
     * 公司名称
     */
    private String name;

    /**
     * 负责人姓名
     */
    private String personName;

    /**
     * 负责人电话
     */
    private String personPhone;

    /**
     * AK
     */
    private String ak;

    /**
     * SK
     */
    private String sk;

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .append("name", getName())
            .append("personName", getPersonName())
            .append("personPhone", getPersonPhone())
            .append("ak", getAk())
            .append("sk", getSk())
            .toString();
    }

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
