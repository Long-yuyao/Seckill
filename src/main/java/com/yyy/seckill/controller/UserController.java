package com.yyy.seckill.controller;
import com.yyy.seckill.pojo.seckill_user;
import com.yyy.seckill.redis.RedisService;
import com.yyy.seckill.result.Result;
import com.yyy.seckill.service.SeckillUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    SeckillUserService userService;

    @Autowired
    RedisService redisService;

    @RequestMapping("/info")
    @ResponseBody
    public Result<seckill_user> info(Model model, seckill_user user) {
        return Result.Success(user);
    }

}
