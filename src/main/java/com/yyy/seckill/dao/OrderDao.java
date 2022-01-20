package com.yyy.seckill.dao;

import com.yyy.seckill.pojo.miaosha_order;
import com.yyy.seckill.pojo.order_info;
import org.apache.ibatis.annotations.*;

@Mapper
public interface OrderDao {
    @Select("select * from order_info where goods_id = #{goodsId} and user_id = #{userId}")
    public order_info GetOrderByGoodsIdUserId(@Param("goodsId") long goodsId, @Param("userId") long userId);
    @Insert("insert into order_info(user_id, goods_id, goods_name, goods_count, goods_price, order_channel, status, create_date)values("
            + "#{userId}, #{goodsId}, #{goodsName}, #{goodsCount}, #{goodsPrice}, #{orderChannel},#{status},#{createDate} )")
    @SelectKey(keyColumn="id", keyProperty="id", resultType=long.class, before=false, statement="select last_insert_id()")
    public long insert(order_info order);
    @Insert("insert into miaosha_order (user_id, goods_id, order_id)values(#{userId}, #{goodsId}, #{orderId})")
    void insertMiaoshaorder(miaosha_order miaoshaOrder);
}
