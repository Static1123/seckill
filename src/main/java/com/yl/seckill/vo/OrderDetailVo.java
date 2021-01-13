package com.yl.seckill.vo;


import com.yl.seckill.model.BaseObject;
import com.yl.seckill.model.OrderInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author Administrator
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString
public class OrderDetailVo extends BaseObject {
    private GoodsVo goods;
    private OrderInfo order;
}