package team.opentech.usher.rpc.filter.result;

import team.opentech.usher.pojo.DTO.base.ServiceResult;
import team.opentech.usher.rpc.annotation.RpcSpi;
import team.opentech.usher.rpc.exchange.pojo.content.RpcRequestContent;
import team.opentech.usher.rpc.netty.spi.step.template.ProviderResponseObjExtension;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2022年07月20日 10时08分
 */
@RpcSpi(name = "myProviderResponseObjExtension")
public class MyProviderResponseObjExtension implements ProviderResponseObjExtension {

    @Override
    public Object doFilter(Object obj, RpcRequestContent requestContent) {
        if (obj instanceof ServiceResult) {
            return obj;
        }
        return ServiceResult.buildSuccessResult(obj);
    }
}
