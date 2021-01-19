package com.yl.seckill.controller;


import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import com.yl.seckill.service.UserService;
import com.yl.seckill.vo.CodeMsg;
import com.yl.seckill.vo.LoginVo;
import com.yl.seckill.vo.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;


/**
 * @author Administrator
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    @Resource
    private UserService userService;

    /**
     * 定义图形验证码的长、宽、验证码字符数、干扰元素个数
     */
    private static final CircleCaptcha CAPTCHA = CaptchaUtil.createCircleCaptcha(200, 100, 5, 30);

    private static final String VERIFY_CODE = "verify_code";


    @RequestMapping(value = "/toLogin", method = RequestMethod.GET)
    public String toLogin() {
        return "login";
    }

    @RequestMapping(value = "/doLogin", method = RequestMethod.POST)
    @ResponseBody
    public Result<String> doLogin(HttpServletRequest request, HttpSession session, @Valid LoginVo loginVo) {
        String verifyCode = loginVo.getVerifyCode();
        if (!verifyCode.equalsIgnoreCase(String.valueOf(session.getAttribute(VERIFY_CODE)))) {
            return Result.error(CodeMsg.VALID_CODE_ERROR);
        }
        String token = userService.login(request, loginVo);
        return Result.success(token);
    }

    @RequestMapping(value = "/verifyCode", method = RequestMethod.GET)
    public void verifyCode(HttpSession session, HttpServletResponse response) throws IOException {
        CAPTCHA.createCode();
        String code = CAPTCHA.getCode();
        session.setAttribute(VERIFY_CODE, code);
        //图形验证码写出，可以写出到文件，也可以写出到流
        CAPTCHA.write(response.getOutputStream());
    }
}