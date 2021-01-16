package com.yl.seckill.rabbitmq;


import com.yl.seckill.model.SeckillOrder1;
import com.yl.seckill.model.User;
import com.yl.seckill.redis.RedisService;
import com.yl.seckill.service.GoodsService;
import com.yl.seckill.service.OrderService;
import com.yl.seckill.service.SeckillService1;
import com.yl.seckill.vo.GoodsVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Administrator
 * 消息接收方
 */
@Service
public class MQReceiver {

    private static final Logger LOGGER = LoggerFactory.getLogger(MQReceiver.class);

    @Resource
    private RedisService redisService;

    @Resource
    GoodsService goodsService;

    @Resource
    OrderService orderService;

    @Resource
    SeckillService1 seckillService;

    @RabbitListener(queues = MQConfig.QUEUE)
    public void receive(String message) {
        LOGGER.info("receive message:" + message);
        SeckillMessage m = RedisService.stringToBean(message, SeckillMessage.class);
        User user = m.getUser();
        long goodsId = m.getGoodsId();

        GoodsVo goodsVo = goodsService.getGoodsVoByGoodsId(goodsId);
        int stock = goodsVo.getStockCount();
        if (stock <= 0) {
            return;
        }

        //判断重复秒杀
        SeckillOrder1 order = orderService.getOrderByUserIdGoodsId(user.getId(), goodsId);
        if (order != null) {
            return;
        }
        try {
            //减库存 下订单 写入秒杀订单
            seckillService.seckill(user, goodsVo);
        } catch (Exception ex) {
            LOGGER.error("减库存 下订单 写入秒杀订单 异常");
            LOGGER.error("{}", ex.getMessage(), ex);
        }
    }

    @RabbitListener(queues = MQConfig.TOPIC_QUEUE1)
    public void receiveTopic1(String message) {
        LOGGER.info(" topic  queue1 message:" + message);
    }

    @RabbitListener(queues = MQConfig.TOPIC_QUEUE2)
    public void receiveTopic2(String message) {
        LOGGER.info(" topic  queue2 message:" + message);
    }
}