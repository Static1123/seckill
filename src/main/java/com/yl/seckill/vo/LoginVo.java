package com.yl.seckill.vo;


import com.yl.seckill.model.BaseObject;
import com.yl.seckill.validator.IsMobile;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author Administrator
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString
public class LoginVo extends BaseObject {

    @NotNull
    @IsMobile
    private String mobile;

    @NotNull
    @NotEmpty
    private String password;

    @NotNull
    @NotEmpty
    private String verifyCode;
}