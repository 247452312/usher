package top.uhyils.usher.pojo.DTO;

import java.io.Serializable;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年12月06日 17时26分
 */
public class AiDeviceAndRealTimeDTO implements Serializable {

    /**
     * 设备信息
     */
    private AiDeviceDTO deviceDTO;

    /**
     * 设备实时状态信息
     */
    private AiDeviceRealTimeDTO realTimeDTO;

    public AiDeviceDTO getDeviceDTO() {
        return deviceDTO;
    }

    public void setDeviceDTO(AiDeviceDTO deviceDTO) {
        this.deviceDTO = deviceDTO;
    }

    public AiDeviceRealTimeDTO getRealTimeDTO() {
        return realTimeDTO;
    }

    public void setRealTimeDTO(AiDeviceRealTimeDTO realTimeDTO) {
        this.realTimeDTO = realTimeDTO;
    }
}
