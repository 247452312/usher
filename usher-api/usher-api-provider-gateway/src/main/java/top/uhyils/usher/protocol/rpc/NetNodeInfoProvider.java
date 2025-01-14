package top.uhyils.usher.protocol.rpc;

import top.uhyils.usher.pojo.DTO.NetNodeInfoDTO;
import top.uhyils.usher.pojo.cqe.NetNodeCreateCommand;
import top.uhyils.usher.pojo.cqe.command.IdCommand;
import top.uhyils.usher.protocol.rpc.base.DTOProvider;

/**
 * 网络调用独立可工作节点(NetNodeInfo)表 Rpc对外访问层
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2025年01月10日 15时22分
 */
public interface NetNodeInfoProvider extends DTOProvider<NetNodeInfoDTO> {

    /**
     * 创建一个节点
     *
     * @param command 节点信息
     *
     * @return
     */
    Boolean create(NetNodeCreateCommand command);


    /**
     * 删除一个节点
     *
     * @param id
     *
     * @return
     */
    Integer remove(IdCommand id);



}
