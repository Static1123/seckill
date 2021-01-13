package com.yl.seckill.validator;


import com.yl.seckill.utils.MobileUtil;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Administrator
 * 自定义手机格式校验器
 */
public class IsMobileValidator implements ConstraintValidator<IsMobile, String> {

    private boolean required = false;

    @Override
    public void initialize(IsMobile isMobile) {
        required = isMobile.required();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (required) {
            return MobileUtil.checkPhone(value);
        } else {
            if (StringUtils.isEmpty(value)) {
                return true;
            } else {
                return MobileUtil.checkPhone(value);
            }
        }
    }
}