package top.uhyils.usher.mysql.pojo.DTO;

import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 调用表信息
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年08月30日 17时17分
 */
public class TableDTO implements Serializable {

    private static final long serialVersionUID = -1L;

    /**
     * 厂商id
     */
    private Long companyId;

    /**
     * 转换节点id
     */
    private Long nodeId;


    /**
     * 对应唯一标识(url形式),如果是mysql调用,则使用全称拼写,例如库名/表名
     */
    private String url;

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("companyId", getCompanyId())
            .append("nodeId", getNodeId())
            .append("url", getUrl())
            .toString();
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getNodeId() {
        return nodeId;
    }

    public void setNodeId(Long nodeId) {
        this.nodeId = nodeId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
