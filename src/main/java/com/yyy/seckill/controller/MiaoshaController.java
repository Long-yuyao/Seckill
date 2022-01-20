package com.yyy.seckill.controller;

import com.yyy.seckill.pojo.order_info;
import com.yyy.seckill.pojo.seckill_user;
import com.yyy.seckill.result.CodeMsg;
import com.yyy.seckill.service.GoodsService;
import com.yyy.seckill.service.MiaoshaService;
import com.yyy.seckill.service.OrderService;
import com.yyy.seckill.service.SeckillUserService;
import com.yyy.seckill.vo.GoodsVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Service
@EnableAutoConfiguration
@RequestMapping("/miaosha")
public class MiaoshaController {
    @Autowired
    SeckillUserService seckillUserService;
    @Autowired
    GoodsService goodsService;
    @Autowired
    OrderService orderService;
    @Autowired
    MiaoshaService miaoshaService;
    @RequestMapping("/do_miaosha")
    public String do_miaosha(Model model, @RequestParam("goodsId")long goodsId, seckill_user user){
        if(user == null){
            return "to_login";
        }
        //判断库存
        GoodsVo goodsVo = goodsService.GetGoodsVoByGoodsId(goodsId);
        if(goodsVo.getStockCount() == 0){
            model.addAttribute("errormsg", CodeMsg.MIAO_SHA_OVER.getMsg());
            return "miaosha_fail";
        }
        //判断是否已经秒杀
        order_info order = orderService.GetOrderByGoodsIdUserId(goodsId,user.getId());
        if(order != null){
            model.addAttribute("errormsg", CodeMsg.REPEATE_MIAOSHA.getMsg());
            return "miaosha_fail";
        }
        //写入秒杀订单
        order_info neworder = miaoshaService.miaosha(goodsVo, user);
        model.addAttribute("orderInfo", neworder);
        model.addAttribute("goods",goodsVo);
        return "order_detail";
    }
}
