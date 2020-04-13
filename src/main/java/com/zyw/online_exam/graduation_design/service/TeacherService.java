package com.zyw.online_exam.graduation_design.service;

import com.zyw.online_exam.graduation_design.dao.*;
import com.zyw.online_exam.graduation_design.pojo.*;
import com.zyw.online_exam.graduation_design.utils.Md5Util;
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
    //未登录时手机验证码登录和重置密码
    //todo
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
    public Dto getTeacherMajor(){
        List<StudentScore> list = new ArrayList<>();
        List<Student> studentList = studentDao.findAll();
        List<Paper> paperList = paperDao.findAll();
        List<Score> scoreList = scoreDao.findAll();
        Map<Score,String> map = new HashMap<>();
        StudentScore studentScore;
        for(Score score:scoreList){
            for(Student student:studentList){
                if(student.getId().equals(score.getStudentId())){
                    map.put(score,student.getName());
                }
            }
        }
        for(Score key : map.keySet()){
            for(Paper paper:paperList){
                if(key.getPaperId().equals(paper.getId())){
                    studentScore = new StudentScore(map.get(key),key.getStudentId(),Integer.valueOf(key.getScore()),paper.getName());
                    list.add(studentScore);
                }
            }
        }
        return Dto.getSuccess(list);




    }
}
