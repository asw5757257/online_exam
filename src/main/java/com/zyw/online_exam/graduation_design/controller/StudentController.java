package com.zyw.online_exam.graduation_design.controller;

import com.zyw.online_exam.graduation_design.Dto;
import com.zyw.online_exam.graduation_design.pojo.Student;
import com.zyw.online_exam.graduation_design.pojo.Teacher;
import com.zyw.online_exam.graduation_design.service.StudentService;
import com.zyw.online_exam.graduation_design.utils.CookieUtil;
import com.zyw.online_exam.graduation_design.utils.JacksonUtil;
import com.zyw.online_exam.graduation_design.utils.RedisPoolUtil;
import com.zyw.online_exam.graduation_design.vo.StudentVo;
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

/**
 * @author cengyunwen
 * @version 1.0
 * @date 2020/4/15 16:16
 */
@RestController
@RequestMapping("/student")
public class StudentController {
    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);
    @Autowired
    private StudentService studentService;

    @RequestMapping("/login")
    public Dto login(@RequestParam(value = "username")String username,
                     @RequestParam(value = "password")String password,
                     HttpSession session, HttpServletResponse response){
        Dto dto = studentService.login(username,password);
        if(dto.getState().equals(1)){
            CookieUtil.writeCookie(response,session.getId());
            RedisPoolUtil.setEx(session.getId(), JacksonUtil.objToString(dto.getObject()), 60 * 30);
        }
        return dto;
    }

    @RequestMapping("/getUser")
    public Dto getUser(HttpServletRequest request){
        String token  = CookieUtil.readCookie(request);
        if(StringUtils.isEmpty(token)){
            logger.info("没有登录token");
            return Dto.getFailed("请先登录");
        }
        String studentStr = RedisPoolUtil.get(token);
        Student student = JacksonUtil.stringToObj(studentStr, Student.class);
        if (student != null) {
            StudentVo studentVo = studentService.setStudentVo(student);
            return Dto.getSuccess(studentVo);
        }
        return Dto.getFailed("请先登录");
    }
    //登录过后修改密码
    @RequestMapping("/resetPassword")
    public Dto resetPassword(@RequestParam(value = "passwordOld")String passwordOld,
                             @RequestParam(value = "passwordNew")String passwordNew,
                             HttpServletRequest request ){
        String token  = CookieUtil.readCookie(request);
        if(StringUtils.isEmpty(token)){
            logger.info("没有登录token");
            return Dto.getFailed("请先登录");
        }
        String studentStr = RedisPoolUtil.get(token);
        Student student = JacksonUtil.stringToObj(studentStr, Student.class);
        if (student == null) {
            logger.info("没有登录");
            return Dto.getFailed("请先登录");
        }
        return studentService.resetStudentPassword(passwordNew,passwordOld,student);
    }
    //查询待完成试卷
    @RequestMapping("/getUnfinishedPaper")
    public Dto getUnfinishedPaper(HttpServletRequest request){
        String token  = CookieUtil.readCookie(request);
        if(StringUtils.isEmpty(token)){
            logger.info("没有登录token");
            return Dto.getFailed("请先登录");
        }
        String studentStr = RedisPoolUtil.get(token);
        Student student = JacksonUtil.stringToObj(studentStr, Student.class);
        if (student == null) {
            logger.info("没有登录");
            return Dto.getFailed("请先登录");
        }
        return studentService.getUnfinishedPaper(student);
    }
    //获取试卷内容
    @RequestMapping("/getPaperDetail")
    public Dto getPaperDetail(HttpServletRequest request,
                              @RequestParam(value = "id") Integer id){
        String token  = CookieUtil.readCookie(request);
        if(StringUtils.isEmpty(token)){
            logger.info("没有登录token");
            return Dto.getFailed("请先登录");
        }
        String studentStr = RedisPoolUtil.get(token);
        Student student = JacksonUtil.stringToObj(studentStr, Student.class);
        if (student == null) {
            logger.info("没有登录");
            return Dto.getFailed("请先登录");
        }
        return studentService.getPaper(id,student);
    }
    //学生交卷
    @RequestMapping("/submitPaper")
    public Dto submitPaper(@RequestParam(value = "id") Integer id,
                           @RequestParam(value = "questionAndAnswer")String questionAndAnswer,
                           HttpServletRequest request){
        String token  = CookieUtil.readCookie(request);
        if(StringUtils.isEmpty(token)){
            logger.info("没有登录token");
            return Dto.getFailed("请先登录");
        }
        String studentStr = RedisPoolUtil.get(token);
        Student student = JacksonUtil.stringToObj(studentStr, Student.class);
        if (student == null) {
            logger.info("没有登录");
            return Dto.getFailed("请先登录");
        }
        return studentService.submitPaper(id,student,questionAndAnswer);
    }
    //查询成绩
    @RequestMapping("/queryScore")
    public Dto queryScore(HttpServletRequest request){
        try{
            String token  = CookieUtil.readCookie(request);
            if(StringUtils.isEmpty(token)){
                logger.info("没有登录token");
                return Dto.getFailed("请先登录");
            }
            String studentStr = RedisPoolUtil.get(token);
            Student student = JacksonUtil.stringToObj(studentStr, Student.class);
            if (student == null) {
                logger.info("没有登录");
                return Dto.getFailed("请先登录");
            }
            return studentService.queryScore(student);
        }catch (Exception e){
            logger.error("学生查询成绩{}",e);
            return Dto.getFailed("未登录或无权限");
        }
    }
}
