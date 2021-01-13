package com.yl.seckill.utils;


import com.yl.seckill.constants.PatternConstants;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Administrator
 */
public final class IdCardUtil {
    private IdCardUtil() {
    }

    /**
     * 身份证号脱敏筛选正则
     */
    private static final String CARD_BLUR_REGEX = "(\\d{6})\\d{8}(\\d{3}[0-9Xx])";

    /**
     * 身份证号脱敏替换规则
     */
    private static final String CARD_BLUR_REPLACE_REGEX = "$1********$2";

    /**
     * 身份证号格式校验
     *
     * @param idCard
     * @return
     */
    public static boolean checkIdCard(String idCard) {
        if (StringUtils.isEmpty(idCard)) {
            return false;
        }
        return idCard.matches(PatternConstants.ID_CARD);
    }

    /**
     * 身份证号脱敏处理
     *
     * @param idCard
     * @return
     */
    public static String blurIdCard(String idCard) {
        boolean checkFlag = checkIdCard(idCard);
        // 如果不是身份证号，返回原始值
        if (!checkFlag) {
            return idCard;
        }
        return idCard.replaceAll(CARD_BLUR_REGEX, CARD_BLUR_REPLACE_REGEX);
    }
}