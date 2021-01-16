package com.yl.seckill.controller;


import com.yl.seckill.service.UserService;
import com.yl.seckill.vo.LoginVo;
import com.yl.seckill.vo.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;


/**
 * @author Administrator
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    @Resource
    private UserService userService;


    @RequestMapping("/to_login")
    public String toLogin() {
        return "login";
    }

    @RequestMapping("/do_login")
    @ResponseBody
    public Result<String> doLogin(HttpServletResponse response, HttpServletRequest request, @Valid LoginVo loginVo) {//加入JSR303参数校验
        String token = userService.login(response, request, loginVo);
        return Result.success(token);
    }
}