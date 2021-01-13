package com.yl.seckill.utils;


import com.yl.seckill.constants.PatternConstants;
import org.apache.commons.lang3.StringUtils;

/**
 * 手机号工具类
 *
 * @author Administrator
 */
public final class MobileUtil {

    private MobileUtil() {
    }

    /**
     * 手机号脱敏筛选正则
     */
    private static final String PHONE_BLUR_REGEX = "(\\d{3})\\d{4}(\\d{4})";

    /**
     * 手机号脱敏替换规则
     */
    private static final String MOBILE_BLUR_REPLACE_REGEX = "$1****$2";

    /**
     * 手机号格式校验
     *
     * @param phone
     * @return
     */
    public static boolean checkPhone(String phone) {
        if (StringUtils.isEmpty(phone)) {
            return false;
        }
        return phone.matches(PatternConstants.MOBILE);
    }

    /**
     * 手机号脱敏处理
     *
     * @param phone
     * @return
     */
    public static String blurPhone(String phone) {
        boolean checkFlag = checkPhone(phone);
        // 如果不是手机号，返回原始值
        if (!checkFlag) {
            return phone;
        }
        return phone.replaceAll(PHONE_BLUR_REGEX, MOBILE_BLUR_REPLACE_REGEX);
    }
}