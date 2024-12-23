package com.capricorn.summer.intercepters;

import com.capricorn.common.redis.RedisCache;
import com.capricorn.summer.service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Component
@Slf4j
public class TokenInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        response.setHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers"));
        String path = request.getServletPath();
//        if (path.contains("static")){
//            return true ;
//        }
        //查看客户请求的资源
        log.info(String.format("请求资源：%s",path));
        //跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
        if ("OPTIONS".equals(request.getMethod())){
            response.setStatus(HttpServletResponse.SC_OK);
            return true ;
        }
        String token = request.getHeader("token");
        //TODO 解析token
        String sessionId = request.getSession().getId();
        String userName = redisCache.getCacheObject(sessionId);
        //session过期
        if (ObjectUtils.isEmpty(userName)){
            response.getOutputStream().print("{\"code\":\"401\"}");
            return false;
        }
        Map<String, Object> loginUser = tokenService.getLoginUser(token);
        //token非法
        if (ObjectUtils.isEmpty(loginUser)){
            response.getOutputStream().print("{\"code\":\"402\"}");
            return false;
        }
        StringUtils.isEmpty("");
        return true;
    }
}
