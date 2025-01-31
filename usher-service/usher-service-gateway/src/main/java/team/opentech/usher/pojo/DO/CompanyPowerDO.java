package team.opentech.usher.pojo.DO;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import team.opentech.usher.pojo.DO.base.BaseDO;

/**
 * 厂商接口调用权限表(CompanyPower)表 数据库实体类
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2022年09月09日 15时45分
 */
@TableName(value = "sys_company_power")
public class CompanyPowerDO extends BaseDO {

    private static final long serialVersionUID = -1L;

    /**
     * 厂商id
     */
    @TableField
    private Long companyId;

    /**
     * 接口id
     */
    @TableField
    private Long providerInterfaceId;

    /**
     * 状态 -1禁用 0申请中 1使用中
     */
    @TableField
    private Integer status;

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .append("companyId", getCompanyId())
            .append("providerInterfaceId", getProviderInterfaceId())
            .append("status", getStatus())
            .toString();
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getProviderInterfaceId() {
        return providerInterfaceId;
    }

    public void setProviderInterfaceId(Long providerInterfaceId) {
        this.providerInterfaceId = providerInterfaceId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
