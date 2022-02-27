package com.yyy.seckill.dao;

import com.yyy.seckill.pojo.miaosha_goods;
import com.yyy.seckill.vo.GoodsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface GoodsDao {
    @Select("select g.*,mg.stock_count, mg.start_date, mg.end_date,mg.miaosha_price from miaosha_goods mg left join goods g on mg.goods_id = g.id")
    public List<GoodsVo> GetGoodsVoList();
    @Select("select g.*,mg.stock_count, mg.start_date, mg.end_date,mg.miaosha_price from miaosha_goods mg left join goods g on mg.goods_id = g.id where g.id = #{goodsId}")
    GoodsVo GetGoodsVoByGoodsId(@Param("goodsId") long goodsId);
    @Update("update miaosha_goods set stock_count = stock_count -1 where goods_id = #{id} and stock_count>0")
    void reduceStock(@Param("id") Long id);
    @Update("update miaosha_goods set stock_count = #{stockCount} where goods_id = #{goodsId}")
    void resetStock(miaosha_goods g);
}
