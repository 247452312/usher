package top.uhyils.usher.pojo.entity;

import java.util.List;
import top.uhyils.usher.annotation.Default;
import top.uhyils.usher.node.LeafNodeFactory;
import top.uhyils.usher.node.call.CallNode;
import top.uhyils.usher.pojo.CallInfo;
import top.uhyils.usher.pojo.DO.NetNodeInfoDO;
import top.uhyils.usher.pojo.SqlInvokeCommand;
import top.uhyils.usher.pojo.TableInfo;
import top.uhyils.usher.pojo.entity.base.AbstractDoEntity;
import top.uhyils.usher.repository.NetNodeInfoDetailRepository;
import top.uhyils.usher.ustream.UStream;
import top.uhyils.usher.util.SpringUtil;

/**
 * 网络调用独立可工作节点(NetNodeInfo)表 数据库实体类
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2025年01月10日 15时22分
 */
public class NetNodeInfo extends AbstractDoEntity<NetNodeInfoDO> {

    /**
     * 详情, 当前节点对应sql类型 例如 query,update等
     */
    private List<NetNodeInfoDetail> details;

    @Default
    public NetNodeInfo(NetNodeInfoDO data) {
        super(data);
    }

    public NetNodeInfo(Long id) {
        super(id, new NetNodeInfoDO());
    }

    public CallNode toCallNode(SqlInvokeCommand invokeCommand) {
        TableInfo tableInfo = new TableInfo();
        NetNodeInfoDO data = toDataAndValidate();
        tableInfo.setNodeId(data.getId());
        tableInfo.setDatabaseName(data.getDatabase());
        tableInfo.setTableName(data.getTable());
        CallInfo callInfo = new CallInfo();
        List<NetNodeInfoDetail> detailsTemp = details();
        UStream<NetNodeInfoDetail> ustream = detailsTemp.ustream();
        callInfo.setParams(ustream.toMap(NetNodeInfoDetail::type, NetNodeInfoDetail::params));
        callInfo.setSupportSqlTypes(ustream.map(NetNodeInfoDetail::type).toList());

        tableInfo.setCallInfo(callInfo);
        tableInfo.setType(data.getType());

        return LeafNodeFactory.makeLeafNode(invokeCommand, tableInfo);
    }

    private List<NetNodeInfoDetail> details() {
        if (details != null) {
            return details;
        }
        NetNodeInfoDetailRepository detailRepository = SpringUtil.getBean(NetNodeInfoDetailRepository.class);
        this.details = detailRepository.findByNodeId(this.unique);
        return this.details;
    }

}
