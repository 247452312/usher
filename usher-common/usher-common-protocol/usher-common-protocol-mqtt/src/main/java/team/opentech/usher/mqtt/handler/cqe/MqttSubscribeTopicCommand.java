package team.opentech.usher.mqtt.handler.cqe;

import java.util.List;
import team.opentech.usher.pojo.cqe.command.base.AbstractCommand;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年09月11日 20时02分
 */
public class MqttSubscribeTopicCommand extends AbstractCommand {

    private List<MqttTopicItem> items;

    public List<MqttTopicItem> getItems() {
        return items;
    }

    public void setItems(List<MqttTopicItem> items) {
        this.items = items;
    }

}
