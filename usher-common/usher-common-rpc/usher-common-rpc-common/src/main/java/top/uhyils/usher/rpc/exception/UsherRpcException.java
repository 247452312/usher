package top.uhyils.usher.rpc.exception;

/**
 * rpc异常
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年12月18日 11时30分
 */
public class UsherRpcException extends RpcException {

    public UsherRpcException() {
        super("请求协议不是UsherRpc");
    }
}
