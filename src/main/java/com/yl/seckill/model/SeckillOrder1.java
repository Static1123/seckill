package com.yl.seckill.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author Administrator
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString
public class SeckillOrder1 extends BaseObject {
    private Long id;
    private Long userId;
    private Long orderId;
    private Long goodsId;
}