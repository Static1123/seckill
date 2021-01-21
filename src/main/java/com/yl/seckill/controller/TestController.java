package com.yl.seckill.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Administrator
 */
@RestController
public class TestController {
    @GetMapping("/test")
    public String test() throws Exception {
        return "this is a test";
    }
}