package top.uhyils.usher.protocol.rpc;

import java.io.IOException;
import top.uhyils.usher.pojo.DTO.request.VerificationCommand;
import top.uhyils.usher.pojo.DTO.response.VerificationGetDTO;
import top.uhyils.usher.pojo.cqe.DefaultCQE;
import top.uhyils.usher.protocol.rpc.base.BaseProvider;

/**
 * 验证码类
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年09月14日 06时28分
 */
public interface VerificationProvider extends BaseProvider {

    /**
     * 获取验证码
     *
     * @param request 默认请求
     *
     * @return 验证码与在redis中的key
     */
    VerificationGetDTO getVerification(DefaultCQE request) throws IOException;


    /**
     * 验证码验证
     *
     * @param request
     *
     * @return
     */
    Boolean verification(VerificationCommand request);

}
