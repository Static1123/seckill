package com.yl.seckill.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectKey;
import org.springframework.stereotype.Repository;

/**
 * @author Administrator
 */
@Mapper
@Repository
public interface LoginLogMapper {
    @Insert("insert into sk_login_log(user_id, ip, login_time, user_agent)values("
            + "#{userId}, #{ip}, NOW(), #{userAgent})")
    @SelectKey(keyColumn = "id", keyProperty = "id", resultType = long.class, before = false, statement = "select last_insert_id()")
    long insert(@Param("userId") Long userId,
                @Param("ip") String ip,
                @Param("userAgent") String userAgent);
}