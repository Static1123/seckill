package com.yl.seckill.dao;


import com.yl.seckill.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * @author Administrator
 */
@Mapper
@Repository
public interface UserMapper {

    @Select("select * from sk_user where phone = #{phone}")
    User getByPhone(@Param("phone") Long phone);

    @Update("update sk_user set password = #{password} where id = #{id}")
    void update(User toBeUpdate);

    @Update("update sk_user set login_count = login_count + 1 where id = #{id}")
    void updateLoginCount(@Param("id") Long id);
}