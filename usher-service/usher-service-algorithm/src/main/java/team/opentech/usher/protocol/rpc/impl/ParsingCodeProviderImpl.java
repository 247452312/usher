package team.opentech.usher.protocol.rpc.impl;

import team.opentech.usher.pojo.DTO.request.ExecuteCodeRequest;
import team.opentech.usher.pojo.DTO.response.ExecuteCodeResponse;
import team.opentech.usher.protocol.rpc.ParsingCodeProvider;
import team.opentech.usher.rpc.annotation.RpcService;
import team.opentech.usher.service.ParsingCodeService;
import javax.annotation.Resource;


/**
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年09月09日 21时13分
 */
@RpcService
public class ParsingCodeProviderImpl implements ParsingCodeProvider {

    @Resource
    private ParsingCodeService service;

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest request) {
        String result = service.executeCode(request.getClassValue());
        return ExecuteCodeResponse.build(result);
    }
}
