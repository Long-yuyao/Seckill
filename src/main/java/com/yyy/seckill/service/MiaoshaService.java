package com.yyy.seckill.service;

import com.yyy.seckill.pojo.order_info;
import com.yyy.seckill.pojo.seckill_user;
import com.yyy.seckill.redis.MiaoshaKey;
import com.yyy.seckill.redis.RedisService;
import com.yyy.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MiaoshaService {
    @Autowired
    GoodsService goodsService;
    @Autowired
    OrderService orderService;
    @Autowired
    RedisService redisService;
    @Transactional
    public order_info miaosha(GoodsVo goodsVo, seckill_user user){
        //减少库存，下订单，写入秒杀订单
        goodsService.reduceStock(goodsVo);
        return orderService.createOrder(goodsVo, user);
    }

    public boolean checkPath(seckill_user user, long goodsId, String path) {
        if(user == null || path == null) {
            return false;
        }
        String pathOld = redisService.Get(MiaoshaKey.getMiaoshaPath, ""+user.getId() + "_"+ goodsId, String.class);
        return path.equals(pathOld);
    }

    public void reset(List<GoodsVo> goodsList) {
        goodsService.resetStock(goodsList);
        orderService.deleteOrders();
    }
}
