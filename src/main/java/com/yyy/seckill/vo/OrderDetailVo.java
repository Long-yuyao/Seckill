package com.yyy.seckill.vo;

import com.yyy.seckill.pojo.order_info;

public class OrderDetailVo {
    private GoodsVo goods;
    private order_info order;

    public GoodsVo getGoods() {
        return goods;
    }

    public void setGoods(GoodsVo goods) {
        this.goods = goods;
    }

    public order_info getOrder() {
        return order;
    }

    public void setOrder(order_info order) {
        this.order = order;
    }
}
