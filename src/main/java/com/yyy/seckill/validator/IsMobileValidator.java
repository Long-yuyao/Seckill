package com.yyy.seckill.validator;

import com.yyy.seckill.utils.ValidUtil;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsMobileValidator implements ConstraintValidator<IsMobile, String> {

    private boolean required = false;

    public void initialize(IsMobile constraintAnnotation) {
        required = constraintAnnotation.required();
    }

    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(required) {
            return ValidUtil.isvalid(value);
        }else {
            if(StringUtils.isEmpty(value)) {
                return true;
            }else {
                return ValidUtil.isvalid(value);
            }
        }
    }

}
