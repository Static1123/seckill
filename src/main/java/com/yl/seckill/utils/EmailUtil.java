package com.yl.seckill.utils;


import com.yl.seckill.constants.PatternConstants;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Administrator
 */
public final class EmailUtil {
    private EmailUtil() {
    }

    /**
     * email脱敏筛选正则
     */
    private static final String EMAIL_BLUR_REGEX = "(^\\w)[^@]*(@.*$)";

    /**
     * email脱敏替换规则
     */
    private static final String EMAIL_BLUR_REPLACE_REGEX = "$1****$2";

    /**
     * email格式校验
     *
     * @param email
     * @return
     */
    public static boolean checkEmail(String email) {
        if (StringUtils.isEmpty(email)) {
            return false;
        }
        return email.matches(PatternConstants.EMAIL);
    }

    /**
     * email脱敏处理
     *
     * @param email
     * @return
     */
    public static String blurEmail(String email) {
        boolean checkFlag = checkEmail(email);
        // 如果不是email，返回原始值
        if (!checkFlag) {
            return email;
        }
        return email.replaceAll(EMAIL_BLUR_REGEX, EMAIL_BLUR_REPLACE_REGEX);
    }
}