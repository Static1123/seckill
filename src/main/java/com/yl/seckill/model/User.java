package com.yl.seckill.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;

/**
 * @author Administrator
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString
public class User extends BaseObject {
    private Long id;
    private String nickname;
    private String password;
    private String salt;
    private String head;
    private Date registerDate;
    private Date lastLoginDate;
    private Integer loginCount;
}