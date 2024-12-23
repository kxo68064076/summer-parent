package com.capricorn.summer.service;

import com.capricorn.common.redis.CacheConstants;
import com.capricorn.common.utils.Constants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.capricorn.common.redis.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * token验证处理
 *
 * @author ruoyi
 */
@Component
public class TokenService
{
    private static final Logger log = LoggerFactory.getLogger(TokenService.class);

    // 令牌自定义标识
    @Value("${token.header}")
    private String header;

    // 令牌秘钥
    @Value("${token.secret}")
    private String secret;

    // 令牌有效期（默认30分钟）
    @Value("${token.expireTime}")
    private int expireTime;

    protected static final long MILLIS_SECOND = 1000;

    protected static final long MILLIS_MINUTE = 60 * MILLIS_SECOND;

    private static final Long MILLIS_MINUTE_TEN = 20 * 60 * 1000L;

    @Autowired
    private RedisCache redisCache;

    /**
     * 获取用户身份信息
     *
     * @return 用户信息
     */
    public Map<String,Object> getLoginUser(String token)
    {
        // 获取请求携带的令牌
        if (!ObjectUtils.isEmpty(token))
        {
            try
            {
                Claims claims = parseToken(token);
                // 解析对应的权限以及用户信息
                String uuid = (String) claims.get(Constants.LOGIN_USER_KEY);
                String userKey = getTokenKey(uuid);
                Map<String,Object> userInfo = redisCache.getCacheObject(userKey);
                return userInfo;
            }
            catch (Exception e)
            {
                log.error("获取用户信息异常'{}'", e.getMessage());
            }
        }
        return null;
    }

    /**
     * 设置用户身份信息
     */
//    public void setLoginUser(LoginUser loginUser)
//    {
//        if (StringUtils.isNotNull(loginUser) && StringUtils.isNotEmpty(loginUser.getToken()))
//        {
//            refreshToken(loginUser);
//        }
//    }

    /**
     * 删除用户身份信息
     */
//    public void delLoginUser(String token)
//    {
//        if (StringUtils.isNotEmpty(token))
//        {
//            String userKey = getTokenKey(token);
//            redisCache.deleteObject(userKey);
//        }
//    }

    /**
     * 创建令牌
     *
     * @param map 用户信息
     * @return 令牌
     */
    public String createToken(Map<String,String> map)
    {
        String token = UUID.randomUUID().toString().replace("-","");
        map.put("token",token);
        refreshToken(map);

        return createToken(token);
    }

    /**
     * 验证令牌有效期，相差不足20分钟，自动刷新缓存
     *
     * @param loginUser
     * @return 令牌
     */
//    public void verifyToken(LoginUser loginUser)
//    {
//        long expireTime = loginUser.getExpireTime();
//        long currentTime = System.currentTimeMillis();
//        if (expireTime - currentTime <= MILLIS_MINUTE_TEN)
//        {
//            refreshToken(loginUser);
//        }
//    }

    /**
     * 刷新令牌有效期
     *
     * @param map 登录信息
     */
    public void refreshToken(Map<String,String> map)
    {
        map.put("longTime", String.valueOf(System.currentTimeMillis()));
        map.put("expireTime", String.valueOf(Long.parseLong(map.get("longTime"))+expireTime * MILLIS_MINUTE));
        // 根据uuid将loginUser缓存
        String userKey = getTokenKey(map.get("token"));
        redisCache.setCacheObject(userKey, map, expireTime, TimeUnit.MINUTES);
    }

    /**
     * 从数据声明生成令牌
     *
     * @param uuid 数据声明
     * @return 令牌
     */
    private String createToken(String uuid)

    {
        Map<String, Object> claims = new HashMap<>();
        claims.put(Constants.LOGIN_USER_KEY, uuid);
        String token = Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret).compact();
        return token;
    }

    /**
     * 从令牌中获取数据声明
     *
     * @param token 令牌
     * @return 数据声明
     */
    private Claims parseToken(String token)
    {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 从令牌中获取用户名
     *
     * @param token 令牌
     * @return 用户名
     */
    public String getUsernameFromToken(String token)
    {
        Claims claims = parseToken(token);
        return claims.getSubject();
    }

    /**
     * 获取请求token
     *
     * @param request
     * @return token
     */
    private String getToken(HttpServletRequest request)
    {
        String token = request.getHeader(header);
        if (ObjectUtils.isEmpty(token) && token.startsWith(Constants.TOKEN_PREFIX))
        {
            token = token.replace(Constants.TOKEN_PREFIX, "");
        }
        return token;
    }

    private String getTokenKey(String uuid)
    {
        return CacheConstants.LOGIN_TOKEN_KEY + uuid;
    }
}
