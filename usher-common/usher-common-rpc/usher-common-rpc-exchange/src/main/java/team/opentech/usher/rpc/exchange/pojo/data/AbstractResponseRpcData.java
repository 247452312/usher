package team.opentech.usher.rpc.exchange.pojo.data;

import team.opentech.usher.rpc.enums.RpcTypeEnum;

/**
 * rpc响应体
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年12月18日 12时23分
 */
public abstract class AbstractResponseRpcData extends AbstractRpcData {

    protected AbstractResponseRpcData() {
    }

    @Override
    public void init(Object... params) {
        if (params.length != 0) {
            super.init(params);
        }
        this.type = RpcTypeEnum.RESPONSE.getCode();
    }

}
