package com.yyy.seckill.rabbitmq;


import com.yyy.seckill.pojo.miaosha_order;
import com.yyy.seckill.pojo.order_info;
import com.yyy.seckill.pojo.seckill_user;
import com.yyy.seckill.redis.RedisService;
import com.yyy.seckill.service.GoodsService;
import com.yyy.seckill.service.MiaoshaService;
import com.yyy.seckill.service.OrderService;
import com.yyy.seckill.vo.GoodsVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MQReciver {
    private static Logger log = LoggerFactory.getLogger(MQReciver.class);

    @Autowired
    RedisService redisService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Autowired
    MiaoshaService miaoshaService;

    @RabbitListener(queues=MQConfig.MIAOSHA_QUEUE)
    public void receive(String message) {
        log.info("receive message:"+message);
        MiaoshaMessage mm  = RedisService.stringToBean(message, MiaoshaMessage.class);
        seckill_user user = mm.getUser();
        long goodsId = mm.getGoodsId();

        GoodsVo goods = goodsService.GetGoodsVoByGoodsId(goodsId);
        int stock = goods.getStockCount();
        if(stock <= 0) {
            return;
        }
        //判断是否已经秒杀到了
        order_info order = orderService.GetOrderByGoodsIdUserId(user.getId(), goodsId);
        if(order != null) {
            return;
        }
        //减库存 下订单 写入秒杀订单
        miaoshaService.miaosha(goods, user);
    }

    @RabbitListener(queues=MQConfig.QUEUE)
    public void receive2(String message) {
        log.info("receive message:"+message);
    }
//
		@RabbitListener(queues=MQConfig.TOPIC_QUEUE1)
		public void receiveTopic1(String message) {
			log.info(" topic  queue1 message:"+message);
		}

		@RabbitListener(queues=MQConfig.TOPIC_QUEUE2)
		public void receiveTopic2(String message) {
			log.info(" topic  queue2 message:"+message);
		}
//
		@RabbitListener(queues=MQConfig.HEADER_QUEUE)
		public void receiveHeaderQueue(byte[] message) {
			log.info(" header  queue message:"+new String(message));
		}
}
