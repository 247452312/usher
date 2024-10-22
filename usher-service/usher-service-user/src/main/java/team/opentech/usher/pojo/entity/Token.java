package team.opentech.usher.pojo.entity;

import team.opentech.usher.context.UsherContext;
import team.opentech.usher.enums.UserTypeEnum;
import team.opentech.usher.pojo.DO.base.TokenInfo;
import team.opentech.usher.pojo.entity.base.AbstractEntity;
import team.opentech.usher.pojo.entity.type.Identifier;
import team.opentech.usher.repository.UserRepository;
import team.opentech.usher.util.AESUtil;
import team.opentech.usher.util.Asserts;
import team.opentech.usher.util.SpringUtil;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * token本身
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月24日 18时26分
 */
public class Token extends AbstractEntity<String> {

    /**
     * 这个token对应的id
     */
    private final Identifier id;

    public Token(Long id, String token) {
        this.id = new Identifier(id);
        this.unique = token;

    }

    public Token(String token) {
        this.unique = token;
        this.id = parseTokenToId(token);
    }

    /**
     * 获取此token对应的用户类型
     *
     * @return
     */
    public UserTypeEnum tokenUserType() {
        return UserTypeEnum.getByCode(this.unique.substring(0, 2)).orElse(null);
    }

    public Identifier getId() {
        return id;
    }

    public String getToken() {
        return unique;
    }

    public TokenInfo parseToTokenInfo(String encodeRules, String salt, UserRepository userRepository) {
        String tokenInfoString = AESUtil.AESDecode(encodeRules, unique);

        assert tokenInfoString != null;
        String day = tokenInfoString.substring(0, 2);
        String hour = tokenInfoString.substring(2, 4);
        String mon = tokenInfoString.substring(4, 6);
        String sec = tokenInfoString.substring(6, 8);
        String random = tokenInfoString.substring(8, 10);

        long userId = Long.parseLong(tokenInfoString.substring(10, tokenInfoString.length() - 1 - salt.length()));

        TokenInfo tokenInfo = new TokenInfo();
        tokenInfo.setDay(Integer.parseInt(day));
        tokenInfo.setHour(Integer.parseInt(hour));
        tokenInfo.setMin(Integer.parseInt(mon));
        tokenInfo.setSec(Integer.parseInt(sec));
        tokenInfo.setRandom(Integer.parseInt(random));
        tokenInfo.setUserId(userId);
        Boolean aBoolean = userRepository.haveToken(this);
        if (aBoolean == null) {
            LocalDateTime localDateTime = LocalDateTime.now();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("ddhhmm");
            String format = localDateTime.format(dateTimeFormatter);
            int dayNow = Integer.parseInt(format.substring(0, 2));
            int hourNow = Integer.parseInt(format.substring(2, 4));
            int monNow = Integer.parseInt(format.substring(4, 6));
            // 如果分钟差超过30
            if (monNow - Integer.parseInt(mon) >= UsherContext.LOGIN_TIME_OUT_MIN) {
                tokenInfo.setTimeOut(Boolean.TRUE);
            } else if (hourNow - Integer.parseInt(hour) > 0) {
                tokenInfo.setTimeOut(Boolean.TRUE);
            } else if (dayNow - Integer.parseInt(day) > 0) {
                tokenInfo.setTimeOut(Boolean.TRUE);
            } else {
                tokenInfo.setTimeOut(false);
            }
        } else {
            tokenInfo.setTimeOut(!aBoolean);
        }
        return tokenInfo;
    }

    private Identifier parseTokenToId(String token) {
        UserTypeEnum userTypeEnum = tokenUserType();
        Asserts.assertTrue(userTypeEnum == UserTypeEnum.USER, "token不是用户类型");
        String salt = SpringUtil.getProperty("token.salt");
        assert salt != null;
        String encodeRules = SpringUtil.getProperty("token.encodeRules");
        assert encodeRules != null;
        String tokenInfoString = AESUtil.AESDecode(encodeRules, token);
        assert tokenInfoString != null;
        long id = Long.parseLong(tokenInfoString.substring(10, tokenInfoString.length() - 1 - salt.length()));
        return new Identifier(id);
    }
}
