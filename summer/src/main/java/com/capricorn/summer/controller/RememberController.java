package com.capricorn.summer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("summer")
public class RememberController {

    @RequestMapping("helloSummer")
    public String helloSummer(){
        return "index";
    }

}
