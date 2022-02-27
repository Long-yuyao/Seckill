package com.yyy.seckill.vo;

import com.yyy.seckill.pojo.goods;

import java.util.Date;

public class GoodsVo extends goods {
    private Double miaosha_price;
    private Integer stockCount;
    private Date startDate;
    private Date endDate;

    public Double getMiaosha_price() {
        return miaosha_price;
    }

    public void setMiaosha_price(Double miaosha_price) {
        this.miaosha_price = miaosha_price;
    }

    public Integer getStockCount() {
        return stockCount;
    }

    public void setStockCount(Integer stockCount) {
        this.stockCount = stockCount;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return ""+miaosha_price.toString()+ super.getGoodsImg()+super.getGoodsDetail()+super.getId()+super.getGoodsTitle()+super.getGoodsName()+super.getGoodsPrice()+super.getGoodsStock();
    }
}
