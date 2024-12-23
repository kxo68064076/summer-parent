package com.capricorn.fund.job.factory;

import com.capricorn.common.utils.SpringUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class OperateFactory {
    private static volatile OperateFactory instance;
    private static Map<String,IOperateService> operateMap;

    private OperateFactory(){
        operateMap = new ConcurrentHashMap<String,IOperateService>();
        IOperateService operateService = SpringUtil.getBean(FundOperateServiceImpl.class);
        operateMap.put("FUND",operateService);

    }

    public static OperateFactory getInstance(){
        if (instance==null){
            synchronized (OperateFactory.class){
                if (instance==null){
                    instance = new OperateFactory();
                }
            }
        }
        return instance;
    }

    public IOperateService get(String type){
        return operateMap.get(type);
    }
}
