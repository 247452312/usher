package top.uhyils.usher.pojo.entity;

import top.uhyils.usher.pojo.DTO.AiDeviceDTO;
import top.uhyils.usher.util.IdUtil;
import top.uhyils.usher.util.SpringUtil;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年12月02日 18时32分
 */
public abstract class AbstractDevice implements Device {

    /**
     * 设备id,从控制层获取, 如果没有,则临时生成一个,等到设备创建成功后,再设置
     */
    private String id;

    private Boolean aiDevice;

    public AbstractDevice(String id, Boolean aiDevice) {
        this.id = id;
        this.aiDevice = aiDevice;
    }

    public AbstractDevice() {
        this.id = Long.toString(SpringUtil.getBean(IdUtil.class).newId());
        this.aiDevice = false;
    }

    @Override
    public String id() {
        return id;
    }

    @Override
    public boolean aiDevice() {
        return aiDevice;
    }

    @Override
    public void changeByDevice(AiDeviceDTO deviceDTO) {
        this.id = deviceDTO.getId().toString();
        this.aiDevice = true;
    }
}
