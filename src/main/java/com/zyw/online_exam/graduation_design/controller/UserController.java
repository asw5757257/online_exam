package com.zyw.online_exam.graduation_design.controller;

import com.zyw.online_exam.graduation_design.Dto;
import com.zyw.online_exam.graduation_design.utils.CookieUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author cengyunwen
 * @version 1.0
 * @date 2020/4/15 10:31
 */
@RestController("/user")
public class UserController {
    //用户登出
    @RequestMapping("/logout")
    public Dto logout(HttpServletRequest request, HttpServletResponse response){
        String token = CookieUtil.readCookie(request);
        CookieUtil.deleteCookie(request,response);
        //RedisPoolUtil.del(token);
        return Dto.getSuccess("登出成功");
    }
    //学生退出
    @RequestMapping("/studentLogout")
    public Dto stuLogout(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.removeAttribute("name");
        return Dto.getSuccess("退出成功");
    }
}
