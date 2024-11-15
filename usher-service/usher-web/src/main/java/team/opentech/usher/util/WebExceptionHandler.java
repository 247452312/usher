package team.opentech.usher.util;

import java.util.Optional;
import java.util.concurrent.ExecutionException;
import team.opentech.usher.enums.ServiceCode;
import team.opentech.usher.exception.AssertException;
import team.opentech.usher.exception.NoLoginException;
import team.opentech.usher.exception.ServiceResultException;
import team.opentech.usher.pojo.DTO.response.WebResponse;
import team.opentech.usher.rpc.exception.RpcNetException;
import team.opentech.usher.rpc.exception.UsherRpcProviderThrowException;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2022年07月27日 09时13分
 */
public class WebExceptionHandler {

    /**
     * 遇到要返回前台的错误时如何解析
     *
     * @return
     */
    public static WebResponse onException(Throwable th) {

        // 解析线程池包装异常
        if (th instanceof ExecutionException) {
            return onException(th.getCause());
        }
        // 解析rpc包装异常
        if (th instanceof UsherRpcProviderThrowException) {
            return onException(th.getCause());
        }
        // 断言异常 返回前端
        if (th instanceof AssertException) {
            return onAssertException((AssertException) th);
        }
        //rpc返回了相关的错误码
        if (th instanceof ServiceResultException) {
            ServiceResultException resultException = (ServiceResultException) th;
            Integer errorCode = resultException.getErrorCode();
            Optional<ServiceCode> byText = ServiceCode.getByText(errorCode);
            if (!byText.isPresent()) {
                return onOtherException(th);
            }
            switch (byText.get()) {
                case ERROR:
                case REQUEST_PARAM_ERROR:
                    return onOtherException(th);
                default:
                    return WebResponse.buildWithError(resultException.getMessage(), errorCode);
            }
        }
        // 未登录异常返回前端
        if (th instanceof NoLoginException) {
            return onNoLoginException((NoLoginException) th);
        }
        // rpc网络异常返回前端
        if (th instanceof RpcNetException) {
            return onRpcNetException((Exception) th);
        }
        // 其他异常(不暴露异常信息)
        return onOtherException(th);

    }

    /**
     * 返回前端其他异常
     *
     * @param th
     *
     * @return
     */
    private static WebResponse onOtherException(Throwable th) {
        LogUtil.error(th);
        return WebResponse.buildWithError("系统异常,请联系管理员!", ServiceCode.ERROR.getText());
    }

    /**
     * 服务器错误
     *
     * @param th
     *
     * @return
     */
    private static WebResponse onRpcNetException(Exception th) {
        return WebResponse.buildWithError("网络异常:" + th.getMessage(), ServiceCode.ERROR.getText());
    }

    /**
     * 需要登录时的返回
     *
     * @param nle
     *
     * @return
     */
    private static WebResponse onNoLoginException(NoLoginException nle) {
        return WebResponse.buildWithError(nle.getMessage(), ServiceCode.NO_LOGIN__ERROR.getText());
    }

    /**
     * 断言异常
     *
     * @param ae
     *
     * @return
     */
    private static WebResponse onAssertException(AssertException ae) {
        return WebResponse.buildWithError(ae.getMessage(), ServiceCode.ASSERT_EXCEPTION.getText());
    }

}
