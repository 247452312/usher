package team.opentech.usher.pojo.DO;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import team.opentech.usher.pojo.DO.base.BaseDO;

/**
 * 工单节点处理结果样例表(OrderBaseNodeResultType)表 数据库实体类
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年09月11日 10时46分04秒
 */
@TableName(value = "sys_order_base_node_result_type")
public class OrderBaseNodeResultTypeDO extends BaseDO {

    private static final long serialVersionUID = 308480782271096328L;


    /**
     * 节点id
     */
    @TableField
    private Long baseNodeId;

    /**
     * 处理结果名称
     */
    @TableField
    private String dealResultName;


    public Long getBaseNodeId() {
        return baseNodeId;
    }

    public void setBaseNodeId(Long baseNodeId) {
        this.baseNodeId = baseNodeId;
    }


    public String getDealResultName() {
        return dealResultName;
    }

    public void setDealResultName(String dealResultName) {
        this.dealResultName = dealResultName;
    }

}
