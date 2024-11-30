package top.uhyils.usher.rpc.exception;

/**
 * rpc对方报错
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2022年06月27日 09时29分
 */
public class UsherRpcProviderThrowException extends RpcException {


    public UsherRpcProviderThrowException(String message) {
        super(message);
    }

    public UsherRpcProviderThrowException(Throwable th) {
        super(th);
    }

}
