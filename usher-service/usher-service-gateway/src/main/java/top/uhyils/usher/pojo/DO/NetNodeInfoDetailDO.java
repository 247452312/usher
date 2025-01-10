package top.uhyils.usher.pojo.DO;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import top.uhyils.usher.pojo.DO.base.BaseDO;

/**
 * 网络调用独立可工作节点支持的语句类型(NetNodeInfoDetail)表 数据库实体类
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2025年01月10日 15时22分
 */
@TableName(value = "sys_net_node_info_detail")
public class NetNodeInfoDetailDO extends BaseDO {

    private static final long serialVersionUID = -1L;

    /**
     * 主表id
     */
    @TableField
    private Long nodeId;

    /**
     * 查询语句的类型 例如 QUERY
     */
    @TableField
    private String querySqlType;

    /**
     * 参数 http就是url method之类的, DB就是update select等
     */
    @TableField
    private String params;

    public String getQuerySqlType() {
        return querySqlType;
    }

    public void setQuerySqlType(String querySqlType) {
        this.querySqlType = querySqlType;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public Long getNodeId() {
        return nodeId;
    }

    public void setNodeId(Long nodeId) {
        this.nodeId = nodeId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .append("querySqlType", getQuerySqlType())
            .append("params", getParams())
            .toString();
    }
}
