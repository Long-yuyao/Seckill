package com.yyy.seckill.redis;

public class GoodsKey extends BasePrefix{
    public static KeyPrefix getMiaoshaGoodsStock;

    private GoodsKey(int expireseconds, String prefix) {
        super(expireseconds, prefix);
    }
    public static GoodsKey getGoodsList = new GoodsKey(60,"goodslist");
    public static GoodsKey getGoodsDetail = new GoodsKey(60,"goodsdetail");
}
