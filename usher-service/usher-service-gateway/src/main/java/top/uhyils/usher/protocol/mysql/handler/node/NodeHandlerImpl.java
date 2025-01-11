package top.uhyils.usher.protocol.mysql.handler.node;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import top.uhyils.usher.assembler.NetNodeInfoAssembler;
import top.uhyils.usher.handler.impl.AbstractNodeHandler;
import top.uhyils.usher.pojo.DTO.NetNodeInfoDTO;
import top.uhyils.usher.pojo.DTO.NetNodeInfoDetailDTO;
import top.uhyils.usher.pojo.TableInfo;
import top.uhyils.usher.service.NetNodeInfoService;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2025年01月11日 09时38分
 */
@Component
public class NodeHandlerImpl extends AbstractNodeHandler {

    @Resource
    private NetNodeInfoService service;

    @Resource
    private NetNodeInfoAssembler assembler;

    @Override
    protected TableInfo findByDatabaseAndTable(String database, String table) {
        NetNodeInfoDTO nodeInfo = service.findByDatabaseAndTable(database, table);
        List<NetNodeInfoDetailDTO> details = service.findDetailById(nodeInfo.getId());
        return assembler.toTableInfo(nodeInfo, details);
    }
}
