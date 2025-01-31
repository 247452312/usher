package team.opentech.usher.pojo.DTO;


import team.opentech.usher.pojo.DTO.base.IdDTO;
import java.util.List;

/**
 * 工单基础信息样例表(OrderBaseInfo)表 对外数据传输载体
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月31日 19时58分55秒
 */
public class OrderBaseInfoDTO extends IdDTO {

    private static final long serialVersionUID = -78635420350310975L;


    /**
     * 工单名称
     */
    private String name;

    /**
     * 工单描述
     */
    private String desc;

    /**
     * 优先级 0->普通 1->加急
     */
    private Integer priority;

    /**
     * 是否是子流程
     */
    private Integer son;

    /**
     * 监管人id
     */
    private Long monitorUserId;

    /**
     * 可查询人id
     */
    private Long queryUserIds;

    /**
     * 类型 dict的OrderType
     */
    private Integer type;

    /**
     * 运行时限(分钟)
     */
    private Integer limitTime;

    /**
     * 节点列表
     */
    private List<OrderBaseNodeDTO> nodes;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }


    public Integer getSon() {
        return son;
    }

    public void setSon(Integer son) {
        this.son = son;
    }


    public Long getMonitorUserId() {
        return monitorUserId;
    }

    public void setMonitorUserId(Long monitorUserId) {
        this.monitorUserId = monitorUserId;
    }


    public Long getQueryUserIds() {
        return queryUserIds;
    }

    public void setQueryUserIds(Long queryUserIds) {
        this.queryUserIds = queryUserIds;
    }


    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }


    public Integer getLimitTime() {
        return limitTime;
    }

    public void setLimitTime(Integer limitTime) {
        this.limitTime = limitTime;
    }

    public List<OrderBaseNodeDTO> getNodes() {
        return nodes;
    }

    public void setNodes(List<OrderBaseNodeDTO> nodes) {
        this.nodes = nodes;
    }
}
