package com.yyy.seckill.redis;

public class SeckillUserKey extends BasePrefix{

    public static final int TOKEN_EXPIRE = 3600*24 * 2;
    public SeckillUserKey(int expireseconds, String prefix) {
        super(expireseconds, prefix);
    }

    public SeckillUserKey(String prefix) {
        super(prefix);
    }
    public static SeckillUserKey token = new SeckillUserKey(TOKEN_EXPIRE,"token");
    public static SeckillUserKey GetById = new SeckillUserKey(0,"id");
}
