package team.opentech.usher.protocol.rpc;

import java.util.List;
import team.opentech.usher.pojo.DTO.AiDeviceDTO;
import team.opentech.usher.pojo.cqe.FindDeviceBySubSpaceIdQuery;
import team.opentech.usher.protocol.rpc.base.DTOProvider;

/**
 * 设备表(AiDevice)表 Rpc对外访问层
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2024年11月13日 20时55分
 */
public interface AiDeviceProvider extends DTOProvider<AiDeviceDTO> {

    /**
     * 根据子空间id获取设备id
     *
     * @param query
     *
     * @return
     */
    List<AiDeviceDTO> findDeviceBySubSpaceId(FindDeviceBySubSpaceIdQuery query);
}
