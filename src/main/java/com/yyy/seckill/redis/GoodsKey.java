package com.yyy.seckill.redis;

public class GoodsKey extends BasePrefix{
    private GoodsKey(int expireseconds, String prefix) {
        super(expireseconds, prefix);
    }
    public static GoodsKey getGoodsList = new GoodsKey(60,"goodslist");
}
