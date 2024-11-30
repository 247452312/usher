package top.uhyils.usher.rpc.exchange.pojo.data;

import top.uhyils.usher.rpc.annotation.RpcSpi;
import top.uhyils.usher.rpc.enums.RpcTypeEnum;
import top.uhyils.usher.rpc.exchange.pojo.content.RpcRequestContentFactory;

/**
 * rpc正常请求体(默认扩展实现,如果有其他实现,请自行处理)
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年12月18日 10时52分
 */
@RpcSpi(single = false)
public class NormalRequestRpcData extends AbstractRequestRpcData {


    public NormalRequestRpcData() {
    }

    @Override
    public Integer type() {
        return RpcTypeEnum.REQUEST.getCode();
    }

    @Override
    protected void initContent() {
        this.content = RpcRequestContentFactory.createNormalByContentArray(this, this.contentArray);
    }


}
