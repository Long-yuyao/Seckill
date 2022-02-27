package com.yyy.seckill.controller;

import com.yyy.seckill.pojo.test;
import com.yyy.seckill.rabbitmq.MQsender;
import com.yyy.seckill.redis.RedisService;
import com.yyy.seckill.redis.UserKey;
import com.yyy.seckill.result.CodeMsg;
import com.yyy.seckill.result.Result;
import com.yyy.seckill.service.testService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@EnableAutoConfiguration
@RequestMapping("/demo")
public class DemoController {
    //测试页面跳转
    @RequestMapping("/hello")
    public String hello(Model model){
        model.addAttribute("name","world");
        return "hello";
    }

    //rest api json 输出页面
    @RequestMapping("/result")
    @ResponseBody
    public Result<String> hellosuccess(){
        return Result.Success("hello,world");
    }
    @RequestMapping("/resulterror")
    @ResponseBody
    public Result<String> hellofail(){
        return Result.Fail(CodeMsg.SERVER_ERROR);
    }
    @Autowired
    testService testService;
    @Autowired
    RedisService redisService;
    @RequestMapping("/db/test")
    @ResponseBody
    public Result<test> DBGet(){
        test test = testService.GetById(1);
        return Result.Success(test);
    }
    @RequestMapping("/db/tx")
    @ResponseBody
    public Result<Boolean> inserttest(){
        testService.inserttest();
        return Result.Success(true);
    }
    @RequestMapping("/redis/get")
    @ResponseBody
    public Result<test> RedisGet(){
        test t = redisService.Get(UserKey.GetByID,""+1, test.class);
        return Result.Success(t);
    }
    @RequestMapping("/redis/set")
    @ResponseBody
    public Result<Boolean> RedisSet(){
        test t = new test();
        t.setId(1);
        t.setName("kate");
        boolean r = redisService.Set(UserKey.GetByID,""+1, t);
        return Result.Success(true);
    }
    @Autowired
    MQsender mQsender;
    @RequestMapping("/mq")
    @ResponseBody
    public Result<String> mq(Model model){
        mQsender.send("hello");
        return Result.Success("hello");
    }
    @RequestMapping("/mq/topic")
    @ResponseBody
    public Result<String> mqtopic(Model model){
        mQsender.sendTopic("hello");
        return Result.Success("hello");
    }
}
