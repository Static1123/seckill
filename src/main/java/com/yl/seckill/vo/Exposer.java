package com.yl.seckill.vo;

import com.yl.seckill.model.BaseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author Administrator
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString
public class Exposer extends BaseObject {
    /**
     * 是否开启秒杀
     */
    private Boolean exposed;

    /**
     * 加密措施，避免用户通过抓包拿到秒杀地址
     */
    private String md5;

    private long seckillId;

    /**
     * 系统当前时间(毫秒)
     */
    private Long now;

    /**
     * 秒杀开启时间
     */
    private Long start;

    /**
     * 秒杀结束时间
     */
    private Long end;

    public Exposer(boolean exposed, String md5, long seckillId) {
        this.exposed = exposed;
        this.md5 = md5;
        this.seckillId = seckillId;
    }

    public Exposer(boolean exposed, Long seckillId, long now, long start, long end) {
        this.exposed = exposed;
        this.seckillId = seckillId;
        this.now = now;
        this.start = start;
        this.end = end;
    }

    public Exposer(boolean exposed, long seckillId) {
        this.exposed = exposed;
        this.seckillId = seckillId;
    }
}