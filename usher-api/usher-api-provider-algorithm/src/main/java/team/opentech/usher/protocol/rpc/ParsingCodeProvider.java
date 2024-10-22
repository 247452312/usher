package team.opentech.usher.protocol.rpc;

import team.opentech.usher.pojo.DTO.request.ExecuteCodeRequest;
import team.opentech.usher.pojo.DTO.response.ExecuteCodeResponse;
import team.opentech.usher.protocol.rpc.base.BaseProvider;

/**
 * 解析代码的service
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月10日 08时45分
 */
public interface ParsingCodeProvider extends BaseProvider {

    /**
     * 解析代码
     *
     * @param request
     *
     * @return
     */
    ExecuteCodeResponse executeCode(ExecuteCodeRequest request);
}
