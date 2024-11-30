package top.uhyils.usher.mysql.pojo.DTO;

import java.io.Serializable;

/**
 * 用户表的另一个显示
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年08月23日 13时53分
 */
public class IUserPrivilegesInfo implements Serializable {

    /**
     * 授予特权的帐户名称， 格式。 'user_name'@'host_name'
     */
    private String grantee;

    /**
     * 目录的名称。此值始终为 def。
     */
    private String tableCatalog;

    /**
     * 授予的特权。该值可以是可以在全局级别上授予的任何特权。每行列出一个特权，因此，受赠者所拥有的每个全局特权都有一行。
     */
    private String privilegeType;

    /**
     * YES如果用户具有 GRANT OPTION特权， NO否则。输出不会使用列出 GRANT OPTION为单独的行PRIVILEGE_TYPE='GRANT OPTION'。
     */
    private String isGrantable;

    public String getGrantee() {
        return grantee;
    }

    public void setGrantee(String grantee) {
        this.grantee = grantee;
    }

    public String getTableCatalog() {
        return tableCatalog;
    }

    public void setTableCatalog(String tableCatalog) {
        this.tableCatalog = tableCatalog;
    }

    public String getPrivilegeType() {
        return privilegeType;
    }

    public void setPrivilegeType(String privilegeType) {
        this.privilegeType = privilegeType;
    }

    public String getIsGrantable() {
        return isGrantable;
    }

    public void setIsGrantable(String isGrantable) {
        this.isGrantable = isGrantable;
    }
}
