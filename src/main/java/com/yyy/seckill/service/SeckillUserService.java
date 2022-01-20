package com.yyy.seckill.service;

import com.yyy.seckill.controller.LoginController;
import com.yyy.seckill.dao.seckill_userDao;
import com.yyy.seckill.exception.GlobalException;
import com.yyy.seckill.pojo.seckill_user;
import com.yyy.seckill.redis.RedisService;
import com.yyy.seckill.redis.SeckillUserKey;
import com.yyy.seckill.result.CodeMsg;
import com.yyy.seckill.utils.MD5Util;
import com.yyy.seckill.utils.UUIDUtil;
import com.yyy.seckill.vo.LoginVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Service
public class SeckillUserService {
    public static final String COOKI_NAME_TOKEN = "token";
    private static Logger log = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    seckill_userDao seckill_userDao;
    @Autowired
    RedisService redisService;

    public seckill_user GetByToken(HttpServletResponse response,String token) {
        if(StringUtils.isEmpty(token)){
            return null;
        }
        seckill_user user = redisService.Get(SeckillUserKey.token, token, seckill_user.class);
        //延长有效期
        if(user != null) {
            addCookie(response, token, user);
        }
        return user;
    }

    public seckill_user GetById(long id){return seckill_userDao.GetById(id);}

    public boolean login(HttpServletResponse response,LoginVo vo) {
        if(vo == null){
            log.info(vo.toString());
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }
        String mobile = vo.getMobile();
        String frompass = vo.getPassword();
        //判断手机号是否存在
        seckill_user user = GetById(Long.parseLong(mobile));
        if(user == null){
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }
        //验证密码
        String dbpass = user.getPassword();
        String dbsalt = user.getSalt();
        log.info(dbsalt);
        if(!MD5Util.FromPassToDBPass(frompass, dbsalt).equals(dbpass)){
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        }
        //生成cookie
        String token = UUIDUtil.uuid();
        addCookie(response, token, user);
        return true;
    }
    private void addCookie(HttpServletResponse response, String token, seckill_user user) {
        redisService.Set(SeckillUserKey.token, token, user);
        Cookie cookie = new Cookie(COOKI_NAME_TOKEN, token);
        cookie.setMaxAge(SeckillUserKey.token.ExpireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
