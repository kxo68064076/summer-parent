package com.capricorn.summer.controller;

import com.capricorn.summer.entity.AjaxResult;
import com.capricorn.summer.service.Impl.UserRelationServiceImpl;
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
@RequestMapping("/user")
public class UserRelationController
{
    @Autowired
    UserRelationServiceImpl userRelationService;

    @GetMapping("/queryUserList")
    public AjaxResult queryUserList(@RequestParam("pageNum") String pageNum,
                                     @RequestParam("pageSize") String pageSize,
                                     @RequestParam("userName") String userName){
        AjaxResult ajax = AjaxResult.success();
        PageInfo<Map<String, String>> userList = userRelationService.queryUserList(Integer.parseInt(pageNum), Integer.parseInt(pageSize), userName);
        ajax.put("data",userList);
        ajax.put("code","200");
        ajax.put("msg","success");
        return ajax;
    }

    @PostMapping("/addUser")
    public AjaxResult addUser(@RequestBody Map<String,String> map){
        AjaxResult ajax = AjaxResult.success();
        Map<String, String> addUser = userRelationService.addUser(map);
        ajax.put("code","success".equals(addUser.get("flag"))?"200":"400");
        ajax.put("msg","success".equals(addUser.get("flag"))?"添加成功":addUser.get("msg"));
        return ajax;
    }

    @PostMapping("/editUser")
    public AjaxResult editUser(@RequestBody Map<String,String> map){
        AjaxResult ajax = AjaxResult.success();
        Map<String, String> addUser = userRelationService.editUser(map);
        ajax.put("code","success".equals(addUser.get("flag"))?"200":"400");
        ajax.put("msg","success".equals(addUser.get("flag"))?"修改成功":addUser.get("msg"));
        return ajax;
    }

    @PostMapping("/delUser")
    public AjaxResult delUser(@RequestBody Map<String,String> map){
        AjaxResult ajax = AjaxResult.success();
        Map<String, String> addUser = userRelationService.delUser(map.get("userId"));
        ajax.put("code","success".equals(addUser.get("flag"))?"200":"400");
        ajax.put("msg","success".equals(addUser.get("flag"))?"修改成功":addUser.get("msg"));
        if (ObjectUtils.isEmpty(ajax.get("msg"))){
            ajax.put("msg","操作失败，请联系管理员");
        }
        return ajax;
    }
}

