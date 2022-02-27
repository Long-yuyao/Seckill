package com.yyy.seckill.rabbitmq;

import com.yyy.seckill.pojo.seckill_user;

public class MiaoshaMessage {
    private seckill_user user;
    private long goodsId;
    public seckill_user getUser() {
        return user;
    }
    public void setUser(seckill_user user) {
        this.user = user;
    }
    public long getGoodsId() {
        return goodsId;
    }
    public void setGoodsId(long goodsId) {
        this.goodsId = goodsId;
    }
}
