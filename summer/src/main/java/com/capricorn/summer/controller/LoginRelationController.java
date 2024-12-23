package com.capricorn.summer.controller;

import com.capricorn.common.redis.CacheConstants;
import com.capricorn.common.redis.RedisCache;
import com.capricorn.summer.config.Base64;
import com.capricorn.summer.entity.AjaxResult;
import com.capricorn.summer.service.Impl.LoginRelationServiceImpl;
import com.capricorn.summer.service.TokenService;
import com.google.code.kaptcha.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author
 */

@RestController
@RequestMapping("/login")
public class LoginRelationController
{
    @Resource(name = "captchaProducer")
    private Producer captchaProducer;

    @Resource(name = "captchaProducerMath")
    private Producer captchaProducerMath;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private LoginRelationServiceImpl loginRelationService;

    @Autowired
    private TokenService tokenService;

    // 令牌有效期（默认30分钟）
    @Value("${token.expireTime}")
    private int expireTime;

    /**
     * 生成验证码
     */
    @GetMapping("/captchaImage")
    public AjaxResult getCode()
    {
        AjaxResult ajax = AjaxResult.success();

        // 保存验证码信息
        String uuid = UUID.randomUUID().toString();
        String verifyKey = CacheConstants.CAPTCHA_CODE_KEY + uuid;

        String capStr = null, code = null;
        BufferedImage image = null;

        // 生成验证码
        capStr = code = captchaProducer.createText();
        image = captchaProducer.createImage(capStr);
        redisCache.setCacheObject(verifyKey, code, 5, TimeUnit.MINUTES);
        // 转换流信息写出
        FastByteArrayOutputStream os = new FastByteArrayOutputStream();
        try
        {
            ImageIO.write(image, "jpg", os);
        }
        catch (IOException e)
        {
            return AjaxResult.error(e.getMessage());
        }
        ajax.put("uuid", uuid);
        ajax.put("img", Base64.encode(os.toByteArray()));
        return ajax;
    }

    @PostMapping("/login")
    public AjaxResult login(@RequestBody Map<String,String> map, HttpServletRequest servletRequest) {
        Map<String, Object> loginResult = loginRelationService.login(map);
        AjaxResult ajax = AjaxResult.success();
        if (CacheConstants.TRUE.equals(loginResult.get("flag"))){
            String token = tokenService.createToken(map);
            String sessionId = servletRequest.getSession().getId();
            redisCache.setCacheObject(sessionId,map.get("userName"),expireTime,TimeUnit.MINUTES);
            ajax.put("token",token);
            ajax.put("msg",loginResult.get("msg"));
        }else {
            ajax.put("msg",loginResult.get("msg"));
            ajax.put("code","403");
        }
        return ajax;
    }

    @GetMapping("/checkLogin")
    public AjaxResult checkLogin(@RequestParam(value = "token") String token){

        AjaxResult ajax = AjaxResult.success();
        Map<String, Object> loginUser = tokenService.getLoginUser(token);
        ajax.put("userInfo",loginUser);
        return ajax;
    }
}

