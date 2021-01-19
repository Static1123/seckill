package com.yl.seckill.constants;

/**
 * @author xie.zhe
 * @date 2020-09-08
 */
public final class PatternConstants {

    private PatternConstants() {
    }

    /**
     * 手机号正则
     */
    public static final String MOBILE = "^1([3-9])\\d{9}$";

    /**
     * 身份证号
     */
    public static final String ID_CARD = "^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$";

    /**
     * 邮箱
     */
    public static final String EMAIL = "^[a-z]([a-z0-9]*[-_.]?[a-z0-9]+)*@([a-z0-9]*[-_]?[a-z0-9]+)+[\\.][a-z]{2,3}([\\.][a-z]{2})?$";

    /**
     * Base64格式
     * 1.字符串只可能包含A-Z,a-z,0-9,+,/,=字符
     * 2.字符串长度是4的倍数
     * 3.=只会出现在字符串最后,可能没有或者一个等号或者两个等号
     */
    public static final String BASE64 = "^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{4}|[A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)$";

    /**
     * 请求参数token
     */
    public static final String TOKEN_NAME = "token";
}