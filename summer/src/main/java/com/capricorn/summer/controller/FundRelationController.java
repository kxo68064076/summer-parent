package com.capricorn.summer.controller;

import com.capricorn.common.utils.BecomingRichUtil;
import com.capricorn.summer.entity.AjaxResult;
import com.capricorn.summer.service.Impl.FundRelationServiceImpl;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/fund")
public class FundRelationController {

    @Autowired
    FundRelationServiceImpl fundRelationService;

    @Value("${IP}")
    private String IP;

    @GetMapping ("/queryHoldingFundList")
    public AjaxResult queryHoldingFundList(@RequestParam("pageNum") String pageNum,
                                    @RequestParam("pageSize") String pageSize,
                                    @RequestParam("fund") String fund,
                                    @RequestParam("userName") String userName){
        AjaxResult ajax = AjaxResult.success();
        PageInfo<Map<String, Object>> queryFundList = fundRelationService.queryHoldingFundList(Integer.parseInt(pageNum), Integer.parseInt(pageSize), fund, userName);
        ajax.put("data",queryFundList);
        ajax.put("code","200");
        ajax.put("msg","success");
        return ajax;
    }

    @GetMapping ("/queryCollectList")
    public AjaxResult queryCollectList(@RequestParam("pageNum") String pageNum,
                                           @RequestParam("pageSize") String pageSize,
                                           @RequestParam("fund") String fund,
                                           @RequestParam("userName") String userName){
        AjaxResult ajax = AjaxResult.success();
        PageInfo<Map<String, Object>> queryFundList = fundRelationService.queryCollectList(Integer.parseInt(pageNum), Integer.parseInt(pageSize), fund, userName);
        ajax.put("data",queryFundList);
        ajax.put("code","200");
        ajax.put("msg","success");
        return ajax;
    }

    @PostMapping ("/editHoldingFund")
    public AjaxResult editHoldingFund(@RequestBody Map<String,String> map){
        AjaxResult ajax = AjaxResult.success();
        if (Float.parseFloat(map.get("HOW_MANY"))==0){
            map.put("IF_BUY","0");
        }else {
            map.put("IF_BUY","1");
        }
        int result = fundRelationService.editHoldingFund(map);
        ajax.put("code",result!=0?"200":"400");
        ajax.put("msg",result!=0?"success":"fail");
        return ajax;
    }

    @GetMapping ("/verifyFundCode")
    public AjaxResult verifyFundCode(@RequestParam("fund") String fund){
        AjaxResult ajax = AjaxResult.success();
        String fund_code = BecomingRichUtil.req(fund, IP);
        if ("005669".equals(fund)){
            Map<String, Object> map = new HashMap<>();
            map.put("FUND_CODE","005669");
            map.put("FUND_NAME","前海开源公用事业股票");
            ajax.put("data",map);
            ajax.put("code","200");
        }else if (ObjectUtils.isEmpty(fund_code)){
            ajax.put("code","400");
        }else if (!ObjectUtils.isEmpty(fund)){
           Map<String, Object> map = BecomingRichUtil.regular(fund_code,null);
            ajax.put("data",map);
            ajax.put("code","200");
        }
        return ajax;
    }

    @PostMapping ("/addCollectFund")
    public AjaxResult addCollectFund(@RequestBody Map<String,String> map){
        AjaxResult ajax = AjaxResult.success();
        HashMap<String, String> result = fundRelationService.addCollectFund(map);
        if ("success".equals(result.get("result"))){
            ajax.put("code","200");
        }else {
            ajax.put("code","400");
            ajax.put("msg",result.get("msg"));
        }

        return ajax;
    }

}
