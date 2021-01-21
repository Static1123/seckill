package com.yl.seckill.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Administrator
 */
public class MD5Util {
    private static final Logger LOGGER = LoggerFactory.getLogger(MD5Util.class);

    public static String md5(String src) {
        return DigestUtils.md5Hex(src);
    }

    /**
     * 密码加盐
     */
    private static final String SALT = "com.yl.seckill";

    /**
     * 第一次MD5加密，用于网络传输
     *
     * @param inputPass
     * @return
     */
    public static String inputPassToFormPass(String inputPass) {
        //避免在网络传输被截取然后反推出密码，所以在md5加密前先打乱密码
        String str = "" + SALT.charAt(0) + SALT.charAt(2) + inputPass + SALT.charAt(5) + SALT.charAt(4);
        return md5(str);
    }

    /**
     * 第二次MD5加密，用于存储到数据库
     *
     * @param formPass
     * @param salt
     * @return
     */
    public static String formPassToDBPass(String formPass, String salt) {
        String str = "" + salt.charAt(0) + salt.charAt(2) + formPass + salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }

    /**
     * 合并
     *
     * @param input
     * @param salt
     * @return
     */
    public static String inputPassToDbPass(String input, String salt) {
        String formPass = inputPassToFormPass(input);
        String dbPass = formPassToDBPass(formPass, salt);
        return dbPass;
    }

    public static void main(String[] args) {
        String pwd = PasswordUtil.generatePwd(12);
        LOGGER.info("{}", pwd);
        LOGGER.info("{}", inputPassToDbPass(pwd, SALT));
    }
}