package com.yyy.seckill.dao;

import com.yyy.seckill.pojo.test;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface testDao {
    @Select("select * from test where id = #{id}")
    public test GetByID(@Param("id") int id);
    @Insert("Insert into test(id,name)values (#{id},#{name})")
    public int inserttest(test user);
}
