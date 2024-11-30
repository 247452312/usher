package top.uhyils.usher.protocol.rpc.impl;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import top.uhyils.usher.annotation.Public;
import top.uhyils.usher.pojo.DTO.request.VerificationCommand;
import top.uhyils.usher.pojo.DTO.response.VerificationGetDTO;
import top.uhyils.usher.pojo.cqe.DefaultCQE;
import top.uhyils.usher.protocol.rpc.VerificationProvider;
import top.uhyils.usher.rpc.annotation.RpcService;
import top.uhyils.usher.service.VerificationService;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年09月14日 06时34分
 */
@RpcService
public class VerificationProviderImpl implements VerificationProvider {


    @Autowired
    private VerificationService service;

    @Override
    @Public
    public VerificationGetDTO getVerification(DefaultCQE request) throws IOException {
        return service.getVerification();
    }

    @Override
    @Public
    public Boolean verification(VerificationCommand request) {
        String key = request.getKey();
        String code = request.getCode();
        return service.verification(key, code);
    }
}
