package com.yl.seckill.dao;


import com.yl.seckill.model.Seckill;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author Administrator
 */
@Mapper
@Repository
public interface SeckillMapper {

    /**
     * 查询所有秒杀商品的记录信息
     *
     * @return
     */
    List<Seckill> findAll();

    /**
     * 根据主键查询当前秒杀商品的数据
     *
     * @param id
     * @return
     */
    Seckill findById(long id);

    /**
     * 减库存
     *
     * @param seckillId 秒杀商品ID
     * @param killTime  秒杀时间
     * @return 返回此SQL更新的记录数，如果>=1表示更新成功
     */
    int reduceStock(@Param("seckillId") Long seckillId,
                    @Param("killTime") Date killTime);
}