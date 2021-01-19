package com.yl.seckill.filter;

import com.yl.seckill.constants.PatternConstants;
import com.yl.seckill.model.User;
import com.yl.seckill.redis.RedisService;
import com.yl.seckill.redis.UserKey;
import com.yl.seckill.thread.ThreadLocalMap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author Administrator
 */
@Component
@javax.servlet.annotation.WebFilter(urlPatterns = "/**", filterName = "webFilter")
public class WebFilter extends HttpFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebFilter.class);

    @Resource
    private RedisService redisService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (!(request instanceof HttpServletRequest)) {
            chain.doFilter(request, response);
            return;
        }
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String token = httpServletRequest.getParameter(PatternConstants.TOKEN_NAME);
        if (StringUtils.isEmpty(token)) {
            chain.doFilter(request, response);
            return;
        }
        User user = redisService.get(UserKey.token, token, User.class);
        if (user != null) {
            ThreadLocalMap.put(token, user);
        }
        LOGGER.debug("{}", user);
        chain.doFilter(request, response);
    }
}