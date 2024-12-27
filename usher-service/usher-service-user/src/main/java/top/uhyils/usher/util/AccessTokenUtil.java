package top.uhyils.usher.util;

import java.security.SecureRandom;
import java.util.Base64;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年12月18日 16时48分
 */
public final class AccessTokenUtil {

    private AccessTokenUtil() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 创建一个随机的accessToken
     *
     * @return
     */
    public static String makeAccessToken() {
        byte[] keyBytes = new byte[16];
        SecureRandom random = new SecureRandom();
        random.nextBytes(keyBytes);
        return Base64.getEncoder().encodeToString(keyBytes);
    }

    /**
     * 模糊处理
     *
     * @param accessToken
     *
     * @return
     */
    public static String eraseAccessToken(String accessToken) {
        return accessToken.substring(0, 4) + accessToken.substring(4).stream().map(t -> '*').join();
    }
}
