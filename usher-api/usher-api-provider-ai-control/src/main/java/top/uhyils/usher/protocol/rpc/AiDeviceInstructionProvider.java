package top.uhyils.usher.protocol.rpc;

import java.util.List;
import top.uhyils.usher.pojo.DTO.AiDeviceInstructionDTO;
import top.uhyils.usher.pojo.DTO.AiSubspaceDTO;
import top.uhyils.usher.pojo.cqe.CopyInstructionsByDeviceIdCommand;
import top.uhyils.usher.pojo.cqe.CreateDeviceInstructionCommand;
import top.uhyils.usher.pojo.cqe.command.IdCommand;
import top.uhyils.usher.pojo.cqe.query.IdQuery;
import top.uhyils.usher.protocol.rpc.base.DTOProvider;

/**
 * 设备指令表(AiDeviceInstruction)表 Rpc对外访问层
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2024年11月13日 20时55分
 */
public interface AiDeviceInstructionProvider extends DTOProvider<AiDeviceInstructionDTO> {

    /**
     * 创建指令
     *
     * @param command
     *
     * @return
     */
    Boolean createDeviceInstruction(CreateDeviceInstructionCommand command);


    /**
     * 删除指令
     *
     * @param command
     *
     * @return
     */
    Boolean removeDeviceInstruction(IdCommand command);


    /**
     * 根据设备id查询指令
     *
     * @param command
     *
     * @return
     */
    List<AiDeviceInstructionDTO> findDeviceInstructionById(IdCommand command);

    /**
     * 复制设备指令
     *
     * @param command
     *
     * @return
     */
    Boolean copyInstructionsByDeviceId(CopyInstructionsByDeviceIdCommand command);


    /**
     * 根据设备id获取所在独立空间下所有的子空间
     *
     * @param query
     *
     * @return
     */
    List<AiSubspaceDTO> findAllSubSpaceInSpaceByDeviceId(IdQuery query);
    /**
     * 执行指令
     *
     * @param command
     *
     * @return
     */
    Boolean executeInstruction(IdCommand command);


}
