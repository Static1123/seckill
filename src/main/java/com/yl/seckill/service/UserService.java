package com.yl.seckill.service;

import com.yl.seckill.dao.UserMapper;
import com.yl.seckill.dto.LoginDTO;
import com.yl.seckill.exception.SeckillException;
import com.yl.seckill.model.User;
import com.yl.seckill.redis.RedisService;
import com.yl.seckill.redis.UserKey;
import com.yl.seckill.utils.IpUtils;
import com.yl.seckill.utils.MD5Util;
import com.yl.seckill.utils.UUIDUtil;
import com.yl.seckill.vo.LoginVo;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Administrator
 */
@Service
public class UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private RedisService redisService;

    @Resource
    private ApplicationEventPublisher publisher;

    private User getByPhone(long phone) {
        //对象缓存
        User user = redisService.get(UserKey.getById, "" + phone, User.class);
        if (user != null) {
            return user;
        }
        //从DB读取
        user = userMapper.getByPhone(phone);
        //再存入缓存
        if (user != null) {
            redisService.set(UserKey.getById, "" + phone, user);
        }
        return user;
    }

    /**
     * 典型缓存同步场景：更新密码
     */
    public boolean updatePassword(String token, long id, String formPass) {
        //取user
        User user = this.getByPhone(id);
        if (user == null) {
            throw new SeckillException("用户不存在");
        }
        //更新数据库
        User toBeUpdate = new User();
        toBeUpdate.setId(id);
        toBeUpdate.setPassword(MD5Util.formPassToDBPass(formPass, user.getSalt()));
        userMapper.update(toBeUpdate);
        //更新缓存：先删除再插入
        redisService.delete(UserKey.getById, "" + id);
        user.setPassword(toBeUpdate.getPassword());
        redisService.set(UserKey.token, token, user);
        return true;
    }

    public String login(HttpServletRequest request, LoginVo loginVo) {
        if (loginVo == null) {
            throw new SeckillException("系统异常");
        }
        String mobile = loginVo.getMobile();
        String formPass = loginVo.getPassword();
        //判断手机号是否存在
        User user = this.getByPhone(Long.parseLong(mobile));
        if (user == null) {
            throw new SeckillException("用户不存在");
        }
        //验证密码
        String dbPass = user.getPassword();
        String salt = user.getSalt();
        String calcPass = MD5Util.inputPassToDbPass(formPass, salt);
        if (!calcPass.equals(dbPass)) {
            throw new SeckillException("用户密码错误");
        }
        //生成唯一id作为token
        String token = UUIDUtil.uuid();
        //写入redis
        redisService.set(UserKey.token, token, user);
        //异步更新数据
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setId(user.getId());
        loginDTO.setIp(IpUtils.getIpAddr(request));
        loginDTO.setUserAgent(request.getHeader("User-Agent"));
        publisher.publishEvent(loginDTO);
        return token;
    }
}