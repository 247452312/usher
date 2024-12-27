package top.uhyils.usher.protocol.rpc.impl;

import org.springframework.beans.factory.annotation.Autowired;
import top.uhyils.usher.pojo.DTO.request.ExecuteCodeRequest;
import top.uhyils.usher.pojo.DTO.response.ExecuteCodeResponse;
import top.uhyils.usher.protocol.rpc.ParsingCodeProvider;
import top.uhyils.usher.rpc.annotation.RpcService;
import top.uhyils.usher.service.ParsingCodeService;


/**
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年09月09日 21时13分
 */
@RpcService
public class ParsingCodeProviderImpl implements ParsingCodeProvider {

    @Autowired
    private ParsingCodeService service;

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest request) {
        String result = service.executeCode(request.getClassValue());
        return ExecuteCodeResponse.build(result);
    }
}
