package com.yyy.seckill.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

@Component
public class MD5Util {
    public static String md5(String src){
        return DigestUtils.md5Hex(src);
    }

    private static final String salt = "1a2b3c4d";

    public static String InputPassToFromPass(String inputpass){
        String str = ""+salt.charAt(0)+salt.charAt(2) + inputpass +salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }
    //此处salt为数据库获取值
    public static String FromPassToDBPass(String frompass, String salt){
        String str = ""+salt.charAt(0)+salt.charAt(2) + frompass +salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }

    public static String InputpassToDBPass(String inputpass, String salt){
        String s = InputPassToFromPass(inputpass);
        return FromPassToDBPass(s, salt);
    }

    public static void main(String[] args) {
        System.out.println(InputPassToFromPass("123456"));
        String s1 = FromPassToDBPass("d3b1294a61a07da9b49b6e22b2cbd7f9","1a2b3c4d");
        String s2 = InputpassToDBPass("123456", "1a2b3c4d");
        System.out.println(s1.equals(s2));
        System.out.println(s2);
    }
}
