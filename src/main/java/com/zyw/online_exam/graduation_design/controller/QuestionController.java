package com.zyw.online_exam.graduation_design.controller;

import com.zyw.online_exam.graduation_design.Dto;
import com.zyw.online_exam.graduation_design.common.Constant;
import com.zyw.online_exam.graduation_design.exception.MyException;
import com.zyw.online_exam.graduation_design.pojo.Manager;
import com.zyw.online_exam.graduation_design.pojo.Question;
import com.zyw.online_exam.graduation_design.pojo.Teacher;
import com.zyw.online_exam.graduation_design.service.ManagerService;
import com.zyw.online_exam.graduation_design.service.QuestionService;
import com.zyw.online_exam.graduation_design.utils.CookieUtil;
import com.zyw.online_exam.graduation_design.utils.JacksonUtil;
import com.zyw.online_exam.graduation_design.utils.RedisPoolUtil;
import com.zyw.online_exam.graduation_design.vo.QuestionVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.function.ServerResponse;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author cengyunwen
 * @version 1.0
 * @date 2020/4/15 10:41
 */
@RestController()
@RequestMapping("/question")
public class QuestionController {
    private static final Logger logger = LoggerFactory.getLogger(QuestionController.class);
    @Autowired
    private QuestionService questionService;
    @Autowired
    private ManagerService managerService;
    @RequestMapping("/queryQuestions")
    public Dto queryQuestions(HttpServletRequest request,
                              @RequestParam(value = "start")int start,
                              @RequestParam(value = "size")int size,
                              @RequestParam(value = "query")String query)  {
        String token = CookieUtil.readCookie(request);
        if (StringUtils.isEmpty(token)) {
            return Dto.getFailed("请先登录");
        }
        String teacherStr = RedisPoolUtil.get(token);
        Teacher teacher = JacksonUtil.stringToObj(teacherStr, Teacher.class);
        if (teacher == null) {
            return  Dto.getFailed("请先登录");
        }
        if(Constant.Role.ROLE_TEACHER.equals(teacher.getRole())){

            return questionService.getAll(start,size,query);
        }
        return Dto.getFailed();
    }
    //查询我的试题
    /*@RequestMapping("queryMyQuestion")
    public Dto queryMyQuestion(HttpServletRequest request,
                               @RequestParam(value = "start",defaultValue = "0")int start,
                               @RequestParam(value = "size",defaultValue = "5")int size){
        String token = CookieUtil.readCookie(request);
        if (StringUtils.isEmpty(token)) {
            return Dto.getFailed("请先登录");
        }
        String teacherStr = RedisPoolUtil.get(token);
        Teacher teacher = JacksonUtil.stringToObj(teacherStr, Teacher.class);
        if (teacher == null) {
            return  Dto.getFailed("请先登录");
        }
        if(Constant.Role.ROLE_TEACHER.equals())


    }*/
    @RequestMapping("/getMajor")
    public Dto getMajor(HttpServletRequest request){
        String token = CookieUtil.readCookie(request);
        if (StringUtils.isEmpty(token)) {
            return Dto.getFailed("请先登录");
        }
        String teacherStr = RedisPoolUtil.get(token);
        Teacher teacher = JacksonUtil.stringToObj(teacherStr, Teacher.class);
        if (teacher == null) {
            return  Dto.getFailed("请先登录");
        }
        //判断权限，业务处理
        if (Constant.Role.ROLE_TEACHER.equals(teacher.getRole())) {
            return managerService.getMajor();
        }
        return Dto.getFailed("不是教师,无法操作");
    }
    //获取单试题信息
        @RequestMapping("/getQuestionInfo")
    public Dto getQuestionInfo(HttpServletRequest request,
                               @RequestParam(value = "id") int tid){
        String token = CookieUtil.readCookie(request);
        if (StringUtils.isEmpty(token)) {
            return Dto.getFailed("请先登录");
        }
        String teacherStr = RedisPoolUtil.get(token);
        Teacher teacher = JacksonUtil.stringToObj(teacherStr, Teacher.class);
        if (teacher == null) {
            return  Dto.getFailed("请先登录");
        }
        if(Constant.Role.ROLE_TEACHER.equals(teacher.getRole())){
            return questionService.getById(tid);
        }
        return Dto.getFailed("不是教师,无法操作");
    }
    //新增试题
    @RequestMapping("/addQuestion")
    public Dto addQuestion(HttpServletRequest request,
                           @RequestBody Question question){
        String token = CookieUtil.readCookie(request);
        if (StringUtils.isEmpty(token)) {
            return Dto.getFailed("请先登录");
        }
        String teacherStr = RedisPoolUtil.get(token);
        Teacher teacher = JacksonUtil.stringToObj(teacherStr, Teacher.class);
        if (teacher == null) {
            return  Dto.getFailed("请先登录");
        }
        if(Constant.Role.ROLE_TEACHER.equals(teacher.getRole())){
            return questionService.add(question,teacher);
        }
        return Dto.getFailed("不是教师,无法操作");
    }
    //修改试题状态
    @RequestMapping("/modifyQuestionFlag")
    public Dto modifyQuestionFlag(HttpServletRequest request,
                                  @RequestParam(value = "id")int qid,
                                  @RequestParam(value = "flag")String flag){
        String token = CookieUtil.readCookie(request);
        if (StringUtils.isEmpty(token)) {
            return Dto.getFailed("请先登录");
        }
        String teacherStr = RedisPoolUtil.get(token);
        Teacher teacher = JacksonUtil.stringToObj(teacherStr, Teacher.class);
        if (teacher == null) {
            return  Dto.getFailed("请先登录");
        }
        if(Constant.Role.ROLE_TEACHER.equals(teacher.getRole())){
            return questionService.updateFlag(qid,flag,teacher);
        }
        return Dto.getFailed("不是教师,无法操作");
    }
    //查询科目列表
    @RequestMapping("/selectSubjects")
    public Dto selectSubjects(HttpServletRequest request){
        String token = CookieUtil.readCookie(request);
        if (StringUtils.isEmpty(token)) {
            return Dto.getFailed("请先登录");
        }
        String teacherStr = RedisPoolUtil.get(token);
        Teacher teacher = JacksonUtil.stringToObj(teacherStr, Teacher.class);
        if (teacher == null) {
            return  Dto.getFailed("请先登录");
        }
        if(Constant.Role.ROLE_TEACHER.equals(teacher.getRole())){
            return questionService.getSubject();
        }
        return Dto.getFailed("不是教师,无法操作");
    }
}
