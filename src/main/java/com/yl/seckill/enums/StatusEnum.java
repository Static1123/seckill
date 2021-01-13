package com.yl.seckill.enums;

/**
 * @author Administrator
 */
public enum StatusEnum {
    /**
     * 秒杀成功
     */
    SUCCESS(1, "秒杀成功"),
    /**
     * 秒杀结束
     */
    END(0, "秒杀结束"),
    /**
     * 重复秒杀
     */
    REPEAT_KILL(-1, "重复秒杀"),
    /**
     * 系统异常
     */
    SYSTEM_ERROR(-2, "系统异常"),
    /**
     * 数据串改
     */
    DATA_REWRITE(-3, "数据串改");

    private int state;
    private String desc;

    StatusEnum(int state, String desc) {
        this.state = state;
        this.desc = desc;
    }

    public int getState() {
        return state;
    }

    public String getDesc() {
        return this.desc;
    }

    public static StatusEnum stateOf(int value) {
        for (StatusEnum state : values()) {
            if (state.getState() == value) {
                return state;
            }
        }
        return null;
    }
}