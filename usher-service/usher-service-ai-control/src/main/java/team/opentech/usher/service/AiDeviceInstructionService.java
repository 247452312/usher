package team.opentech.usher.service;


import java.util.List;
import team.opentech.usher.pojo.DTO.AiDeviceInstructionDTO;
import team.opentech.usher.pojo.DTO.AiSubspaceDTO;
import team.opentech.usher.pojo.cqe.CopyInstructionsByDeviceIdCommand;
import team.opentech.usher.pojo.cqe.CreateDeviceInstructionCommand;
import team.opentech.usher.pojo.cqe.command.IdCommand;
import team.opentech.usher.pojo.cqe.query.IdQuery;

/**
 * 设备指令表(AiDeviceInstruction)表 内部服务接口
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2024年11月13日 20时55分
 */
public interface AiDeviceInstructionService extends BaseDoService<AiDeviceInstructionDTO> {

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
