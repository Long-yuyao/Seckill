package com.yyy.seckill.redis;

public interface KeyPrefix {
    public int ExpireSeconds();
    public String getPrefix();
}
