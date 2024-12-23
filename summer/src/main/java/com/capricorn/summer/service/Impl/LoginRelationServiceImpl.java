package com.capricorn.summer.service.Impl;

import com.capricorn.common.redis.CacheConstants;
import com.capricorn.common.redis.RedisCache;
import com.capricorn.summer.mapper.LoginRelationMapper;
import com.capricorn.summer.service.ILoginRelationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class LoginRelationServiceImpl implements ILoginRelationService {
    @Autowired
    private LoginRelationMapper loginRelationMapper;

    @Autowired
    private RedisCache redisCache;

    @Override
    public Map<String,Object> login(Map<String,String> map) {
        HashMap<String, Object> result = new HashMap<>();
        result.put("flag","false");
        //比较用户名
        Map<String, String> user = loginRelationMapper.queryUserByUserId(map);
        if (ObjectUtils.isEmpty(user)){
            log.info(String.format("用户%s不存在！",map.get("userName")));
            result.put("msg",String.format("用户%s不存在！",map.get("userName")));
            return result;
        }
        //比较验证码
        String verifyKey = CacheConstants.CAPTCHA_CODE_KEY + map.get("uuid");
        Object cacheObject = redisCache.getCacheObject(verifyKey);
        if (ObjectUtils.isEmpty(cacheObject) || !map.get("code").equals(cacheObject.toString())){
            result.put("msg",String.format("验证码不正确或过期。",map.get("userName")));
            log.info(String.format("验证码不正确或过期。",map.get("userName")));
            return result;
        }
        //比较密码
        if (user.get("USER_PASS").equals(map.get("userPassword"))){
            log.info(String.format("用户%s登录成功。",map.get("userName")));
            result.put("msg",String.format("用户%s登录成功。",map.get("userName")));
            result.put("flag","true");;
        }else if (!user.get("USER_PASS").equals(map.get("userPassword"))){
            log.info(String.format("用户名或密码不正确。",map.get("userName")));
            result.put("msg",String.format("用户名或密码不正确。",map.get("userName")));
            result.put("flag","false");;
        }
        return result;
    }
}
