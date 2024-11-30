package top.uhyils.usher.service;

import java.io.IOException;
import top.uhyils.usher.pojo.DTO.response.VerificationGetDTO;

/**
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月25日 20时27分
 */
public interface VerificationService extends BaseService {

    /**
     * 获取验证码
     *
     * @return 验证码与在redis中的key
     */
    VerificationGetDTO getVerification() throws IOException;


    /**
     * 验证码验证
     *
     * @param key
     * @param code
     *
     * @return
     */
    Boolean verification(String key, String code);
}
