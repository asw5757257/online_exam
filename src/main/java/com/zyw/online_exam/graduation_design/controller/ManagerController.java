package com.zyw.online_exam.graduation_design.controller;

import com.zyw.online_exam.graduation_design.Dto;
import com.zyw.online_exam.graduation_design.common.Constant;
import com.zyw.online_exam.graduation_design.pojo.Major;
import com.zyw.online_exam.graduation_design.pojo.Manager;
import com.zyw.online_exam.graduation_design.pojo.Student;
import com.zyw.online_exam.graduation_design.pojo.Teacher;
import com.zyw.online_exam.graduation_design.service.ManagerService;
import com.zyw.online_exam.graduation_design.utils.CookieUtil;
import com.zyw.online_exam.graduation_design.utils.JacksonUtil;
import com.zyw.online_exam.graduation_design.utils.RedisPoolUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

/**
 * @author cengyunwen
 * @version 1.0
 * @date 2020/4/16 15:12
 */
@RestController
@RequestMapping("/manage")
public class ManagerController {
    @Autowired
    private ManagerService managerService;
    private static final Logger logger = LoggerFactory.getLogger(ManagerController.class);
    //管理员登陆
    @RequestMapping("/login")
    public Dto login(@RequestParam(value = "username")String username,
                     @RequestParam(value = "password")String password,
                     HttpSession session, HttpServletResponse response){
        Dto dto = managerService.login(username,password);
        Manager manager = (Manager)dto.getObject();
        if(dto.getState().equals(1)){
            CookieUtil.writeCookie(response,session.getId(),manager.getRole());
            RedisPoolUtil.setEx(session.getId(), JacksonUtil.objToString(dto.getObject()), 60 * 30);
        }
        return dto;
    }
    //获取教师信息
    @RequestMapping("/queryTeacher")
    public Dto queryTeacher(HttpServletRequest request,
                            @RequestParam(value = "start",defaultValue = "1")int start,
                            @RequestParam(value = "size",defaultValue = "5")int size,
                            @RequestParam (value = "query")String  query){
        String token = CookieUtil.readCookie(request);
        if (StringUtils.isEmpty(token)) {
            return Dto.getFailed("请先登录");
        }
        String manageStr = RedisPoolUtil.get(token);
        Manager manager = JacksonUtil.stringToObj(manageStr, Manager.class);
        if (manager == null) {
            return Dto.getFailed("请先登录");
        }
        //判断权限，业务处理
        if (Constant.Role.ROLE_ADMIN.equals(manager.getRole())) {
            if(query.equals("")){
                return managerService.getTeacher(start,size);
            }
            return managerService.queryTeacher(query,start,size);
        }
        return Dto.getFailed("不是管理员,无法操作");
    }
    /*@RequestMapping("/getTeacher")
    public Dto getTeacher(HttpServletRequest request,
                          @RequestParam(value = "start",defaultValue = "0")int start,
                          @RequestParam(value = "size",defaultValue = "5")int size){
        String token = CookieUtil.readCookie(request);
        if (StringUtils.isEmpty(token)) {
            return Dto.getFailed("请先登录");
        }
        String manageStr = RedisPoolUtil.get(token);
        Manager manager = JacksonUtil.stringToObj(manageStr, Manager.class);
        if (manager == null) {
            return Dto.getFailed("请先登录");
        }
        //判断权限，业务处理
        if (Constant.Role.ROLE_ADMIN.equals(manager.getRole())) {
            return managerService.getTeacher(start,size);
        }
        return Dto.getFailed("不是管理员,无法操作");
    }*/
    //新增或修改教师
    @RequestMapping("/addOrModifyTeacher")
    public Dto addOrModifyTeacher(HttpServletRequest request,
                                  @RequestBody Teacher teacher){
        String token = CookieUtil.readCookie(request);
        if (StringUtils.isEmpty(token)) {
            return Dto.getFailed("请先登录");
        }
        String manageStr = RedisPoolUtil.get(token);
        Manager manager = JacksonUtil.stringToObj(manageStr, Manager.class);
        if (manager == null) {
            return Dto.getFailed("请先登录");
        }
        //判断权限，业务处理
        if (Constant.Role.ROLE_ADMIN.equals(manager.getRole())) {
            return managerService.addOrModifyTeacher(teacher,manager);
        }
        return Dto.getFailed("不是管理员,无法操作");
    }
    //获取单条教师信息
    @RequestMapping("/getTeacherInfo")
    public Dto getTeacherInfo(HttpServletRequest request,
                              @RequestParam(value = "id")int id){
        String token = CookieUtil.readCookie(request);
        if (StringUtils.isEmpty(token)) {
            return Dto.getFailed("请先登录");
        }
        String manageStr = RedisPoolUtil.get(token);
        Manager manager = JacksonUtil.stringToObj(manageStr, Manager.class);
        if (manager == null) {
            return Dto.getFailed("请先登录");
        }
        //判断权限，业务处理
        if (Constant.Role.ROLE_ADMIN.equals(manager.getRole())) {
            return managerService.getTeacherInfo(id);
        }
        return Dto.getFailed("不是管理员,无法操作");
    }
    //删除教师
    @Transactional
    @RequestMapping("/delTeacher")
    public Dto delTeacher(HttpServletRequest request,
                          @RequestParam(value = "id")int id){
        String token = CookieUtil.readCookie(request);
        if (StringUtils.isEmpty(token)) {
            return Dto.getFailed("请先登录");
        }
        String manageStr = RedisPoolUtil.get(token);
        Manager manager = JacksonUtil.stringToObj(manageStr, Manager.class);
        if (manager == null) {
            return Dto.getFailed("请先登录");
        }
        //判断权限，业务处理
        if (Constant.Role.ROLE_ADMIN.equals(manager.getRole())) {
            return managerService.delTeacher(id);
        }
        return Dto.getFailed("不是管理员,无法操作");
    }
    //查询学生
    @RequestMapping("/queryStudent")
    public Dto queryStudent(HttpServletRequest request,
                            @RequestParam(value = "start",defaultValue = "1")int start,
                            @RequestParam(value = "size",defaultValue = "5")int size,
                            @RequestParam (value = "query")String  query){
        String token = CookieUtil.readCookie(request);
        if (StringUtils.isEmpty(token)) {
            return Dto.getFailed("请先登录");
        }
        String manageStr = RedisPoolUtil.get(token);
        Manager manager = JacksonUtil.stringToObj(manageStr, Manager.class);
        if (manager == null) {
            return Dto.getFailed("请先登录");
        }
        //判断权限，业务处理
        if (Constant.Role.ROLE_ADMIN.equals(manager.getRole())) {
            if(query.equals("")){
                return managerService.getStudent(start,size);
            }
            return managerService.queryStudent(query,start,size);
        }
        return Dto.getFailed("不是管理员,无法操作");
    }
    //新增或修改学生
    @RequestMapping("/addOrModifyStudent")
    public Dto addOrModifyStudent(HttpServletRequest request,@RequestBody Student student){
        String token = CookieUtil.readCookie(request);
        if (StringUtils.isEmpty(token)) {
            return Dto.getFailed("请先登录");
        }
        String manageStr = RedisPoolUtil.get(token);
        Manager manager = JacksonUtil.stringToObj(manageStr, Manager.class);
        if (manager == null) {
            return Dto.getFailed("请先登录");
        }
        //判断权限，业务处理
        if (Constant.Role.ROLE_ADMIN.equals(manager.getRole())) {
            return managerService.addOrModifyStudent(student,manager);
        }
        return Dto.getFailed("不是管理员,无法操作");
    }
    //获取单条学生信息
    @RequestMapping("/getStudentInfo")
    public Dto getStudentInfo(HttpServletRequest request,
                              @RequestParam(value = "id")int id){
        String token = CookieUtil.readCookie(request);
        if (StringUtils.isEmpty(token)) {
            return Dto.getFailed("请先登录");
        }
        String manageStr = RedisPoolUtil.get(token);
        Manager manager = JacksonUtil.stringToObj(manageStr, Manager.class);
        if (manager == null) {
            return Dto.getFailed("请先登录");
        }
        //判断权限，业务处理
        if (Constant.Role.ROLE_ADMIN.equals(manager.getRole())) {
            return managerService.getStudentInfo(id);
        }
        return Dto.getFailed("不是管理员,无法操作");
    }
    //删除学生
    @Transactional
    @RequestMapping("/delStudent")
    public Dto delStudent(HttpServletRequest request,
                          @RequestParam(value = "id")int id){
        String token = CookieUtil.readCookie(request);
        if (StringUtils.isEmpty(token)) {
            return Dto.getFailed("请先登录");
        }
        String manageStr = RedisPoolUtil.get(token);
        Manager manager = JacksonUtil.stringToObj(manageStr, Manager.class);
        if (manager == null) {
            return Dto.getFailed("请先登录");
        }
        //判断权限，业务处理
        if (Constant.Role.ROLE_ADMIN.equals(manager.getRole())) {
            return managerService.delStudent(id);
        }
        return Dto.getFailed("不是管理员,无法操作");
    }
    //查询教师关联班级
    @RequestMapping("/getTeacherAndMajor")
    public Dto getTeacherAndMajor(HttpServletRequest request,
                                  @RequestParam(value = "id")int id){
        String token = CookieUtil.readCookie(request);
        if (StringUtils.isEmpty(token)) {
            return Dto.getFailed("请先登录");
        }
        String manageStr = RedisPoolUtil.get(token);
        Manager manager = JacksonUtil.stringToObj(manageStr, Manager.class);
        if (manager == null) {
            return Dto.getFailed("请先登录");
        }
        //判断权限，业务处理
        if (Constant.Role.ROLE_ADMIN.equals(manager.getRole())) {
            return managerService.getTeacherAndMajor(id);

        }
        return Dto.getFailed("不是管理员,无法操作");
    }
    //新增教师关联班级
    @RequestMapping("/addTeacherAndMajor")
    public Dto addTeacherAndMajor(HttpServletRequest request,
                                  @RequestParam(value = "mid")int mid,
                                  @RequestParam(value = "tid")int tid){
        String token = CookieUtil.readCookie(request);
        if (StringUtils.isEmpty(token)) {
            return Dto.getFailed("请先登录");
        }
        String manageStr = RedisPoolUtil.get(token);
        Manager manager = JacksonUtil.stringToObj(manageStr, Manager.class);
        if (manager == null) {
            return Dto.getFailed("请先登录");
        }
        //判断权限，业务处理
        if (Constant.Role.ROLE_ADMIN.equals(manager.getRole())) {
            return managerService.addTeacherAndMajor(tid,mid,manager);
        }
        return Dto.getFailed("不是管理员,无法操作");

    }
    //删除教师关联班级
    @RequestMapping("/delTeacherAndMajor")
    public Dto delTeacherAndMajor(HttpServletRequest request,
                                  @RequestParam(value = "mid")int mid,
                                  @RequestParam(value = "tid")int tid){
        String token = CookieUtil.readCookie(request);
        if (StringUtils.isEmpty(token)) {
            return Dto.getFailed("请先登录");
        }
        String manageStr = RedisPoolUtil.get(token);
        Manager manager = JacksonUtil.stringToObj(manageStr, Manager.class);
        if (manager == null) {
            return Dto.getFailed("请先登录");
        }
        //判断权限，业务处理
        if (Constant.Role.ROLE_ADMIN.equals(manager.getRole())) {
            return managerService.delTeacherAndMajor(tid,mid);
        }
        return Dto.getFailed("不是管理员,无法操作");

    }
    //获取年级
    @RequestMapping("/getGrade")
    public Dto getGrade(HttpServletRequest request){
        String token = CookieUtil.readCookie(request);
        if (StringUtils.isEmpty(token)) {
            return Dto.getFailed("请先登录");
        }
        String manageStr = RedisPoolUtil.get(token);
        Manager manager = JacksonUtil.stringToObj(manageStr, Manager.class);
        if (manager == null) {
            return Dto.getFailed("请先登录");
        }
        //判断权限，业务处理
        if (Constant.Role.ROLE_ADMIN.equals(manager.getRole())) {
            return managerService.getGrade();
        }
        return Dto.getFailed("不是管理员,无法操作");
    }
    //获取指定年级的专业
    @RequestMapping("/getMajorByGrade")
    public Dto getMajorByGrade(HttpServletRequest request,
                        @RequestParam(value = "grade")String grade){
        String token = CookieUtil.readCookie(request);
        if (StringUtils.isEmpty(token)) {
            return Dto.getFailed("请先登录");
        }
        String manageStr = RedisPoolUtil.get(token);
        Manager manager = JacksonUtil.stringToObj(manageStr, Manager.class);
        if (manager == null) {
            return Dto.getFailed("请先登录");
        }
        //判断权限，业务处理
        if (Constant.Role.ROLE_ADMIN.equals(manager.getRole())) {
            return managerService.getMajorByGrade(grade);
        }
        return Dto.getFailed("不是管理员,无法操作");
    }
    @RequestMapping("/getMajor")
    public Dto getMajor(HttpServletRequest request){
        String token = CookieUtil.readCookie(request);
        if (StringUtils.isEmpty(token)) {
            return Dto.getFailed("请先登录");
        }
        String manageStr = RedisPoolUtil.get(token);
        Manager manager = JacksonUtil.stringToObj(manageStr, Manager.class);
        if (manager == null) {
            return Dto.getFailed("请先登录");
        }
        //判断权限，业务处理
        if (Constant.Role.ROLE_ADMIN.equals(manager.getRole())) {
            return managerService.getMajor();
        }
        return Dto.getFailed("不是管理员,无法操作");
    }
    //获取教师的班级
    @RequestMapping("/getTeacherMajor")
    public Dto getTeacherMajor(HttpServletRequest request,
                               @RequestParam(value = "start")int start,
                               @RequestParam(value = "size")int size,
                               @RequestParam(value = "id")int id){
        String token = CookieUtil.readCookie(request);
        if (StringUtils.isEmpty(token)) {
            return Dto.getFailed("请先登录");
        }
        String manageStr = RedisPoolUtil.get(token);
        Manager manager = JacksonUtil.stringToObj(manageStr, Manager.class);
        if (manager == null) {
            return Dto.getFailed("请先登录");
        }
        //判断权限，业务处理
        if (Constant.Role.ROLE_ADMIN.equals(manager.getRole())) {
            return managerService.getTeacherAndMajor(id,start,size);
        }
        return Dto.getFailed("不是管理员,无法操作");
    }
    //获取专业信息
    @RequestMapping("/queryMajor")
    public Dto queryMajor(HttpServletRequest request,
                          @RequestParam(value = "start")int start,
                          @RequestParam(value = "size")int size,
                          @RequestParam(value = "query")String query){
        String token = CookieUtil.readCookie(request);
        if (StringUtils.isEmpty(token)) {
            return Dto.getFailed("请先登录");
        }
        String manageStr = RedisPoolUtil.get(token);
        Manager manager = JacksonUtil.stringToObj(manageStr, Manager.class);
        if (manager == null) {
            return Dto.getFailed("请先登录");
        }
        //判断权限，业务处理
        if (Constant.Role.ROLE_ADMIN.equals(manager.getRole())) {
            return managerService.queryMajor(query,start,size);
        }
        return Dto.getFailed("不是管理员,无法操作");
    }
    //查看单条专业信息
    @RequestMapping("/getMajorInfo")
    public Dto getMajorInfo(HttpServletRequest request,
                            @RequestParam(value = "id")int id){
        String token = CookieUtil.readCookie(request);
        if (StringUtils.isEmpty(token)) {
            return Dto.getFailed("请先登录");
        }
        String manageStr = RedisPoolUtil.get(token);
        Manager manager = JacksonUtil.stringToObj(manageStr, Manager.class);
        if (manager == null) {
            return Dto.getFailed("请先登录");
        }
        //判断权限，业务处理
        if (Constant.Role.ROLE_ADMIN.equals(manager.getRole())) {
            return managerService.getMajorInfo(id);
        }
        return Dto.getFailed("不是管理员,无法操作");
    }
    //新增或修改专业信息
    @RequestMapping("addOrModifyMajor")
    public Dto addOrModifyMajor(HttpServletRequest request,
                                @RequestBody Major major){
        String token = CookieUtil.readCookie(request);
        if (StringUtils.isEmpty(token)) {
            return Dto.getFailed("请先登录");
        }
        String manageStr = RedisPoolUtil.get(token);
        Manager manager = JacksonUtil.stringToObj(manageStr, Manager.class);
        if (manager == null) {
            return Dto.getFailed("请先登录");
        }
        //判断权限，业务处理
        if (Constant.Role.ROLE_ADMIN.equals(manager.getRole())) {
            return managerService.addOrModifyMajor(major,manager);
        }
        return Dto.getFailed("不是管理员,无法操作");
    }
    //删除专业信息
    @RequestMapping("/delMajor")
    public Dto delMajor(HttpServletRequest request,
                        @RequestParam(value = "id")int id){
        String token = CookieUtil.readCookie(request);
        if (StringUtils.isEmpty(token)) {
            return Dto.getFailed("请先登录");
        }
        String manageStr = RedisPoolUtil.get(token);
        Manager manager = JacksonUtil.stringToObj(manageStr, Manager.class);
        if (manager == null) {
            return Dto.getFailed("请先登录");
        }
        //判断权限，业务处理
        if (Constant.Role.ROLE_ADMIN.equals(manager.getRole())) {
            return managerService.delMajor(id);
        }
        return Dto.getFailed("不是管理员,无法操作");
    }
    //重置教师密码
    @RequestMapping("/resetTeacherPwd")
    public Dto resetTeacherPwd(HttpServletRequest request,@RequestParam(value = "id")int id){
        String token = CookieUtil.readCookie(request);
        if (StringUtils.isEmpty(token)) {
            return Dto.getFailed("请先登录");
        }
        String manageStr = RedisPoolUtil.get(token);
        Manager manager = JacksonUtil.stringToObj(manageStr, Manager.class);
        if (manager == null) {
            return Dto.getFailed("请先登录");
        }
        //判断权限，业务处理
        if (Constant.Role.ROLE_ADMIN.equals(manager.getRole())) {
            return managerService.resetTeacherPwd(id);
        }
        return Dto.getFailed("不是管理员,无法操作");
    }
    //重置学生密码
    @RequestMapping("/resetStudentPwd")
    public Dto resetStudentPwd(HttpServletRequest request,@RequestParam(value = "id")int id){
        String token = CookieUtil.readCookie(request);
        if (StringUtils.isEmpty(token)) {
            return Dto.getFailed("请先登录");
        }
        String manageStr = RedisPoolUtil.get(token);
        Manager manager = JacksonUtil.stringToObj(manageStr, Manager.class);
        if (manager == null) {
            return Dto.getFailed("请先登录");
        }
        //判断权限，业务处理
        if (Constant.Role.ROLE_ADMIN.equals(manager.getRole())) {
            return managerService.resetStudentPwd(id);
        }
        return Dto.getFailed("不是管理员,无法操作");
    }
    //

}
