package top.uhyils.usher.pojo.DO;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import top.uhyils.usher.pojo.DO.base.BaseDO;

/**
 * 网络调用独立可工作节点(NetNodeInfo)表 数据库实体类
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2025年01月10日 15时22分
 */
@TableName(value = "sys_net_node_info")
public class NetNodeInfoDO extends BaseDO {

    private static final long serialVersionUID = -1L;

    /**
     * 数据库名
     */
    @TableField("`database`")
    private String database;

    /**
     * 表名
     */
    @TableField("`table`")
    private String table;

    /**
     * 类型
     */
    @TableField("`type`")
    private String type;


    /**
     * 所属公司id
     */
    @TableField
    private Long companyId;

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .append("database", getDatabase())
            .append("table", getTable())
            .append("type", getType())
            .toString();
    }
}
