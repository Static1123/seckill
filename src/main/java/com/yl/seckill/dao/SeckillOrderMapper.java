package com.yl.seckill.dao;


import com.yl.seckill.model.SeckillOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

/**
 * @author Administrator
 */
@Mapper
@Repository
public interface SeckillOrderMapper {

    /**
     * 插入购买订单明细
     *
     * @param seckillId 秒杀到的商品ID
     * @param money     秒杀的金额
     * @param userPhone 秒杀的用户
     * @return 返回该SQL更新的记录数，如果>=1则更新成功
     */
    int insertOrder(@Param("seckillId") Long seckillId,
                    @Param("money") BigDecimal money,
                    @Param("userPhone") long userPhone);

    /**
     * 根据秒杀商品ID查询订单明细数据并得到对应秒杀商品的数据
     *
     * @param seckillId
     * @param userPhone
     * @return
     */
    SeckillOrder findById(@Param("seckillId") Long seckillId,
                          @Param("userPhone") Long userPhone);
}