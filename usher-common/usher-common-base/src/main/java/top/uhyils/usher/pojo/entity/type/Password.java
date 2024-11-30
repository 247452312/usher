package top.uhyils.usher.pojo.entity.type;

import java.io.Serializable;
import top.uhyils.usher.util.AESUtil;
import top.uhyils.usher.util.Asserts;
import top.uhyils.usher.util.SpringUtil;
import top.uhyils.usher.util.StringUtil;

/**
 * 密码
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月24日 17时34分
 */
public class Password implements Serializable {

    private final String password;

    public Password(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    /**
     * 转AES编码
     *
     * @return
     */
    public String encode() {
        String encodeRules = SpringUtil.getProperty("token.encodeRules", "usher");
        Asserts.assertTrue(StringUtil.isNotEmpty(encodeRules), "encodeRules没有配置");
        return AESUtil.AESEncode(encodeRules, mixedSalt(password));
    }

    /**
     * 通过AES解码
     *
     * @return
     */
    public String decode() {
        String encodeRules = SpringUtil.getProperty("token.encodeRules", "usher");
        Asserts.assertTrue(StringUtil.isNotEmpty(encodeRules), "encodeRules没有配置");
        String decodePassword = AESUtil.AESDecode(encodeRules, password);
        return cleanSalt(decodePassword);
    }

    /**
     * 混合盐
     *
     * @return
     */
    private String mixedSalt(String password) {
        String salt = SpringUtil.getProperty("token.salt", "usher");
        return password + salt;
    }

    /**
     * 清除盐
     *
     * @return
     */
    private String cleanSalt(String password) {
        String salt = SpringUtil.getProperty("token.salt", "usher");
        Asserts.assertTrue(StringUtil.isNotEmpty(salt));
        return password.substring(0, password.length() - salt.length());
    }


}
