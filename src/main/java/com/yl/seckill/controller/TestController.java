package com.yl.seckill.controller;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author Administrator
 */
@RestController
public class TestController {
    @GetMapping("/test")
    public String test() throws Exception {
        Map<String, Object> map = Maps.newHashMap();
        System.getProperties().forEach((key, value) -> {
            map.put(key + "", value);
        });
        return JSONObject.toJSONString(map);
    }
}