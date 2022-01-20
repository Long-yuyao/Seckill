package com.yyy.seckill.controller;

import com.yyy.seckill.pojo.seckill_user;
import com.yyy.seckill.redis.GoodsKey;
import com.yyy.seckill.redis.RedisService;
import com.yyy.seckill.service.GoodsService;
import com.yyy.seckill.service.SeckillUserService;
import com.yyy.seckill.vo.GoodsVo;
import org.apache.catalina.core.ApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.IContext;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.util.StringUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@EnableAutoConfiguration
@RequestMapping("/goods")
public class GoodsController {
    private static Logger log = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    SeckillUserService seckillUserService;
    @Autowired
    GoodsService goodsService;
    @Autowired
    RedisService redisService;
    @Autowired
    ThymeleafViewResolver thymeleafViewResolver;
    @RequestMapping(value = "/to_list", produces = "text/html")
    @ResponseBody
    public String to_list(HttpServletRequest request, HttpServletResponse response, Model model, seckill_user user){
        model.addAttribute("user",user);
        //查询商品列表
        List<GoodsVo> goodsVoList = goodsService.ListGoodsVo();
        model.addAttribute("goodsVoList",goodsVoList);
        //return "goods_list";
        String html=redisService.Get(GoodsKey.getGoodsList, "", String.class);
        //取缓存
        if(!StringUtils.isEmpty(html)){
            return html;
        }
        //手动渲染
        WebContext cx = new WebContext(request, response, request.getServletContext());
        html = thymeleafViewResolver.getTemplateEngine().process("goods_list", cx);
        if(!StringUtils.isEmpty(html)){
            redisService.Set(GoodsKey.getGoodsList, "", html);
        }
        return html;
    }
    @RequestMapping("/to_detail/{goodsId}")
    public String to_detail(Model model, seckill_user user, @PathVariable("goodsId")long goodsId){
        model.addAttribute("user",user);
        //snowflake
        GoodsVo goods = goodsService.GetGoodsVoByGoodsId(goodsId);
        model.addAttribute("goods",goods);
        long startAt = goods.getStartDate().getTime();
        long endAt = goods.getEndDate().getTime();
        long now = System.currentTimeMillis();
        int miaoshaStatus = 0;
        int remainSeconds = 0;
        if(now < startAt ) {//秒杀还没开始，倒计时
            remainSeconds = (int)((startAt - now )/1000);
        }else  if(now > endAt){//秒杀已经结束
            miaoshaStatus = 2;
            remainSeconds = -1;
        }else {//秒杀进行中
            miaoshaStatus = 1;
        }
        model.addAttribute("miaoshaStatus",miaoshaStatus);
        model.addAttribute("remainSeconds",remainSeconds);
        return "goods_detail";
    }
}
