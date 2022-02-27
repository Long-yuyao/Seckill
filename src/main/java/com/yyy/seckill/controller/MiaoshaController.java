package com.yyy.seckill.controller;

import com.yyy.seckill.pojo.order_info;
import com.yyy.seckill.pojo.seckill_user;
import com.yyy.seckill.rabbitmq.MQsender;
import com.yyy.seckill.rabbitmq.MiaoshaMessage;
import com.yyy.seckill.redis.*;
import com.yyy.seckill.result.CodeMsg;
import com.yyy.seckill.result.Result;
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
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

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
    @Autowired
    RedisService redisService;
    @Autowired
    MQsender sender;
    private static volatile boolean isGlobalActivityOver = false;
    private HashMap<Long, Boolean> localOverMap =  new HashMap<Long, Boolean>();

    /**
     * 系统初始化
     * */
    public void afterPropertiesSet() throws Exception {
        List<GoodsVo> goodsList = goodsService.ListGoodsVo();
        if(goodsList == null) {
            return;
        }
        for(GoodsVo goods : goodsList) {
            redisService.Set(GoodsKey.getMiaoshaGoodsStock, ""+goods.getId(), goods.getStockCount());
            localOverMap.put(goods.getId(), false);
        }
    }

    /**通过管理后台设置一个全局秒杀结束的标志，防止数据库、redis、rabbitmq等发生意外，活动无法结束**/
    public static void setGlobalActivityOver() {
        isGlobalActivityOver = true;
    }

    public static boolean isGlobalActivityOver() {
        return isGlobalActivityOver;
    }

    @RequestMapping(value="/reset", method=RequestMethod.GET)
    @ResponseBody
    public Result<Boolean> reset(Model model) {
        List<GoodsVo> goodsList = goodsService.ListGoodsVo();
        for(GoodsVo goods : goodsList) {
            goods.setStockCount(10);
            redisService.Set(GoodsKey.getMiaoshaGoodsStock, ""+goods.getId(), 10);
            localOverMap.put(goods.getId(), false);
        }
        redisService.delete(OderKey.getMiaoshaOrderByUidGid);
        redisService.delete(MiaoshaKey.isGoodsOver);
        miaoshaService.reset(goodsList);
        return Result.Success(true);
    }

    /**
     * QPS:1306
     * 5000 * 10
     * QPS: 2114
     * */
    @RequestMapping(value="/{path}/do_miaosha", method=RequestMethod.POST)
    @ResponseBody
    public Result<Integer> miaosha(Model model,seckill_user user,
                                   @RequestParam("goodsId")long goodsId,
                                   @PathVariable("path") String path) {
        model.addAttribute("user", user);
        if (user == null) {
            return Result.Fail(CodeMsg.SESSION_ERROR);
        }
        if (isGlobalActivityOver()) {
            return Result.Fail(CodeMsg.MIAO_SHA_OVER);
        }
        //验证path
        boolean check = miaoshaService.checkPath(user, goodsId, path);
        if (!check) {
            return Result.Fail(CodeMsg.REQUEST_ILLEGAL);
        }
        //内存标记，减少redis访问
        boolean over = localOverMap.get(goodsId);
        if (over) {
            return Result.Fail(CodeMsg.MIAO_SHA_OVER);
        }
        //预减库存
        long stock = redisService.decr(GoodsKey.getMiaoshaGoodsStock, "" + goodsId);//10
        if (stock < 0) {
            localOverMap.put(goodsId, true);
            return Result.Fail(CodeMsg.MIAO_SHA_OVER);
        }
        //判断是否已经秒杀到了
        order_info order = orderService.GetOrderByGoodsIdUserId(user.getId(), goodsId);
        if (order != null) {
            return Result.Fail(CodeMsg.REPEATE_MIAOSHA);
        }
        //入队
        MiaoshaMessage mm = new MiaoshaMessage();
        mm.setUser(user);
        mm.setGoodsId(goodsId);
        sender.sendMiaoshaMessage(mm);
        return Result.Success(0);//排队中
    }
//    @RequestMapping(value = "/do_miaosha", method = RequestMethod.POST)
//    @ResponseBody
//    public Result<order_info> do_miaosha(Model model, @RequestParam("goodsId")long goodsId, seckill_user user){
//        if(user == null){
//            return Result.Fail(CodeMsg.SESSION_ERROR);
//        }
//        //判断库存
//        GoodsVo goodsVo = goodsService.GetGoodsVoByGoodsId(goodsId);
//        if(goodsVo.getStockCount() == 0){
//            return Result.Fail(CodeMsg.MIAO_SHA_OVER);
//        }
//        //判断是否已经秒杀
//        order_info order = orderService.GetOrderByGoodsIdUserId(goodsId,user.getId());
//        if(order != null){
//            return Result.Fail(CodeMsg.REPEATE_MIAOSHA);
//        }
//        //写入秒杀订单
//        order_info neworder = miaoshaService.miaosha(goodsVo, user);
//        return Result.Success(neworder);
//    }
}
