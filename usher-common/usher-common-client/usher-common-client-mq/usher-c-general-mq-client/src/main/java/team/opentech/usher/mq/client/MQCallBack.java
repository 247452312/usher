package team.opentech.usher.mq.client;

import team.opentech.usher.mq.pojo.BackInfo;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年09月02日 19时44分
 */
public interface MQCallBack {


    /**
     * 处理回调
     *
     * @param backInfo
     */
    void invoke(BackInfo backInfo);
}
