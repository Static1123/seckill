package com.yl.seckill.redis;

/**
 * @author Administrator
 * 缓冲key前缀
 */
public interface KeyPrefix {

    /**
     * 有效期
     *
     * @return
     */
    int expireSeconds();

    /**
     * 前缀
     *
     * @return
     */
    String getPrefix();
}