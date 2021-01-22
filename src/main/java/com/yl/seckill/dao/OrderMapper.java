package com.yl.seckill.dao;

import com.yl.seckill.model.OrderInfo;
import com.yl.seckill.model.SeckillOrder1;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Administrator
 */
@Mapper
@Repository
public interface OrderMapper {


    @Select("select * from sk_order where user_id = #{userId} and goods_id = #{goodsId}")
    SeckillOrder1 getOrderByUserIdGoodsId(@Param("userId") long userId, @Param("goodsId") long goodsId);

    /**
     * 通过@SelectKey使insert成功后返回主键id，也就是订单id
     *
     * @param orderInfo
     * @return
     */
    @Insert("insert into sk_order_info(user_id, goods_id, goods_name, goods_count, goods_price, order_channel, status, create_date)values("
            + "#{userId}, #{goodsId}, #{goodsName}, #{goodsCount}, #{goodsPrice}, #{orderChannel},#{status},#{createDate} )")
    @SelectKey(keyColumn = "id", keyProperty = "id", resultType = long.class, before = false, statement = "select last_insert_id()")
    long insert(OrderInfo orderInfo);


    @Insert("insert into sk_order (user_id, goods_id, order_id)values(#{userId}, #{goodsId}, #{orderId})")
    int insertSeckillOrder(SeckillOrder1 order);

    @Select("select * from sk_order_info where id = #{orderId}")
    OrderInfo getOrderById(@Param("orderId") long orderId);

    @Select("select * from sk_order_info")
    List<OrderInfo> getAllList();
}