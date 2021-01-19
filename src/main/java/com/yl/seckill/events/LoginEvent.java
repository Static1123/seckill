package com.yl.seckill.events;

import com.yl.seckill.dao.LoginLogMapper;
import com.yl.seckill.dao.UserMapper;
import com.yl.seckill.dto.LoginDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Administrator
 */
@Component
public class LoginEvent {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginEvent.class);

    @Resource
    private UserMapper userMapper;

    @Resource
    private LoginLogMapper loginLogMapper;

    @Async
    @EventListener
    public void login(LoginDTO loginDTO) {
        try {
            userMapper.updateLoginCount(loginDTO.getId());
            long i = loginLogMapper.insert(loginDTO.getId(), loginDTO.getIp(), loginDTO.getUserAgent());
            LOGGER.info("insert into login_log {}", i);
        } catch (Exception ex) {
            LOGGER.error("{}", ex.getMessage(), ex);
        }
    }
}