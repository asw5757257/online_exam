package com.zyw.online_exam.graduation_design.controller;

import com.zyw.online_exam.graduation_design.Dto;
import com.zyw.online_exam.graduation_design.pojo.Teacher;
import com.zyw.online_exam.graduation_design.service.MailService;
import com.zyw.online_exam.graduation_design.service.TeacherService;
import com.zyw.online_exam.graduation_design.utils.CookieUtil;
import com.zyw.online_exam.graduation_design.utils.JacksonUtil;
import com.zyw.online_exam.graduation_design.utils.RedisPoolUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Random;

/**
 * @author cengyunwen
 * @version 1.0
 * @date 2020/4/15 11:17
 */
@RestController
@RequestMapping("/teacher")
public class TeacherController {
    private static final Logger logger = LoggerFactory.getLogger(TeacherController.class);
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private MailService mailService;
    //登录
    @RequestMapping("/login")
    public Dto login(@RequestParam(value = "username")String username,
                     @RequestParam(value = "password")String password,
                     HttpSession session, HttpServletResponse response, HttpServletRequest request){
        Dto dto = teacherService.login(username,password);
        Teacher teacher = (Teacher)dto.getObject();
        if(dto.getState().equals(1)){
            CookieUtil.writeCookie(response, session.getId(),teacher.getRole());
            System.out.println(session.getId());
            System.out.println(CookieUtil.readCookie(request));
            RedisPoolUtil.setEx(session.getId(), JacksonUtil.objToString(dto.getObject()), 60 * 30);
        }
        return dto;
    }
    //获取用户信息
    @RequestMapping("/getUser")
    public Dto getUser(HttpServletRequest request){
        String token  = CookieUtil.readCookie(request);
        if(StringUtils.isEmpty(token)){
            logger.info("没有登录token");
            return Dto.getFailed("请先登录");
        }
        String teacherStr = RedisPoolUtil.get(token);
        Teacher teacher = JacksonUtil.stringToObj(teacherStr, Teacher.class);
        if (teacher != null) {
            logger.info("获取成功{}",teacher);
            return Dto.getSuccess(teacher);
        }
        logger.info("没有登录");
        return Dto.getFailed("请先登录");
    }
    //获取邮箱验证码
    @RequestMapping("/getCheckCodeByEmail")
    public Dto resetPasswordByEmail(@RequestParam(value = "email")String email){
        Dto dto = teacherService.checkMail(email);
        if(dto.getState().equals(0)){
            return dto;
        }
        String checkCode = String.valueOf(new Random().nextInt(899999) + 100000);
        String message = "您的验证码为："+checkCode+",此验证码只能使用一次，在5分钟内有效。验证成功则自动失效"+
                "如果您没有进行上述操作，请忽略此邮件。";
        try {
            mailService.sendSimpleMail(email, "注册验证码", message);
            RedisPoolUtil.setEx(email,checkCode,60*5);
        }catch (Exception e){
            return Dto.getFailed();
        }
        return Dto.getSuccess();
    }
    //验证邮箱
    @RequestMapping("/checkCheckCode")
    public Dto checkCheckCode(@RequestParam(value = "email")String email,
                              @RequestParam(value = "passwordNew")String passwordNew,
                              @RequestParam(value = "checkCode")String checkCode){
        Dto dto;
        dto = teacherService.checkMail(email);
        if(dto.getState().equals(0)){
            return dto;
        }
        String code = RedisPoolUtil.get(email);
        if(code.equals(checkCode)){
            dto = teacherService.resetPassword(email,passwordNew);
            RedisPoolUtil.del(email);
            return dto;
        }
        return Dto.getFailed("验证码不正确");

    }
    //已登录，重置密码
    @RequestMapping("/resetPassword")
    public Dto resetPassword(@RequestParam(value = "passwordOld")String passwordOld,
                             @RequestParam(value = "passwordNew")String passwordNew,
                             HttpServletRequest request  ){
        String token  = CookieUtil.readCookie(request);
        if(StringUtils.isEmpty(token)){
            logger.info("没有登录token");
            return Dto.getFailed("请先登录");
        }
        String teacherStr = RedisPoolUtil.get(token);
        Teacher teacher = JacksonUtil.stringToObj(teacherStr, Teacher.class);
        if (teacher == null) {
            logger.info("没有登录");
            return Dto.getFailed("请先登录");
        }
        return teacherService.resetTeacherPassword(passwordNew,passwordOld,teacher);
    }
    //获取管理专业
    @RequestMapping("getTeacherMajor")
    public Dto getTeacherMajor(HttpServletRequest request){
        String token  = CookieUtil.readCookie(request);
        if(StringUtils.isEmpty(token)){
            logger.info("没有登录token");
            return Dto.getFailed("请先登录");
        }
        String teacherStr = RedisPoolUtil.get(token);
        Teacher teacher = JacksonUtil.stringToObj(teacherStr, Teacher.class);
        if (teacher == null) {
            logger.info("没有登录");
            return Dto.getFailed("请先登录");
        }
        return teacherService.getTeacherMajor(teacher);
    }
    //获取学生
    @RequestMapping("/getStuScore")
    public Dto getStuScore(HttpServletRequest request){
        String token  = CookieUtil.readCookie(request);
        if(StringUtils.isEmpty(token)){
            logger.info("没有登录token");
            return Dto.getFailed("请先登录");
        }
        String teacherStr = RedisPoolUtil.get(token);
        Teacher teacher = JacksonUtil.stringToObj(teacherStr, Teacher.class);
        if (teacher == null) {
            logger.info("没有登录");
            return Dto.getFailed("请先登录");
        }
        return teacherService.getStuScore();
    }
}
