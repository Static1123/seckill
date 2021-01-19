package com.yl.seckill.redis;

import com.alibaba.fastjson.JSON;
import com.yl.seckill.utils.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * redis服务
 *
 * @author Administrator
 */
@Component
public class RedisService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisService.class);

    @Resource
    private RedisUtil redisUtil;

    /**
     * 从redis连接池获取redis实例
     */
    public <T> T get(KeyPrefix prefix, String key, Class<T> clazz) {
        try {
            //对key增加前缀，即可用于分类，也避免key重复
            String realKey = prefix.getPrefix() + key;
            Object result = redisUtil.get(realKey);
            if (result == null) {
                return null;
            }
            String str = String.valueOf(result);
            return stringToBean(str, clazz);
        } catch (Exception ex) {
            LOGGER.error("{}", ex.getMessage(), ex);
        }
        return null;
    }

    /**
     * 存储对象
     */
    public <T> Boolean set(KeyPrefix prefix, String key, T value) {
        try {
            String str = beanToString(value);
            if (str == null || str.length() <= 0) {
                return false;
            }
            String realKey = prefix.getPrefix() + key;
            //获取过期时间
            int seconds = prefix.expireSeconds();
            if (seconds <= 0) {
                redisUtil.set(realKey, str);
            } else {
                redisUtil.set(realKey, str, seconds, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception ex) {
            LOGGER.error("{}", ex.getMessage(), ex);
        }
        return false;
    }

    /**
     * 删除
     */
    public boolean delete(KeyPrefix prefix, String key) {
        try {
            //生成真正的key
            String realKey = prefix.getPrefix() + key;
            return redisUtil.delete(realKey);
        } catch (Exception ex) {
            LOGGER.error("{}", ex.getMessage(), ex);
        }
        return false;
    }

    /**
     * 判断key是否存在
     */
    public <T> boolean exists(KeyPrefix prefix, String key) {
        try {
            //生成真正的key
            String realKey = prefix.getPrefix() + key;
            return redisUtil.hasKey(realKey);
        } catch (Exception ex) {
            LOGGER.error("{}", ex.getMessage(), ex);
        }
        return false;
    }

    /**
     * 增加值
     * Redis Incr 命令将 key 中储存的数字值增一。    如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 INCR 操作
     */
    public <T> Long incr(KeyPrefix prefix, String key) {
        try {
            //生成真正的key
            String realKey = prefix.getPrefix() + key;
            return redisUtil.incr(realKey);
        } catch (Exception ex) {
            LOGGER.error("{}", ex.getMessage(), ex);
        }
        return null;
    }

    /**
     * 减少值
     */
    public <T> Long decr(KeyPrefix prefix, String key) {
        try {
            //生成真正的key
            String realKey = prefix.getPrefix() + key;
            return redisUtil.decrement(realKey);
        } catch (Exception ex) {
            LOGGER.error("{}", ex.getMessage(), ex);
        }
        return null;
    }


    public static <T> String beanToString(T value) {
        if (value == null) {
            return null;
        }
        Class<?> clazz = value.getClass();
        if (clazz == int.class || clazz == Integer.class) {
            return String.valueOf(value);
        } else if (clazz == long.class || clazz == Long.class) {
            return String.valueOf(value);
        } else if (clazz == String.class) {
            return (String) value;
        } else {
            return JSON.toJSONString(value);
        }
    }

    public static <T> T stringToBean(String str, Class<T> clazz) {
        if (str == null || str.length() <= 0 || clazz == null) {
            return null;
        }
        if (clazz == int.class || clazz == Integer.class) {
            return (T) Integer.valueOf(str);
        } else if (clazz == long.class || clazz == Long.class) {
            return (T) Long.valueOf(str);
        } else if (clazz == String.class) {
            return (T) str;
        } else {
            return JSON.toJavaObject(JSON.parseObject(str), clazz);
        }
    }
}