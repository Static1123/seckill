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

    @Select("select * from sk_user where id = #{id}")
    User getById(@Param("id") long id);

    @Update("update sk_user set password = #{password} where id = #{id}")
    void update(User toBeUpdate);
}