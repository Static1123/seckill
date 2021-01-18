package com.yl.seckill.controller;

import com.alibaba.druid.util.StringUtils;
import com.yl.seckill.constants.PatternConstants;
import com.yl.seckill.dto.DetailRequestDTO;
import com.yl.seckill.model.User;
import com.yl.seckill.redis.GoodsKey;
import com.yl.seckill.redis.RedisService;
import com.yl.seckill.service.GoodsService;
import com.yl.seckill.service.UserService;
import com.yl.seckill.thread.ThreadLocalMap;
import com.yl.seckill.vo.GoodsDetailVo;
import com.yl.seckill.vo.GoodsVo;
import com.yl.seckill.vo.Result;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Administrator
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Resource
    private UserService userService;

    @Resource
    private RedisService redisService;

    @Resource
    private GoodsService goodsService;

    @Resource
    private ThymeleafViewResolver thymeleafViewResolver;

    /**
     * 商品列表页面
     * QPS:433
     * 1000 * 10
     */
    @RequestMapping(value = "/list", produces = "text/html")
    @ResponseBody
    public String list(HttpServletRequest request, HttpServletResponse response, Model model) {
        String token = request.getParameter(PatternConstants.TOKEN_NAME);
        User user = (User) ThreadLocalMap.get(request.getParameter(PatternConstants.TOKEN_NAME));

        //取缓存
        String html = redisService.get(GoodsKey.getGoodsList, "", String.class);
        if (!StringUtils.isEmpty(html)) {
            return html;
        }
        List<GoodsVo> goodsList = goodsService.listGoodsVo();
        model.addAttribute(PatternConstants.TOKEN_NAME, token);
        model.addAttribute("user", user);
        model.addAttribute("goodsList", goodsList);

        //手动渲染
        WebContext ctx = new WebContext(request, response,
                request.getServletContext(), request.getLocale(), model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("goods_list", ctx);

        if (!StringUtils.isEmpty(html)) {
            redisService.set(GoodsKey.getGoodsList, "", html);
        }
        //结果输出
        return html;
    }


    /**
     * 商品详情页面(展示)
     */
    @RequestMapping(value = "/detail2", method = RequestMethod.GET)
    public String detail2(HttpServletRequest request, HttpServletResponse response, Model model,
                          @NotNull
                          @Min(1)
                          @Max(Long.MAX_VALUE)
                          @RequestParam("goodsId") Long goodsId,
                          @RequestParam(value = "token", required = false) String token) {
//        String token = request.getParameter(PatternConstants.TOKEN_NAME);
//        model.addAttribute(PatternConstants.TOKEN_NAME, token);
//
//        //取缓存
//        String html = redisService.get(GoodsKey.getGoodsDetail, "" + goodsId, String.class);
//        if (!StringUtils.isEmpty(html)) {
//            return html;
//        }
//
//        //根据id查询商品详情
//        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
//        model.addAttribute("goods", goods);
//        long startTime = goods.getStartDate().getTime();
//        long endTime = goods.getEndDate().getTime();
//        long now = System.currentTimeMillis();
//
//        int seckillStatus;
//        int remainSeconds;
//
//        if (now < startTime) {
//            //秒杀还没开始，倒计时
//            seckillStatus = 0;
//            remainSeconds = (int) ((startTime - now) / 1000);
//        } else if (now > endTime) {
//            //秒杀已经结束
//            seckillStatus = 2;
//            remainSeconds = -1;
//        } else {
//            //秒杀进行中
//            seckillStatus = 1;
//            remainSeconds = 0;
//        }
//        model.addAttribute("seckillStatus", seckillStatus);
//        model.addAttribute("remainSeconds", remainSeconds);
//
//        //手动渲染
//        WebContext ctx = new WebContext(request, response,
//                request.getServletContext(), request.getLocale(), model.asMap());
//        html = thymeleafViewResolver.getTemplateEngine().process("goods_detail1", ctx);
//        if (!StringUtils.isEmpty(html)) {
//            redisService.set(GoodsKey.getGoodsDetail, "" + goodsId, html);
//        }
//        return html;
        return "goods_detail1";
    }

    /**
     * 商品详情页面(接口，秒杀调用)
     */
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    @ResponseBody
    public Result<GoodsDetailVo> detail(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Model model,
                                        @Valid DetailRequestDTO requestDTO) {
        //根据id查询商品详情
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(requestDTO.getGoodsId());
        model.addAttribute("goods", goods);

        long startTime = goods.getStartDate().getTime();
        long endTime = goods.getEndDate().getTime();
        long now = System.currentTimeMillis();

        int seckillStatus;
        int remainSeconds;

        if (now < startTime) {
            //秒杀还没开始，倒计时
            seckillStatus = 0;
            remainSeconds = (int) ((startTime - now) / 1000);
        } else if (now > endTime) {
            //秒杀已经结束
            seckillStatus = 2;
            remainSeconds = -1;
        } else {
            //秒杀进行中
            seckillStatus = 1;
            remainSeconds = 0;
        }
        GoodsDetailVo vo = new GoodsDetailVo();
        vo.setGoods(goods);
        vo.setRemainSeconds(remainSeconds);
        vo.setSeckillStatus(seckillStatus);
        vo.setToken(requestDTO.getToken());
        return Result.success(vo);
    }
}