package com.yl.seckill.vo;

import com.yl.seckill.enums.StatusEnum;
import com.yl.seckill.model.BaseObject;
import com.yl.seckill.model.SeckillOrder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author Administrator
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString
public class SeckillExecution extends BaseObject {
    private Long seckillId;

    /**
     * 秒杀执行结果状态
     */
    private Integer state;

    /**
     * 状态表示
     */
    private String stateInfo;

    /**
     * 秒杀成功的订单对象
     */
    private SeckillOrder seckillOrder;

    public SeckillExecution(Long seckillId, StatusEnum statusEnum, SeckillOrder seckillOrder) {
        this.seckillId = seckillId;
        this.state = statusEnum.getState();
        this.stateInfo = statusEnum.getDesc();
        this.seckillOrder = seckillOrder;
    }

    public SeckillExecution(Long seckillId, StatusEnum seckillStatEnum) {
        this.seckillId = seckillId;
        this.state = seckillStatEnum.getState();
        this.stateInfo = seckillStatEnum.getDesc();
    }
}