package com.yl.seckill.dto;

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
public class LoginDTO extends BaseObject {
    /**
     * 用户Id
     */
    private Long id;
    /**
     * 登录Ip
     */
    private String ip;
    /**
     * 浏览器信息(可以用于大数据分析)
     */
    private String userAgent;
}