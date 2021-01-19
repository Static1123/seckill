package com.yl.seckill.controller;


import com.yl.seckill.model.OrderInfo;
import com.yl.seckill.model.User;
import com.yl.seckill.redis.RedisService;
import com.yl.seckill.redis.UserKey;
import com.yl.seckill.service.GoodsService;
import com.yl.seckill.service.OrderService;
import com.yl.seckill.vo.CodeMsg;
import com.yl.seckill.vo.GoodsVo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author Administrator
 */
@Controller
@RequestMapping("/order")
public class OrderController {

    @Resource
    RedisService redisService;

    @Resource
    OrderService orderService;

    @Resource
    GoodsService goodsService;

    @RequestMapping("/detail")
    public String info(Model model,
                       @NotNull
                       @Min(1)
                       @Max(Long.MAX_VALUE)
                       @RequestParam("orderId") Long orderId,

                       @NotEmpty
                       @RequestParam("token") String token) {
        User user = redisService.get(UserKey.token, token, User.class);
        if (user == null) {
            return CodeMsg.SESSION_ERROR.getMsg();
        }
        OrderInfo order = orderService.getOrderById(orderId);
        if (order == null) {
            return CodeMsg.ORDER_NOT_EXIST.getMsg();
        }
        long goodsId = order.getGoodsId();
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        model.addAttribute("goods", goods);
        model.addAttribute("orderInfo", order);
        return "order_detail";
    }
}