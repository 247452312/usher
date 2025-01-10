package top.uhyils.usher.pojo.DTO;

import org.apache.commons.lang3.builder.ToStringBuilder;
import top.uhyils.usher.pojo.DTO.base.IdDTO;

/**
 * 网络调用独立可工作节点支持的语句类型表(NetNodeInfoDetail)表 对外数据传输载体
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2025年01月10日 15时22分
 */
public class NetNodeInfoDetailDTO extends IdDTO {

    private static final long serialVersionUID = -1L;

    /**
     * 查询语句的类型 例如 QUERY
     */
    private String querySqlType;

    /**
     * 主表id
     */
    private Long nodeId;

    /**
     * 参数 http就是url method之类的, DB就是update select等
     */
    private String params;


    public String getQuerySqlType() {
        return querySqlType;
    }

    public void setQuerySqlType(String querySqlType) {
        this.querySqlType = querySqlType;
    }

    public Long getNodeId() {
        return nodeId;
    }

    public void setNodeId(Long nodeId) {
        this.nodeId = nodeId;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
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
