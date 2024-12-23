package com.capricorn.summer.controller;

import com.capricorn.summer.entity.AjaxResult;
import com.capricorn.summer.service.Impl.PermissionRelationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 *
 * @author
 */
@RestController
@RequestMapping("/permission")
public class PermissionRelationController
{
    @Autowired
    PermissionRelationServiceImpl permissionRelationService;

    @GetMapping("/sinbuc")
    public AjaxResult sinbuc(){

        AjaxResult ajax = AjaxResult.success();
        List<Map<String, String>> allRoutes = permissionRelationService.getAllRoutes();
        ajax.put("data",allRoutes);
        ajax.put("code","200");
        ajax.put("msg","success");
        return ajax;
    }
}

