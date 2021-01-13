package com.yl.seckill.rabbitmq;


import com.yl.seckill.redis.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Administrator
 * 消息发送
 */
@Service
public class MQSender {

    private static final Logger LOGGER = LoggerFactory.getLogger(MQSender.class);

    @Resource
    private AmqpTemplate amqpTemplate;

    public void sendTopic(Object message) {
        String msg = RedisService.beanToString(message);
        LOGGER.info("send topic message:" + msg);
        amqpTemplate.convertAndSend(MQConfig.TOPIC_EXCHANGE, "topic.key1", msg + "1");
        amqpTemplate.convertAndSend(MQConfig.TOPIC_EXCHANGE, "topic.key2", msg + "2");
    }

    public void sendSeckillMessage(SeckillMessage message) {
        String msg = RedisService.beanToString(message);
        LOGGER.info("send message:" + msg);
        amqpTemplate.convertAndSend(MQConfig.QUEUE, msg);

    }
}