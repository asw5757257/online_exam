package com.zyw.online_exam.graduation_design.controller;

import com.zyw.online_exam.graduation_design.Dto;
import com.zyw.online_exam.graduation_design.common.Constant;
import com.zyw.online_exam.graduation_design.pojo.PaperQuestion;
import com.zyw.online_exam.graduation_design.pojo.Teacher;
import com.zyw.online_exam.graduation_design.service.PaperService;
import com.zyw.online_exam.graduation_design.utils.CookieUtil;
import com.zyw.online_exam.graduation_design.utils.JacksonUtil;
import com.zyw.online_exam.graduation_design.utils.RedisPoolUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author cengyunwen
 * @version 1.0
 * @date 2020/4/16 10:34
 */
@RestController
@RequestMapping("/paper")
public class PaperController {
    @Autowired
    private PaperService paperService;
    //查询试卷
    @RequestMapping("/queryPaper")
    public Dto queryPaper(HttpServletRequest request,
                          @RequestParam(value = "start")int start,
                          @RequestParam(value = "size")int size,
                          @RequestParam(value = "query")String query){
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
            return paperService.queryPaper(start,size,query);
        }
        return Dto.getFailed("不是教师,无法操作");
    }
    //查询我的试卷
    //todo
    //新增试卷
    @RequestMapping("/addPaper")
    public Dto addPaper(@RequestParam(value = "name")String name,
                        @RequestParam(value = "flag")String flag,
                        HttpServletRequest request){
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
            return paperService.addPaper(name,flag,teacher);
        }
        return Dto.getFailed("不是教师,无法操作");
    }
    //修改公开状态
    @RequestMapping("/modifyPaperPublicFlag")
    public Dto modifyPaperPublicFlag(@RequestParam(value = "id")int id,
                               @RequestParam(value = "flag")String flag,
                               HttpServletRequest request){
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
            return paperService.updatePublic(id,flag,teacher);
        }
        return Dto.getFailed("不是教师,无法操作");
    }
    //修改有效状态
    @RequestMapping("/modifyPaperFlag")
    public Dto modifyPaperFlag(@RequestParam(value = "id")int id,
                               @RequestParam(value = "flag")String flag,
                               HttpServletRequest request){
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
            return paperService.updateFlag(id,flag,teacher);
        }
        return Dto.getFailed("不是教师,无法操作");
    }
    //试卷预览
    @RequestMapping("/paperDetail")
    public Dto paperDetail(@RequestParam(value = "id")int id,HttpServletRequest request){
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
            return paperService.paperDetail(id,teacher);
        }
        return Dto.getFailed("不是教师,无法操作");
    }
    //手动组卷, 添加试题
    @RequestMapping("/compositionPaper")
    public Dto compositionPaper(@RequestBody PaperQuestion paperQuestion,HttpServletRequest request){
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
            return paperService.compositionPaper(paperQuestion,teacher);
        }
        return Dto.getFailed("不是教师,无法操作");
    }
    //删除试题
    @RequestMapping("/deleteQuestion")
    public Dto deleteQuestion(@RequestParam(value = "qid")int qid,
                              @RequestParam(value = "pid")int pid,
                              HttpServletRequest request){
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
            return paperService.deleteQuestionFromPaper(pid,qid,teacher);
        }
        return Dto.getFailed("不是教师,无法操作");
    }
    //清空试卷试题
    @RequestMapping("/emptyQuestions")
    public Dto emptyQuestions(@RequestParam(value = "id") int id,HttpServletRequest request){
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
            return paperService.emptyQuestionFromPaper(id,teacher);
        }
        return Dto.getFailed("不是教师,无法操作");
    }
    //发布试卷
    @RequestMapping("/assignmentPaper")
    public Dto assignmentPaper(@RequestParam(value = "pid") int pid,
                               @RequestParam(value = "mid")int mid,
                               HttpServletRequest request){
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
            return paperService.publishPaper(pid,mid,teacher);
        }
        return Dto.getFailed("不是教师,无法操作");
    }
    //自动组卷
    /**
     * @param name    试卷名称
     * @param subject      题目所属学科
     * @param isPublic   是否公开
     * @param optionNumber 选择题数量
     * @param optionScore  每道选择题分数
     */
    @RequestMapping("/autoBuildPaper")
    public Dto autoBuildPaper(@RequestParam(value = "name")String name,
                              @RequestParam(value = "subject") String subject,
                              @RequestParam(value = "isPublic",defaultValue = "N")String isPublic,
                              @RequestParam(value = "optionNumber",defaultValue = "20")int optionNumber,
                              @RequestParam(value = "optionScore",defaultValue = "5")String optionScore,
                              HttpServletRequest request){
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
            return paperService.autoBuildPaper(name,isPublic,optionNumber,optionScore,subject,teacher);
        }
        return Dto.getFailed("不是教师,无法操作");
    }
    //查询试卷试题
    @RequestMapping("/selectPaperQuestion")
    public Dto selectPaperQuestion(@RequestParam(value = "id")int id,
                                   HttpServletRequest request){
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
            return paperService.selectPaperQuestion(id);
        }
        return Dto.getFailed("不是教师,无法操作");
    }
}
