package com.yl.seckill.utils;

import org.apache.commons.lang3.RandomUtils;

/**
 * @author Administrator
 */
public final class PasswordUtil {
    private PasswordUtil() {
    }

    /**
     * 生成随机密码
     *
     * @param length 密码长度
     * @return
     */
    public static String generatePwd(int length) {
        if (length <= 0) {
            return "";
        }
        String[] result = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b",
                "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q",
                "r", "s", "t", "u", "v", "w", "x", "y", "z", "A", "B",
                "C", "D", "E", "F", "G", "H", "I", "G", "K", "L", "M", "N", "O", "P", "Q",
                "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "!", "@", "#", "$", "%", "^",
                "&", "*", "(", ")", "-", "_", "+", "=", "<", ">"};
        int len = result.length;
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = RandomUtils.nextInt(0, len - 1);
            stringBuilder.append(result[index]);
        }
        return stringBuilder.toString();
    }
}