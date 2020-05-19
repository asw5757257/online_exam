package com.zyw.online_exam.graduation_design.controller;

import com.zyw.online_exam.graduation_design.Dto;
import com.zyw.online_exam.graduation_design.common.Constant;
import com.zyw.online_exam.graduation_design.pojo.Manager;
import com.zyw.online_exam.graduation_design.pojo.Notice;
import com.zyw.online_exam.graduation_design.pojo.Teacher;
import com.zyw.online_exam.graduation_design.service.NoticeService;
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
 * @date 2020/4/17 11:41
 */
@RestController
@RequestMapping("/notice")
public class NoticeController {
    @Autowired
    private NoticeService noticeService;
    //首页展示通知
    @RequestMapping("/showNotice")
    public Dto showNotice(HttpServletRequest request,
                          @RequestParam(value = "start")int start,
                          @RequestParam(value = "size")int size){
        String token = CookieUtil.readCookie(request);
        if (StringUtils.isEmpty(token)){
            return Dto.getFailed("请先登录");
        }
        String userStr = RedisPoolUtil.get(token);
        if (StringUtils.isBlank(userStr)) {
            return Dto.getFailed("请先登录");
        }
        return noticeService.queryNotice(start,size);
    }
    //查询通知
    @RequestMapping("/queryNotice")
    public Dto queryNotice(HttpServletRequest request,
                           @RequestParam(value = "start")int start,
                           @RequestParam(value = "size")int size,
                           @RequestBody Notice notice){
        String token = CookieUtil.readCookie(request);
        if (StringUtils.isEmpty(token)){
            return Dto.getFailed("请先登录");
        }
        String userStr = RedisPoolUtil.get(token);
        if (StringUtils.isBlank(userStr)) {
            return Dto.getFailed("请先登录");
        }
        notice.setFlag("Y");
        return noticeService.queryNotice(start,size);
    }
    //新增或更新通知
    @RequestMapping("/addOrModifyNotice")
    public Dto addOrModifyNotice(HttpServletRequest request, @RequestBody Notice notice){
        String token = CookieUtil.readCookie(request);
        if (StringUtils.isEmpty(token)){
            return Dto.getFailed("请先登录");
        }
        String manageStr = RedisPoolUtil.get(token);
        Teacher teacher = JacksonUtil.stringToObj(manageStr, Teacher.class);
        if(teacher == null){
            return Dto.getFailed("请先登录");
        }
        if(Constant.Role.ROLE_TEACHER.equals(teacher.getRole())){
            return noticeService.addOrModifyNotice(notice,teacher);
        }
        return Dto.getFailed("不是管理员,无法操作");
    }
    //获取单条通知内容
    @RequestMapping("/getNotice")
    public Dto getNotice(HttpServletRequest request,@RequestParam(value = "id")int id){
        String token = CookieUtil.readCookie(request);
        if (StringUtils.isEmpty(token)){
            return Dto.getFailed("请先登录");
        }
        String manageStr = RedisPoolUtil.get(token);
        Manager manager = JacksonUtil.stringToObj(manageStr, Manager.class);
        if(manager == null){
            return Dto.getFailed("请先登录");
        }
        if(Constant.Role.ROLE_ADMIN.equals(manager.getRole())){
            return noticeService.getNotice(id);
        }
        return Dto.getFailed("不是管理员,无法操作");
    }
}
