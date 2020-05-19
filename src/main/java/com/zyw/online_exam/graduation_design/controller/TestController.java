package com.zyw.online_exam.graduation_design.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author cengyunwen
 * @version 1.0
 * @date 2020/4/17 17:00
 */
@Controller
public class TestController {
    @RequestMapping("/index")
    public String test1(){
        return "Login";
    }


}
