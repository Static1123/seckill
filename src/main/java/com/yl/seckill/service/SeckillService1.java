package com.yl.seckill.service;

import com.yl.seckill.model.OrderInfo;
import com.yl.seckill.model.SeckillOrder1;
import com.yl.seckill.model.User;
import com.yl.seckill.redis.RedisService;
import com.yl.seckill.redis.SeckillKey;
import com.yl.seckill.vo.GoodsVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author Administrator
 */
@Service
public class SeckillService1 {
    @Resource
    private GoodsService goodsService;

    @Resource
    private OrderService orderService;

    @Resource
    private RedisService redisService;

    /**
     * 保证这三个操作，减库存 下订单 写入秒杀订单是一个事务
     *
     * @param user
     * @param goods
     * @return
     */
    @Transactional
    public OrderInfo seckill(User user, GoodsVo goods) {
        //减库存
        boolean success = goodsService.reduceStock(goods);
        if (success) {
            //下订单 写入秒杀订单
            return orderService.createOrder(user, goods);
        } else {
            setGoodsOver(goods.getId());
            return null;
        }
    }

    public long getSeckillResult(long userId, long goodsId) {
        SeckillOrder1 order = orderService.getOrderByUserIdGoodsId(userId, goodsId);
        if (order != null) {
            return order.getOrderId();
        } else {
            boolean isOver = getGoodsOver(goodsId);
            if (isOver) {
                return -1;
            } else {
                return 0;
            }
        }
    }

    private void setGoodsOver(Long goodsId) {
        redisService.set(SeckillKey.isGoodsOver, "" + goodsId, true);
    }

    private boolean getGoodsOver(long goodsId) {
        return redisService.exists(SeckillKey.isGoodsOver, "" + goodsId);
    }
}