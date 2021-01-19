package com.yl.seckill.controller;


import com.yl.seckill.service.UserService;
import com.yl.seckill.vo.LoginVo;
import com.yl.seckill.vo.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


/**
 * @author Administrator
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    @Resource
    private UserService userService;


    @RequestMapping("/toLogin")
    public String toLogin() {
        return "login";
    }

    @RequestMapping("/doLogin")
    @ResponseBody
    public Result<String> doLogin(HttpServletRequest request, @Valid LoginVo loginVo) {
        String token = userService.login(request, loginVo);
        return Result.success(token);
    }
}