package team.opentech.usher.log.filter.mq;

import team.opentech.usher.pojo.other.RpcTraceInfo;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年09月02日 19时17分
 */
public class MqSendInfo {


    private String json;

    private RpcTraceInfo rpcInfo;

    public static MqSendInfo build(String json, RpcTraceInfo rpcInfo) {
        MqSendInfo build = new MqSendInfo();
        build.json = json;
        build.rpcInfo = rpcInfo;
        return build;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public RpcTraceInfo getRpcInfo() {
        return rpcInfo;
    }

    public void setRpcInfo(RpcTraceInfo rpcInfo) {
        this.rpcInfo = rpcInfo;
    }

}
