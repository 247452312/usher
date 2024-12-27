package top.uhyils.usher.rpc.filter.result;

import top.uhyils.usher.pojo.DTO.base.ServiceResult;
import top.uhyils.usher.rpc.annotation.RpcSpi;
import top.uhyils.usher.rpc.exchange.pojo.content.RpcRequestContent;
import top.uhyils.usher.rpc.netty.spi.step.template.ProviderResponseObjExtension;

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
