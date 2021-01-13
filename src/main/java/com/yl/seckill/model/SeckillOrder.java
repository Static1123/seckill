package com.yl.seckill.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Administrator
 * 秒杀订单表（秒杀订单表和其他订单表不同，属于独立的模块）
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = false)
public class SeckillOrder extends BaseObject {

    private Long seckillId;
    private BigDecimal money;

    private Long userPhone;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    private Boolean status;

    private Seckill seckill;
}