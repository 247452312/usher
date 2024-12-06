package top.uhyils.usher.pojo.entity.device.core;

import com.alibaba.fastjson.JSON;
import java.util.Map;
import top.uhyils.usher.pojo.DTO.AiDeviceDTO;
import top.uhyils.usher.pojo.entity.link.Link;
import top.uhyils.usher.util.IdUtil;
import top.uhyils.usher.util.SpringUtil;

/**
 * 持有远程链接的设备模板, 设备信息和链接两方面构成了一个设备实例
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年12月05日 08时47分
 */
public abstract class AbstractLinkDevice extends AbstractDevice {


    private final Link link;

    public AbstractLinkDevice(Map<String, Device> obvDeviceMap, AiDeviceDTO deviceDTO, Link link) {
        this(obvDeviceMap, deviceDTO.getUniqueMark(), true, deviceDTO, link);
    }

    public AbstractLinkDevice(Map<String, Device> obvDeviceMap, Link link) {
        this(obvDeviceMap, Long.toString(SpringUtil.getBean(IdUtil.class).newId()), link);
    }

    public AbstractLinkDevice(Map<String, Device> obvDeviceMap, String unique, Link link) {
        this(obvDeviceMap, unique, false, null, link);
    }

    protected AbstractLinkDevice(Map<String, Device> obvDeviceMap, String uniqueMark, Boolean aiDevice, AiDeviceDTO deviceDTO, Link link) {
        super(obvDeviceMap, uniqueMark, aiDevice, deviceDTO);
        this.link = link;
        this.link.setOnMessageFunction(this::onMessage);
    }

    @Override
    public void start() {
        link.tryLink();
    }

    @Override
    public String ip() {
        return link.ip();
    }

    @Override
    public void request(String request) {
        link.request(request);
    }

    @Override
    public String requestSync(String request) {
        Object res = link.requestSync(request);
        return JSON.toJSONString(res);
    }

    /**
     * 消息来临时接收
     *
     * @param msg
     */
    protected abstract void onMessage(String msg);
}
