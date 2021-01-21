package com.yl.seckill.controller;

import com.google.common.util.concurrent.RateLimiter;
import com.yl.seckill.dao.OrderMapper;
import com.yl.seckill.dto.SecKillRequestDTO;
import com.yl.seckill.model.SeckillOrder1;
import com.yl.seckill.model.User;
import com.yl.seckill.rabbitmq.MQSender;
import com.yl.seckill.rabbitmq.SeckillMessage;
import com.yl.seckill.redis.GoodsKey;
import com.yl.seckill.redis.RedisService;
import com.yl.seckill.redis.UserKey;
import com.yl.seckill.service.GoodsService;
import com.yl.seckill.service.OrderService;
import com.yl.seckill.service.SeckillService1;
import com.yl.seckill.utils.RedisUtil;
import com.yl.seckill.vo.CodeMsg;
import com.yl.seckill.vo.GoodsVo;
import com.yl.seckill.vo.Result;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Administrator
 */
@Controller
@RequestMapping(value = "/seckill")
public class Seckill1Controller implements InitializingBean {
    @Resource
    private GoodsService goodsService;

    @Resource
    private OrderService orderService;

    @Resource
    private SeckillService1 seckillService;

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private MQSender sender;

    @Resource
    private RedisService redisService;

    @Resource
    private OrderMapper orderMapper;

    /**
     * 基于令牌桶算法的限流实现类
     */
    private static RateLimiter rateLimiter = RateLimiter.create(10);

    /**
     * GET POST
     * 1、GET幂等,服务端获取数据，无论调用多少次结果都一样
     * 2、POST，向服务端提交数据，不是幂等
     * <p>
     * 将同步下单改为异步下单
     *
     * @param requestDTO
     * @return
     */
    @RequestMapping(value = "/doSecKill", method = RequestMethod.POST)
    @ResponseBody
    public Result<Integer> list(@Valid SecKillRequestDTO requestDTO) {
        User user = redisService.get(UserKey.token, requestDTO.getToken(), User.class);
        if (user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        if (!rateLimiter.tryAcquire(1000, TimeUnit.MILLISECONDS)) {
            return Result.error(CodeMsg.ACCESS_LIMIT_REACHED);
        }
        Long goodsId = requestDTO.getGoodsId();
        //判断重复秒杀
        SeckillOrder1 order = orderService.getOrderByUserIdGoodsId(user.getId(), goodsId);
        if (order != null) {
            return Result.error(CodeMsg.REPEATE_SECKILL);
        }
        //判断重复秒杀(DB)
        order = orderMapper.getOrderByUserIdGoodsId(user.getId(), goodsId);
        if (order != null) {
            return Result.error(CodeMsg.REPEATE_SECKILL);
        }
        //预减库存
        long stock = redisUtil.decrement(GoodsKey.getGoodsStock.getPrefix() + goodsId);
        if (stock < 0) {
            afterPropertiesSet();
            long stock2 = redisUtil.decrement(GoodsKey.getGoodsStock.getPrefix() + goodsId);
            if (stock2 < 0) {
                return Result.error(CodeMsg.SECKILL_OVER);
            }
        }
        //写入RabbitMq
        SeckillMessage message = new SeckillMessage();
        message.setUser(user);
        message.setGoodsId(goodsId);
        sender.sendSeckillMessage(message);
        return Result.success(0);
    }

    /**
     * 系统初始化,将商品信息加载到redis和本地内存
     */
    @Override
    public void afterPropertiesSet() {
        List<GoodsVo> goodsVoList = goodsService.listGoodsVo();
        if (goodsVoList == null) {
            return;
        }
        for (GoodsVo goods : goodsVoList) {
            redisUtil.set(GoodsKey.getGoodsStock.getPrefix() + goods.getId(), goods.getStockCount());
        }
    }

    /**
     * orderId：成功
     * -1：秒杀失败
     * 0： 排队中
     */
    @RequestMapping(value = "/result", method = RequestMethod.GET)
    @ResponseBody
    public Result<Long> seckillResult(
            @NotNull
            @Min(1)
            @Max(Long.MAX_VALUE)
            @RequestParam("goodsId") Long goodsId,

            @NotEmpty
            @RequestParam("token") String token) {
        User user = redisService.get(UserKey.token, token, User.class);
        if (user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        long orderId = seckillService.getSeckillResult(user.getId(), goodsId);
        return Result.success(orderId);
    }
}