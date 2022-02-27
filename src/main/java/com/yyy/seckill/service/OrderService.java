package com.yyy.seckill.service;

import com.yyy.seckill.dao.OrderDao;
import com.yyy.seckill.pojo.miaosha_order;
import com.yyy.seckill.pojo.order_info;
import com.yyy.seckill.pojo.seckill_user;
import com.yyy.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class OrderService {
    @Autowired
    OrderDao orderDao;
    public order_info GetOrderByGoodsIdUserId(long goodsId, long userId){
        return orderDao.GetOrderByGoodsIdUserId(goodsId,userId);
    }

    @Transactional
    public order_info createOrder(GoodsVo goodsVo, seckill_user user) {
        order_info order = new order_info();
        order.setCreateDate(new Date());
        order.setGoodsId(goodsVo.getId());
        order.setGoodsName(goodsVo.getGoodsName());
        order.setUserId(user.getId());
        order.setGoodsPrice(goodsVo.getMiaosha_price());
        order.setOrderChannel(1);
        order.setStatus(0);
        order.setDeliveryAddrId(0L);
        long orderId = orderDao.insert(order);
        miaosha_order miaoshaOrder = new miaosha_order();
        miaoshaOrder.setOrderId(orderId);
        miaoshaOrder.setGoodsId(goodsVo.getId());
        miaoshaOrder.setUserId(user.getId());
        orderDao.insertMiaoshaorder(miaoshaOrder);
        return order;
    }

    public order_info getOrderById(long orderId) {
        return orderDao.getOrderById(orderId);
    }

    public void deleteOrders() {
        orderDao.deleteOrders();
        orderDao.deleteMiaoshaOrders();
    }
}
