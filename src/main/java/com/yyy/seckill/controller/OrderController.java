package com.yyy.seckill.controller;

import com.yyy.seckill.pojo.order_info;
import com.yyy.seckill.pojo.seckill_user;
import com.yyy.seckill.redis.RedisService;
import com.yyy.seckill.result.CodeMsg;
import com.yyy.seckill.result.Result;
import com.yyy.seckill.service.GoodsService;
import com.yyy.seckill.service.OrderService;
import com.yyy.seckill.service.SeckillUserService;
import com.yyy.seckill.vo.GoodsVo;
import com.yyy.seckill.vo.OrderDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    SeckillUserService userService;

    @Autowired
    RedisService redisService;

    @Autowired
    OrderService orderService;

    @Autowired
    GoodsService goodsService;

    @RequestMapping("/detail")
    @ResponseBody
    public Result<OrderDetailVo> info(Model model, seckill_user user,
                                      @RequestParam("orderId") long orderId) {
        if(user == null) {
            return Result.Fail(CodeMsg.SESSION_ERROR);
        }
        order_info order = orderService.getOrderById(orderId);
        if(order == null) {
            return Result.Fail(CodeMsg.ORDER_NOT_EXIST);
        }
        long goodsId = order.getGoodsId();
        GoodsVo goods = goodsService.GetGoodsVoByGoodsId(goodsId);
        OrderDetailVo vo = new OrderDetailVo();
        vo.setOrder(order);
        vo.setGoods(goods);
        return Result.Success(vo);
    }
}
