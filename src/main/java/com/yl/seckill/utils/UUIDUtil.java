package com.yl.seckill.utils;

import java.util.UUID;

/**
 * @author Administrator
 * 唯一id生成类
 */
public class UUIDUtil {

    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}