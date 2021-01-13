package com.yl.seckill.rabbitmq;


import com.yl.seckill.model.BaseObject;
import com.yl.seckill.model.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author Administrator
 * 消息体
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString
public class SeckillMessage extends BaseObject {
    private User user;
    private Long goodsId;
}