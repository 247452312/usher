package top.uhyils.usher.pojo.cqe;

import java.util.List;
import top.uhyils.usher.pojo.DTO.NetNodeInfoDetailDTO;
import top.uhyils.usher.pojo.cqe.command.base.AbstractCommand;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2025年01月13日 18时53分
 */
public class NetNodeChangeCommand extends AbstractCommand {


    private Long id;

    /**
     * 获取当前节点所在库
     */
    private String database;

    /**
     * 获取当前节点映射的表
     */
    private String table;

    /**
     * 获取当前节点类型
     */
    private String type;

    /**
     * 对应详情, 如果为空, 则不变,因为详情本身不能为空
     */
    private List<NetNodeInfoDetailDTO> details;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public List<NetNodeInfoDetailDTO> getDetails() {
        return details;
    }

    public void setDetails(List<NetNodeInfoDetailDTO> details) {
        this.details = details;
    }
}
