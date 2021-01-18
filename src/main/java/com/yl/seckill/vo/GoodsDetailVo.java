package com.yl.seckill.vo;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author Administrator
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString
public class GoodsDetailVo {
    private Integer seckillStatus = 0;
    private Integer remainSeconds = 0;
    private GoodsVo goods;
    private String token;
}