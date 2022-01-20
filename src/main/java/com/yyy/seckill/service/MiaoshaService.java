package com.yyy.seckill.service;

import com.yyy.seckill.pojo.order_info;
import com.yyy.seckill.pojo.seckill_user;
import com.yyy.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MiaoshaService {
    @Autowired
    GoodsService goodsService;
    @Autowired
    OrderService orderService;
    @Transactional
    public order_info miaosha(GoodsVo goodsVo, seckill_user user){
        //减少库存，下订单，写入秒杀订单
        goodsService.reduceStock(goodsVo);
        return orderService.createOrder(goodsVo, user);
    }
}
