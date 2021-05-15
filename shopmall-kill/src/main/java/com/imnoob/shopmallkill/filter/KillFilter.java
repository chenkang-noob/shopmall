package com.imnoob.shopmallkill.filter;

import com.alibaba.fastjson.JSON;
import com.imnoob.shopmallcommon.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Component
public class KillFilter implements Filter {

    @Autowired
    RedisTemplate<String,Object> redisTemplate;

    @Value("${accessLimit}")
    Integer LIMIT_COUNT;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String id = servletRequest.getParameter("memberId");
        Boolean aBoolean = redisTemplate.opsForValue().setIfAbsent("Limit:" + id, 1, 1, TimeUnit.MINUTES);
        if (aBoolean){
            filterChain.doFilter(servletRequest,servletResponse);
        }else{
            Long increment = redisTemplate.opsForValue().increment("Limit:" + id, 1);
            if (increment >=LIMIT_COUNT){
                R res = R.error("请勿频繁访问");
                String josnstr = JSON.toJSONString(res);
                servletResponse.getWriter().write(josnstr);
            }else{
                filterChain.doFilter(servletRequest,servletResponse);
            }
        }
    }
}
