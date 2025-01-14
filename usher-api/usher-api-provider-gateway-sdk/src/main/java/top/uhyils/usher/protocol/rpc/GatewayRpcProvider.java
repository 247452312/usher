package top.uhyils.usher.protocol.rpc;

import java.util.List;
import java.util.Map;
import top.uhyils.usher.pojo.cqe.SdkSqlInvokeCommand;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2022年08月12日 08时55分
 */
public interface GatewayRpcProvider {

    /**
     * 执行远程请求
     *
     * @param command
     *
     * @return
     */
    List<Map<String, Object>> invokeRpc(SdkSqlInvokeCommand command);
}
