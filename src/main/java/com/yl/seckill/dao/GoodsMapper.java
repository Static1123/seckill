package com.yl.seckill.dao;

import com.yl.seckill.model.SeckillGoods;
import com.yl.seckill.vo.GoodsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Administrator
 */
@Mapper
@Repository
public interface GoodsMapper {

    @Select("select g.*, sg.stock_count, sg.start_date, sg.end_date, sg.seckill_price, sg.version from sk_goods_seckill sg left join sk_goods g on sg.goods_id = g.id")
    List<GoodsVo> listGoodsVo();

    @Select("select g.*, sg.stock_count, sg.start_date, sg.end_date, sg.seckill_price, sg.version  from sk_goods_seckill sg left join sk_goods g  on sg.goods_id = g.id where g.id = #{goodsId}")
    GoodsVo getGoodsVoByGoodsId(@Param("goodsId") long goodsId);

    /**
     * stock_count > 0 和 版本号实现乐观锁 防止超卖
     *
     * @param seckillGoods
     * @return
     */
    @Update("update sk_goods_seckill set stock_count = stock_count - 1, version= version + 1 where goods_id = #{goodsId} and stock_count > 0 and version = #{version}")
    int reduceStockByVersion(SeckillGoods seckillGoods);

    /**
     * 获取最新版本号(乐观锁)
     *
     * @param goodsId
     * @return
     */
    @Select("select version from sk_goods_seckill  where goods_id = #{goodsId}")
    int getVersionByGoodsId(@Param("goodsId") long goodsId);

    /**
     * 获取活动中的商品
     *
     * @return
     */
    @Select("SELECT * FROM sk_goods_seckill AS t WHERE CURRENT_DATE() BETWEEN start_date AND end_date")
    List<GoodsVo> getProcessingList();
}