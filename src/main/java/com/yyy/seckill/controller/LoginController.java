package com.yyy.seckill.controller;

import com.yyy.seckill.redis.RedisService;
import com.yyy.seckill.result.Result;
import com.yyy.seckill.service.SeckillUserService;
import com.yyy.seckill.vo.LoginVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
public class LoginController {
    private static Logger log = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    SeckillUserService seckillUserService;
    @Autowired
    RedisService redisService;

    @RequestMapping("/to_login")
    public String toLogin() {
        return "login";
    }
    @RequestMapping("/do_login")
    @ResponseBody
    public  Result<Boolean> do_login(HttpServletResponse response, @Valid LoginVo vo){
        log.info(vo.toString());
        //登录
        seckillUserService.login(response, vo);
        return Result.Success(true);
    }
}
