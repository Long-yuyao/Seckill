package com.yyy.seckill.redis;

public class OderKey extends BasePrefix{
    public OderKey(int expireseconds, String prefix) {
        super(expireseconds, prefix);
    }
}
