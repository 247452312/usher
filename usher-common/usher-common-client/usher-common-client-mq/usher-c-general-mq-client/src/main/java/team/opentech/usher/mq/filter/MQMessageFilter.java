package team.opentech.usher.mq.filter;

import team.opentech.usher.mq.MQMessage;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年09月02日 19时09分
 */
public interface MQMessageFilter {

    MQMessage invoke(MQMessage message);
}
