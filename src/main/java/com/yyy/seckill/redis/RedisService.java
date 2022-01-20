package com.yyy.seckill.redis;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
public class RedisService {
    @Autowired
    JedisPool jedisPool;
    public <T> T Get(KeyPrefix prefix, String key, Class<T> clazz){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            //生成真正的key
            String realkey = prefix.getPrefix()+key;
            String str = jedis.get(realkey);
            T t = stringToBean(str, clazz);
            return t;
        }finally {
            returnTopool(jedis);
        }
    }

    public <T> boolean exists(KeyPrefix prefix, String key){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            //生成真正的key
            String realkey = prefix.getPrefix()+key;
            return jedis.exists(realkey);
        }finally {
            returnTopool(jedis);
        }
    }

    public <T> boolean Set(KeyPrefix prefix, String key, T value){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            String val = BeanToString(value);
            if (val == null || val.length() <= 0){
                return false;
            }
            String realkey = prefix.getPrefix()+key;
            int seconds = prefix.ExpireSeconds();
            if (seconds <= 0){
                jedis.set(realkey, val);
            }else {
                jedis.setex(realkey, seconds, val);
            }
            return true;
        }finally {
            returnTopool(jedis);
        }
    }

    /*
    增加量
     */
    public <T> Long incr(KeyPrefix prefix, String key){
        Jedis jedis = null;
        try {
            jedis =  jedisPool.getResource();
            //生成真正的key
            String realKey  = prefix.getPrefix() + key;
            return  jedis.incr(realKey);
        }finally {
            returnTopool(jedis);
        }
    }

    /*
    减少量
     */
    public <T> Long decr(KeyPrefix prefix, String key) {
        Jedis jedis = null;
        try {
            jedis =  jedisPool.getResource();
            //生成真正的key
            String realKey  = prefix.getPrefix() + key;
            return  jedis.decr(realKey);
        }finally {
            returnTopool(jedis);
        }
    }


    private <T> String BeanToString(T value) {
        if (value == null){
            return null;
        }
        Class<?> clazz = value.getClass();
        if(clazz == int.class || clazz == Integer.class) {
            return ""+value;
        }else if(clazz == String.class) {
            return (String)value;
        }else if(clazz == long.class || clazz == Long.class) {
            return ""+value;
        }else {
            return JSON.toJSONString(value);
        }
    }

    private <T> T stringToBean(String str, Class<T> clazz) {
        if(str == null || str.length() <= 0 || clazz == null) {
            return null;
        }
        if(clazz == int.class || clazz == Integer.class) {
            return (T)Integer.valueOf(str);
        }else if(clazz == String.class) {
            return (T)str;
        }else if(clazz == long.class || clazz == Long.class) {
            return  (T)Long.valueOf(str);
        }else {
            return JSON.toJavaObject(JSON.parseObject(str), clazz);
        }
    }

    private void returnTopool(Jedis jedis) {
        if (jedis != null)
        {
            jedis.close();
        }
    }
}
