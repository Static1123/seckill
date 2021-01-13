package com.yl.seckill.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author Administrator
 */
public final class EncryptUtil {
    /**
     * 密码加密
     *
     * @param content 输入内容(明文,不作任何处理)
     * @return
     */
    public static String encryptPassword(String content) {
        if (StringUtils.isEmpty(content)) {
            return content;
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(content);
    }

    /**
     * 匹配密文
     *
     * @param input     输入内容(明文,不作任何处理)
     * @param dbContent 数据库中存储的密文
     * @return
     */
    public static Boolean matchesPassword(String input, String dbContent) {
        if (StringUtils.isEmpty(input) || StringUtils.isEmpty(dbContent)) {
            return false;
        }
        input = EncryptUtil.encryptPassword(input);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(input, dbContent);
    }
}