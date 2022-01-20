package com.yyy.seckill.dao;

import com.yyy.seckill.pojo.seckill_user;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface seckill_userDao {
    @Select("select * from miaosha_user where id = #{id}")
    public seckill_user GetById(long id);
}
