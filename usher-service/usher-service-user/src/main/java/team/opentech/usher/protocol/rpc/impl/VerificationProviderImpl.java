package team.opentech.usher.protocol.rpc.impl;

import team.opentech.usher.annotation.Public;
import team.opentech.usher.pojo.DTO.request.VerificationCommand;
import team.opentech.usher.pojo.DTO.response.VerificationGetDTO;
import team.opentech.usher.pojo.cqe.DefaultCQE;
import team.opentech.usher.protocol.rpc.VerificationProvider;
import team.opentech.usher.rpc.annotation.RpcService;
import team.opentech.usher.service.VerificationService;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;

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
