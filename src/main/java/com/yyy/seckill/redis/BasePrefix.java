package com.yyy.seckill.redis;

public abstract class BasePrefix implements KeyPrefix{

    private int expireseconds;
    private String prefix;

    public  BasePrefix(int expireseconds, String prefix){
        this.expireseconds = expireseconds;
        this.prefix = prefix;
    }
    public BasePrefix(String prefix){//0代表永不过期
        this(0,prefix);
    }

    @Override
    public int ExpireSeconds() {
        return expireseconds;
    }

    @Override
    public String getPrefix() {
        String ClassName = getClass().getSimpleName();
        return ClassName + ":" + prefix;//UserKey:id1
    }
}
