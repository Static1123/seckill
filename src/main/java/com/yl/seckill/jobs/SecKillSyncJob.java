package com.yl.seckill.jobs;

import com.yl.seckill.dao.GoodsMapper;
import com.yl.seckill.dao.OrderMapper;
import com.yl.seckill.model.OrderInfo;
import com.yl.seckill.model.SeckillOrder1;
import com.yl.seckill.redis.OrderKey;
import com.yl.seckill.redis.RedisService;
import com.yl.seckill.vo.GoodsVo;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.shardingsphere.elasticjob.api.ShardingContext;
import org.apache.shardingsphere.elasticjob.simple.job.SimpleJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author Administrator
 */
@Component
public class SecKillSyncJob implements SimpleJob {
    private static final Logger LOGGER = LoggerFactory.getLogger(SecKillSyncJob.class);

    @Resource
    private GoodsMapper goodsMapper;

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private RedisService redisService;

    @Override
    public void execute(ShardingContext shardingContext) {
        LOGGER.info("start job");
        //获取秒杀中的商品
        List<GoodsVo> goodsVoList = goodsMapper.getProcessingList();
        if (CollectionUtils.isEmpty(goodsVoList)) {
            LOGGER.info("暂时没有商品秒杀活动");
            return;
        }
        //获取所有订单
        List<OrderInfo> orderInfoList = orderMapper.getAllList();
        if (CollectionUtils.isEmpty(orderInfoList)) {
            LOGGER.info("暂时没有数据订单");
            return;
        }
        goodsVoList.forEach(item -> {
            Long goodsId = item.getGoodsId();
            Date startDate = item.getStartDate();
            Date endDate = item.getEndDate();

            orderInfoList.forEach(orderInfo -> {
                Date createDate = orderInfo.getCreateDate();
                Long userId = orderInfo.getUserId();
                if (createDate.getTime() >= startDate.getTime() && createDate.getTime() <= endDate.getTime()) {
                    SeckillOrder1 seckillOrder1 = new SeckillOrder1();
                    seckillOrder1.setGoodsId(goodsId);
                    seckillOrder1.setOrderId(orderInfo.getId());
                    seckillOrder1.setUserId(userId);
                    seckillOrder1.setId(0L);
                    String key = OrderKey.getSeckillOrderByUidGid.getPrefix().concat(userId + "").concat("_").concat(goodsId + "");
                    LOGGER.info("{}", key);
                    //活动中购买商品
                    redisService.set(OrderKey.getSeckillOrderByUidGid, "" + userId + "_" + goodsId, seckillOrder1);
                }
            });
        });
        LOGGER.info("end job");
    }
}