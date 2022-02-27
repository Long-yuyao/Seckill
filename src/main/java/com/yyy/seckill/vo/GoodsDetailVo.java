package com.yyy.seckill.vo;

import com.yyy.seckill.pojo.seckill_user;

public class GoodsDetailVo {
    private int miaoshaStatus;
    private int remainSeconds;
    private GoodsVo goods;
    private seckill_user user;

    public seckill_user getSeckilluser() {
        return user;
    }

    public void setSeckilluser(seckill_user seckilluser) {
        this.user = seckilluser;
    }

    public int getMiaoshaStatus() {
        return miaoshaStatus;
    }

    public void setMiaoshaStatus(int miaoshaStatus) {
        this.miaoshaStatus = miaoshaStatus;
    }

    public int getRemainSeconds() {
        return remainSeconds;
    }

    public void setRemainSeconds(int remainSeconds) {
        this.remainSeconds = remainSeconds;
    }

    public GoodsVo getGoods() {
        return goods;
    }

    public void setGoods(GoodsVo goods) {
        this.goods = goods;
    }
}
