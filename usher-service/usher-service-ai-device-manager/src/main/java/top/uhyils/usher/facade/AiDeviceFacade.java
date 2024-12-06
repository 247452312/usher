package top.uhyils.usher.facade;

import top.uhyils.usher.pojo.DTO.AiDeviceAndRealTimeDTO;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年12月03日 08时42分
 */
public interface AiDeviceFacade extends BaseFacade {

    /**
     * 根据唯一标示获取设备信息
     *
     * @param uniqueMark
     *
     * @return
     */
    AiDeviceAndRealTimeDTO findByUniqueMark(String uniqueMark);
}
