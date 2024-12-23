package com.capricorn.summer.controller;


import com.alibaba.druid.util.StringUtils;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import com.alibaba.fastjson.JSON;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping("/api/cacheBuilder")
public class CacheBuilderController {

    public static RemovalListener<String,Object> removalListener = notification -> {
        log.info(String.format("%s:%s从缓存中被移除",notification.getKey(),notification.getValue()));
    };
    public static Cache<String,Object> cache ;

    @PostMapping("setCache")
    public Map<String,Object> setCache(@RequestBody Map<Object,Object> map){
        initCache();
        map.keySet().forEach(key ->{
            cache.put(key.toString(),map.get(key));
        });
        HashMap<String, Object> result = new HashMap<>();
        result.put("code",1000);
        result.put("msg",String.format("输入了%s个元素到缓存",cache.size()));
        return result;
    }

    @GetMapping("getCache")
    public Map<String,Object> getCache(@RequestParam(value = "param") String param){
        String value = StringUtils.isEmpty((String) cache.getIfPresent(param))?"NULL":(String) cache.getIfPresent(param);
        HashMap<String, Object> result = new HashMap<>();
        result.put("code",1000);
        result.put("msg",String.format("结果为：%s,缓存内剩余元素个数%s", value,cache.size()));
        return result;
    }

    @GetMapping("getAllCache")
    public Map<String,Object> getAllCache(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("code",1000);
        result.put("msg","success");
        result.put("count",cache.size());
        ConcurrentMap<String, Object> resultMap = cache.asMap();
        result.put("result",JSON.toJSONString(resultMap));
        return result;
    }

    CacheBuilderController(){
        initCache();
    }
    public void initCache(){
        if (cache == null){
            synchronized (CacheBuilderController.class){
                if (cache == null){
                    cache =  CacheBuilder.newBuilder()
                            .expireAfterAccess(10l, TimeUnit.SECONDS)
                            .removalListener(removalListener)
                            .build();
                }
            }
        }
    }
}
