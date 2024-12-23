package com.capricorn.summer.controller;

import com.capricorn.summer.entity.AjaxResult;
import com.capricorn.summer.service.Impl.VideoParsingRelationServiceImpl;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 *
 * @author
 */
@RestController
@RequestMapping("/videoParsing")
public class VideoParsingRelationController
{
    @Autowired
    VideoParsingRelationServiceImpl service;

    @GetMapping("/queryParameterList")
    public AjaxResult queryUserList(@RequestParam("pageNum") String pageNum,
                                     @RequestParam("pageSize") String pageSize){
        PageInfo<Map<String, String>> parameterList = service.queryParameterList(Integer.parseInt(pageNum), Integer.parseInt(pageSize));
        AjaxResult ajax = AjaxResult.success();
        ajax.put("data",parameterList);
        ajax.put("code","200");
        ajax.put("msg","success");
        return ajax;
    }

    @GetMapping("/initParseUrl")
    public AjaxResult initParseUrl(){
        PageInfo<Map<String, String>> pageInfo = service.queryParameterList(0, 9999);
        AjaxResult ajax = AjaxResult.success();
        ajax.put("data",pageInfo);
        ajax.put("code","200");
        ajax.put("msg","success");
        return ajax;
    }

    @PostMapping("/addParsingUrl")
    public AjaxResult addParsingUrl(@RequestBody Map<String,String> map){
        AjaxResult ajax = AjaxResult.success();
        Map<String, String> result = service.addParsingUrl(map.get("url"));
        ajax.put("code","success".equals(result.get("flag"))?"200":"400");
        ajax.put("msg","success".equals(result.get("flag"))?"修改成功":result.get("msg"));
        return ajax;
    }

    @PostMapping("/delParsingUrl")
    public AjaxResult delParsingUrl(@RequestBody Map<String,String> map){
        AjaxResult ajax = AjaxResult.success();
        Map<String, String> result = service.delParsingUrl(map.get("url"));
        ajax.put("code","success".equals(result.get("flag"))?"200":"400");
        ajax.put("msg","success".equals(result.get("flag"))?"修改成功":result.get("msg"));
        return ajax;
    }
}

