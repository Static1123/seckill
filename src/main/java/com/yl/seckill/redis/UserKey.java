package com.yl.seckill.redis;

/**
 * @author Administrator
 */
public class UserKey extends BasePrefix {

    /**
     * 默认2小时
     */
    public static final int TOKEN_EXPIRE = 3600 * 2;

    /**
     * 防止被外面实例化
     */
    private UserKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    /**
     * 需要缓存的字段
     */
    public static UserKey token = new UserKey(TOKEN_EXPIRE, "token:");
    public static UserKey getById = new UserKey(0, "id:");
}