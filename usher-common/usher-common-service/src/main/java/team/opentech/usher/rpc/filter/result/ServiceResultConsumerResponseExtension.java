package team.opentech.usher.rpc.filter.result;

import team.opentech.usher.pojo.DTO.base.ServiceResult;
import team.opentech.usher.rpc.annotation.RpcSpi;
import team.opentech.usher.rpc.exchange.pojo.data.RpcData;
import team.opentech.usher.rpc.netty.spi.step.template.ConsumerResponseObjectExtension;

/**
 * 关于serviceResult 返回值客户端处理
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2022年07月20日 09时17分
 */
@RpcSpi(name = "serviceResultConsumerResponseExtension")
public class ServiceResultConsumerResponseExtension implements ConsumerResponseObjectExtension {

    @Override
    public Object doFilter(Object obj, RpcData rpcData) {
        if (obj instanceof ServiceResult) {
            ServiceResult<?> serviceResult = (ServiceResult<?>) obj;
            return serviceResult.validationAndGet();
        }
        return obj;
    }
}
