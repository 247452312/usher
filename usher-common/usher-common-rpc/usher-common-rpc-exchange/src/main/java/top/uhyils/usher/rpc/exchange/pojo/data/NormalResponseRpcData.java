package top.uhyils.usher.rpc.exchange.pojo.data;

import top.uhyils.usher.rpc.annotation.RpcSpi;
import top.uhyils.usher.rpc.enums.RpcTypeEnum;
import top.uhyils.usher.rpc.exchange.pojo.content.RpcResponseContentFactory;

/**
 * rpc正常响应
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年12月18日 13时34分
 */
@RpcSpi(single = false)
public class NormalResponseRpcData extends AbstractResponseRpcData {


    @Override
    public Integer type() {
        return RpcTypeEnum.RESPONSE.getCode();
    }

    @Override
    protected void initContent() {
        this.content = RpcResponseContentFactory.createByContentArray(this, this.contentArray);
    }

}
