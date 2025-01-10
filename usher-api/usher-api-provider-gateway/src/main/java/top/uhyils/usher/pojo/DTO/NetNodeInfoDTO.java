package top.uhyils.usher.pojo.DTO;

import org.apache.commons.lang3.builder.ToStringBuilder;
import top.uhyils.usher.pojo.DTO.base.IdDTO;

/**
 * 网络调用独立可工作节点表(NetNodeInfo)表 对外数据传输载体
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2025年01月10日 15时22分
 */
public class NetNodeInfoDTO extends IdDTO {

    private static final long serialVersionUID = -1L;

    /**
     * 数据库名
     */
    private String database;

    /**
     * 表名
     */
    private String table;

    /**
     * 类型
     */
    private String type;


    /**
     * 所属公司id
     */
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
