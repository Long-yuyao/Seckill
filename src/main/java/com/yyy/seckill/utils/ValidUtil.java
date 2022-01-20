package com.yyy.seckill.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class ValidUtil {
    private static final Pattern mobile_pattern = Pattern.compile("1\\d{10}");
    public static boolean isvalid(String mobile){
        if(StringUtils.isEmpty(mobile)) {
            return false;
        }
        Matcher m = mobile_pattern.matcher(mobile);
        return m.matches();
    }

    public static void main(String[] args) {
        System.out.println(ValidUtil.isvalid("123456789101"));
    }
}
