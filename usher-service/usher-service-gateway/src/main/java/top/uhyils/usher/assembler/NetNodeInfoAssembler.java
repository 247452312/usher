package top.uhyils.usher.assembler;


import com.alibaba.fastjson.JSONObject;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.mapstruct.Mapper;
import top.uhyils.usher.enums.QuerySqlTypeEnum;
import top.uhyils.usher.mysql.pojo.DTO.TableDTO;
import top.uhyils.usher.pojo.CallInfo;
import top.uhyils.usher.pojo.DO.NetNodeInfoDO;
import top.uhyils.usher.pojo.DTO.NetNodeInfoDTO;
import top.uhyils.usher.pojo.DTO.NetNodeInfoDetailDTO;
import top.uhyils.usher.pojo.TableInfo;
import top.uhyils.usher.pojo.entity.NetNodeInfo;
import top.uhyils.usher.pojo.entity.NetNodeInfoDetail;
import top.uhyils.usher.ustream.UStream;

/**
 * 网络调用独立可工作节点(NetNodeInfo)表 entity,DO,DTO转换工具
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2025年01月10日 15时22分
 */
@Mapper(componentModel = "spring")
public abstract class NetNodeInfoAssembler extends AbstractAssembler<NetNodeInfoDO, NetNodeInfo, NetNodeInfoDTO> {

    @Resource
    private NetNodeInfoDetailAssembler netNodeInfoDetailAssembler;

    public abstract List<TableDTO> toTableDTO(List<NetNodeInfoDTO> callNodeDTOS);

    public TableDTO toTableDTO(NetNodeInfoDTO callNodeDTOS) {
        TableDTO result = new TableDTO();
        result.setCompanyId(callNodeDTOS.getCompanyId());
        result.setNodeId(callNodeDTOS.getId());
        result.setDatabase(callNodeDTOS.getDatabase());
        result.setTable(callNodeDTOS.getTable());

        return result;
    }

    public TableInfo toTableInfo(NetNodeInfoDTO nodeInfo, List<NetNodeInfoDetailDTO> details) {
        TableInfo tableInfo = new TableInfo();
        tableInfo.setNodeId(nodeInfo.getId());
        tableInfo.setDatabaseName(nodeInfo.getDatabase());
        tableInfo.setTableName(nodeInfo.getTable());
        CallInfo callInfo = new CallInfo();
        UStream<NetNodeInfoDetailDTO> ustream = details.ustream();
        Map<QuerySqlTypeEnum, JSONObject> map = ustream.toMap(t -> QuerySqlTypeEnum.findByName(t.getQuerySqlType()), t -> JSONObject.parseObject(t.getParams()));
        callInfo.setParams(map);
        callInfo.setSupportSqlTypes(ustream.map(t -> QuerySqlTypeEnum.findByName(t.getQuerySqlType())).toList());
        tableInfo.setCallInfo(callInfo);
        tableInfo.setType(nodeInfo.getType());
        return tableInfo;
    }

    public TableInfo toTableInfo(NetNodeInfo nodeInfo, List<NetNodeInfoDetail> details) {
        return toTableInfo(toDTO(nodeInfo), netNodeInfoDetailAssembler.listEntityToDTO(details));
    }
}
