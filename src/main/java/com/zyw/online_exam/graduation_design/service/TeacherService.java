package com.zyw.online_exam.graduation_design.service;

import com.zyw.online_exam.graduation_design.dao.*;
import com.zyw.online_exam.graduation_design.pojo.*;
import com.zyw.online_exam.graduation_design.utils.Md5Util;
import com.zyw.online_exam.graduation_design.vo.ScoreVo;
import org.apache.commons.lang3.StringUtils;
import com.zyw.online_exam.graduation_design.Dto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author cengyunwen
 * @version 1.0
 * @date 2020/4/10 12:01
 */
@Service
public class TeacherService {
    @Autowired
    private TeacherDao teacherDao;
    @Autowired
    private TeacherAndMajorDao teacherAndMajorDao;
    @Autowired
    private MajorDao majorDao;
    @Autowired
    private PaperDao paperDao;
    @Autowired
    private StudentDao studentDao;
    @Autowired
    private ScoreDao scoreDao;
    //登录
    public Dto login(String username,String password){
        Teacher teacher;
        if (StringUtils.isAnyBlank(username, password)) {
            return Dto.getFailed("登陆失败:请检查");
        }
        teacher = teacherDao.findAllByUsername(username);
        if(teacher == null){
            return Dto.getFailed("登陆失败:用户名不存在");
        }
        String md5Password = Md5Util.md5EncodeUtf8(password);
        teacher = teacherDao.findAllByUsernameAndPassword(username,md5Password);
        if(teacher == null){
            return Dto.getFailed("登陆失败:密码不正确");
        }
        teacher.setPassword(StringUtils.EMPTY);
        return Dto.getSuccess("登陆成功",teacher);
    }

    //未登录时邮箱验证码登录时查询邮箱是否存在
    public Dto checkMail(String email){
        Teacher tea;
        tea = teacherDao.findAllByEmail(email);
        if(tea == null){
            return Dto.getFailed("该邮箱不存在用户");
        }
        return Dto.getSuccess();
    }
    //未登录重置密码
    public Dto resetPassword(String email,String passwordNew){
        Teacher tea;
        tea = teacherDao.findAllByEmail(email);
        tea.setPassword(Md5Util.md5EncodeUtf8(passwordNew));
        tea.setLastUpdatedBy(tea.getId());
        tea = teacherDao.save(tea);
        if(tea!=null){
            return Dto.getSuccess("密码更新成功");
        }
        return Dto.getSuccess("密码更新失败");
    }
    //登陆过后修改密码
    public Dto resetTeacherPassword(String newPwd,String oldPwd,Teacher teacher){
        Teacher tea;
        tea = teacherDao.findAllByPasswordAndId(Md5Util.md5EncodeUtf8(oldPwd),teacher.getId());
        if(tea == null){
            return Dto.getFailed("旧密码错误");
        }
        teacher.setPassword(Md5Util.md5EncodeUtf8(newPwd));
        teacher.setLastUpdatedBy(teacher.getId());
        teacherDao.save(teacher);
        return Dto.getSuccess("密码更新成功");

    }
    //获取教师管理的专业
    public Dto getTeacherMajor(Teacher teacher){
        List<TeacherAndMajor> majorIdList = teacherAndMajorDao.findAllByTeacherId(teacher.getId());
        List<Major> list = majorDao.findAll();
        List<Major> result = new ArrayList<>();
        for(TeacherAndMajor teacherAndMajor:majorIdList){
            for(Major major:list){
                if(major.getId().equals(teacherAndMajor.getMajorId())){
                    result.add(major);
                    break;
                }
            }
        }
        return Dto.getSuccess(result);
    }
    //获取成绩
    public Dto getStuScore(){
        List<ScoreDetail> list = scoreDao.findAllScore();
        return Dto.getSuccess(list);
    }
}
