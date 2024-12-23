package com.capricorn.fund.controller;


import com.alibaba.druid.filter.config.ConfigTools;
import com.capricorn.common.utils.AttendanceInvocationHandler;
import com.capricorn.fund.job.factory.IOperateService;
import com.capricorn.fund.job.factory.OperateFactory;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/operate")
public class OperateTestController {


    @PostMapping("fundTypeTest")
    public Map<String,Object> findAllFund(@RequestBody Map<Object,Object> map){
        IOperateService service = OperateFactory.getInstance().get(map.get("type").toString());
        AttendanceInvocationHandler handler = new AttendanceInvocationHandler(service);
        IOperateService proxyInstance = (IOperateService)Proxy.newProxyInstance(service.getClass().getClassLoader(),
                service.getClass().getInterfaces(),
                handler);
        switch (map.get("method").toString()){
            case "add":
                proxyInstance.operateForAdd();
                break;
            case "del":
                proxyInstance.operateForDelete();
                break;
            case "modify":
                proxyInstance.operateForUpdate();
                break;
            case "select":
                proxyInstance.operateForQuery();
                break;
            default:
                proxyInstance.operateForQuery();
                break;

        }
        HashMap<String, Object> result = new HashMap<>();
        result.put("code",1000);
        result.put("msg","success");
        return result;
    }


    public static void main(String[] args) {
        //定义数据库密码，以123456为例
        String password = "capricorn";
        //调用druid的工具类来加密密码
        try {
            ConfigTools.main(new String[]{password});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
