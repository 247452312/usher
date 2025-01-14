package top.uhyils.usher.pojo.entity;

import com.alibaba.fastjson.JSONObject;
import top.uhyils.usher.annotation.Default;
import top.uhyils.usher.enums.QuerySqlTypeEnum;
import top.uhyils.usher.pojo.DO.NetNodeInfoDetailDO;
import top.uhyils.usher.pojo.entity.base.AbstractDoEntity;

/**
 * 网络调用独立可工作节点支持的语句类型(NetNodeInfoDetail)表 数据库实体类
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2025年01月10日 15时22分
 */
public class NetNodeInfoDetail extends AbstractDoEntity<NetNodeInfoDetailDO> {

    @Default
    public NetNodeInfoDetail(NetNodeInfoDetailDO data) {
        super(data);
    }

    public NetNodeInfoDetail(Long id) {
        super(id, new NetNodeInfoDetailDO());
    }

    public QuerySqlTypeEnum type() {
        NetNodeInfoDetailDO dataAndValidate = toDataAndValidate();
        return QuerySqlTypeEnum.findByName(dataAndValidate.getQuerySqlType());
    }

    public JSONObject params() {
        NetNodeInfoDetailDO dataAndValidate = toDataAndValidate();
        String params = dataAndValidate.getParams();
        return JSONObject.parseObject(params);
    }

    /**
     * 填充nodeId
     *
     * @param nodeId
     */
    public void fillNodeId(Long nodeId) {
        NetNodeInfoDetailDO dataAndValidate = toDataAndValidate();
        dataAndValidate.setNodeId(nodeId);
        onUpdate();
    }
}
