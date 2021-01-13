package com.yl.seckill.service;

import com.yl.seckill.exception.RepeatKillException;
import com.yl.seckill.exception.SeckillCloseException;
import com.yl.seckill.exception.SeckillException;
import com.yl.seckill.model.Seckill;
import com.yl.seckill.vo.Exposer;
import com.yl.seckill.vo.SeckillExecution;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Administrator
 */
public interface SecKillService {
    /**
     * 获取所有的秒杀商品列表
     *
     * @return
     */
    List<Seckill> findAll();

    /**
     * 获取某一条商品秒杀信息
     *
     * @param seckillId
     * @return
     */
    Seckill findById(Long seckillId);

    /**
     * 秒杀开始时输出暴露秒杀的地址
     * 否者输出系统时间和秒杀时间
     *
     * @param seckillId
     */
    Exposer exportSeckillUrl(Long seckillId);

    /**
     * 执行秒杀的操作
     *
     * @param seckillId
     * @param userPhone
     * @param money
     * @param md5
     */
    SeckillExecution executeSeckill(Long seckillId, BigDecimal money, Long userPhone, String md5)
            throws SeckillException, RepeatKillException, SeckillCloseException;
}