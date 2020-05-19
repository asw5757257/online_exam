package com.zyw.online_exam.graduation_design.service;

import com.zyw.online_exam.graduation_design.Dto;
import com.zyw.online_exam.graduation_design.common.Constant;
import com.zyw.online_exam.graduation_design.dao.*;
import com.zyw.online_exam.graduation_design.pojo.*;
import com.zyw.online_exam.graduation_design.utils.Md5Util;
import com.zyw.online_exam.graduation_design.vo.StudentVo;
import com.zyw.online_exam.graduation_design.vo.TeacherAndMajorVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author cengyunwen
 * @version 1.0
 * @date 2020/4/13 11:03
 */
@Service
public class ManagerService {
    @Autowired
    private TeacherDao teacherDao;
    @Autowired
    private StudentDao studentDao;
    @Autowired
    private TeacherAndMajorDao teacherAndMajorDao;
    @Autowired
    private MajorDao majorDao;
    @Autowired
    private ManagerDao managerDao;
    //管理员登录
    public Dto login(String username,String password){
        Manager manager;
        if (StringUtils.isAnyBlank(username, password)) {
            return Dto.getFailed("登陆失败:请检查");
        }
        manager = managerDao.findAllByUsername(username);
        if(manager == null){
            return Dto.getFailed("登陆失败:用户名不存在");
        }
        String md5Password = Md5Util.md5EncodeUtf8(password);
        manager = managerDao.findAllByUsernameAndPassword(username,md5Password);
        if(manager == null){
            return Dto.getFailed("登陆失败:密码不正确");
        }
        manager.setPassword(StringUtils.EMPTY);
        return Dto.getSuccess("登陆成功",manager);
    }
    //查询教师
    public Dto queryTeacher(String  query,int start,int size){
        Pageable pageable = PageRequest.of(start-1,size);
        Page<Teacher> page = teacherDao.findAllByNameLike(pageable,"%"+query+"%");
        List<Teacher> list = page.getContent();
        int total = teacherDao.countAllByNameLike("%"+query+"%");
        if(list.size() == 0){
            return Dto.getFailed("没有查询结果");
        }
        for(Teacher tea : list){
            tea.setPassword(StringUtils.EMPTY);
        }
        return Dto.getSuccess("查询成功",list,total);
    }
    public Dto getTeacher(int start,int size){
        Pageable pageable = PageRequest.of(start-1,size);
        Page<Teacher> page = teacherDao.findAll(pageable);
        List<Teacher> list = page.getContent();
        int total = teacherDao.countAllBy();
        if(list.size() == 0){
            return Dto.getFailed("没有查询结果");
        }
        for(Teacher tea : list){
            tea.setPassword(StringUtils.EMPTY);
        }

        return Dto.getSuccess("查询成功",list,total);
    }
    //新增或修改教师
    public Dto addOrModifyTeacher(Teacher teacher,Manager manager){
        if(teacher!=null){
            teacher.setLastUpdatedBy(manager.getId());
            if(teacher.getId()!=null){
                Teacher tea = teacherDao.findAllById(teacher.getId());
                if(!Objects.equals(tea.getUsername(),teacher.getUsername())){
                    Teacher t = teacherDao.findAllByUsername(teacher.getUsername());
                    if(t != null){
                        return Dto.getFailed("用户名已经存在");
                    }
                }
                tea.setEmail(teacher.getEmail());
                tea.setTel(teacher.getTel());
                tea = teacherDao.save(tea);
                if(tea != null){
                    return Dto.getSuccess("修改成功");
                }else{
                    return Dto.getFailed("修改失败");
                }
            }else{
                teacher.setCreatedBy(manager.getId());
                Teacher tea = teacherDao.findAllByUsername(teacher.getUsername());
                if(tea != null){
                    return Dto.getFailed("用户名已经存在");
                }
                teacher.setPassword(Md5Util.md5EncodeUtf8(teacher.getPassword()));
                teacher.setRole("1");
                teacher = teacherDao.save(teacher);
                if(teacher != null){
                    return Dto.getSuccess("新增成功");
                }
                return  Dto.getFailed("新增失败");
            }
        }
        return Dto.getFailed("参数不正确");
    }
    //获取单条教师信息
    public Dto getTeacherInfo(Integer tid){
        if(tid == null){
            return Dto.getFailed("参数错误");
        }
        Teacher teacher = teacherDao.findAllById(tid);
        if(teacher!=null){
            teacher.setPassword(StringUtils.EMPTY);
            return Dto.getSuccess(teacher);
        }
        return Dto.getFailed("不存在这名教师");
    }
    //删除教师
    public Dto delTeacher(Integer tid){
        if(tid == null){
            return Dto.getFailed("参数错误");
        }
        int result  = teacherDao.deleteAllById(tid);
        System.out.println(result);
        if(result > 0){
            return Dto.getSuccess("删除成功");
        }
        return Dto.getFailed("删除失败");
    }
    //修改或新增学生
    public Dto addOrModifyStudent(Student student, Manager manager){
        if(student!=null){
            if(student.getId()!=null){
                Student stu = studentDao.findAllById(student.getId());
                if(!Objects.equals(stu.getName(),student.getName()));
                Student s = studentDao.findAllByUsername(student.getUsername());
                if(s!=null){
                    Dto.getFailed("用户名已经存在");
                }
                Student result = studentDao.findAllById(student.getId());
                result.setEmail(student.getEmail());
                result.setTel(student.getTel());
                result = studentDao.save(result);
                if(result!=null){
                    return Dto.getSuccess("修改成功");
                }
                return Dto.getFailed("修改失败");
            }else{
                Student stu = studentDao.findAllByUsername(student.getUsername());
                if(stu!=null){
                    return Dto.getFailed("用户名已经存在");
                }
                student.setCreatedBy(manager.getId());
                student.setPassword(Md5Util.md5EncodeUtf8(student.getPassword()));
                student.setRole(Constant.Role.ROLE_STUDENT);
                student = studentDao.save(student);
                if(student!=null){
                    return Dto.getSuccess("新增成功");
                }
                return Dto.getFailed("新增失败");
            }
        }
        return Dto.getFailed("参数不正确");
    }
    //获取单条学生
    public Dto getStudentInfo(Integer sid){
        if(sid == null){
            return Dto.getFailed("参数不正确");
        }
        Student student = studentDao.findAllById(sid);
        if(student!=null){
            student.setPassword(StringUtils.EMPTY);
            StudentVo studentVo = this.setStudentVO(student);
            return Dto.getSuccess(studentVo);
        }
        return Dto.getFailed("学生不存在");
    }
    //查询学生
    public Dto queryStudent(String query,int start,int size){
        Pageable pageable = PageRequest.of(start-1,size);
        Page<Student> page = studentDao.findAllByNameLike(pageable,"%"+query+"%");
        List<Student> list = page.getContent();
        List<StudentVo> studentVos = new ArrayList<>();
        for(Student stu:list){
            StudentVo studentVo = new StudentVo();
            studentVo = setStudentVO(stu);
            studentVos.add(studentVo);
        }
        int total = studentDao.countAllByNameLike("%"+query+"%");
        if(list.size() == 0){
            return Dto.getFailed("没有查询结果");
        }
        for(Student stu : list){
            stu.setPassword(StringUtils.EMPTY);
        }
        return Dto.getSuccess("查询成功",studentVos,total);
    }
    public Dto getStudent(int start,int size){
        Pageable pageable = PageRequest.of(start-1,size);
        Page<Student> page = studentDao.findAll(pageable);
        List<Student> list = page.getContent();
        List<StudentVo> studentVos = new ArrayList<>();
        for(Student stu:list){
            StudentVo studentVo = new StudentVo();
            studentVo = setStudentVO(stu);
            studentVos.add(studentVo);
        }
        int total = studentDao.countAllBy();
        if(list.size() == 0){
            return Dto.getFailed("没有查询结果");
        }
        for(Student stu : list){
            stu.setPassword(StringUtils.EMPTY);
        }

        return Dto.getSuccess("查询成功",studentVos,total);
    }

    private StudentVo setStudentVO(Student student) {
        StudentVo studentVO = new StudentVo();
        studentVO.setId(student.getId());
        studentVO.setUsername(student.getUsername());
        studentVO.setName(student.getName());
        studentVO.setStudentId(student.getStuNum());
        Major major = majorDao.findAllById(student.getMajorId());
        if (major != null) {
            studentVO.setMajor(major.getName());
            studentVO.setGrade(major.getGrade());
        }
        studentVO.setEmail(student.getEmail());
        studentVO.setTel(student.getTel());
        studentVO.setSex(student.getSex());
        studentVO.setMajorId(String.valueOf(student.getMajorId()));
        studentVO.setCreatedTime(student.getCreateTime());
        studentVO.setLastUpdatedTime(student.getUpdateTime());
        return studentVO;
    }
    //删除学生
    public Dto delStudent(Integer sid){
        if(sid == null){
            return Dto.getFailed("参数错误");
        }
        int result = studentDao.deleteAllById(sid);
        if(result > 0){
            return Dto.getSuccess("删除成功");
        }
        return Dto.getFailed("删除失败");
    }
    //查询教师关联专业
    public Dto getTeacherAndMajor(Integer tid){
        if(tid == null){
            return Dto.getFailed("参数错误");
        }
        TeacherAndMajorVo teacherAndMajorVo = new TeacherAndMajorVo();
        Teacher teacher = teacherDao.findAllById(tid);
        if(teacher!=null){
            teacherAndMajorVo.setName(teacher.getName());
        }else{
            return Dto.getFailed("未找到该教师");
        }
        List<TeacherAndMajor> teacherAndMajorList = teacherAndMajorDao.findAllByTeacherId(tid);
        List<Integer> majorIdList = new ArrayList<>();
        for(TeacherAndMajor teacherAndMajor : teacherAndMajorList){
            majorIdList.add(teacherAndMajor.getMajorId());
        }
        if(majorIdList != null && majorIdList.size()>0){
            List<Major> majorList = new ArrayList<>();
            for(Integer e:majorIdList){
                Major major = majorDao.findAllById(e);
                if(major!=null){
                    majorList.add(major);
                }
            }
            teacherAndMajorVo.setMajorList(majorList);
        }
        return Dto.getSuccess(teacherAndMajorVo);
    }

    //新增教师关联专业
    public Dto addTeacherAndMajor(Integer tid,Integer mid,Manager manager){
        if(tid == null || mid == null){
            return Dto.getFailed("请选择年纪和专业");
        }
        TeacherAndMajor teacherAndMajor = teacherAndMajorDao.findAllByTeacherIdAndMajorId(tid,mid);
        if(teacherAndMajor != null){
            return Dto.getFailed("操作失败:该教师和该专业已经关联");
        }
        TeacherAndMajor TAM = new TeacherAndMajor();
        TAM.setTeacherId(tid);
        TAM.setMajorId(mid);
        TAM.setCreatedBy(manager.getId());
        TAM.setLastUpdatedBy(manager.getId());
        TAM = teacherAndMajorDao.save(TAM);
        if(TAM!=null){
            return Dto.getSuccess("关联成功");
        }
        return Dto.getFailed("关联失败");
    }
    //删除教师关联专业
    @Transactional
    public Dto delTeacherAndMajor(Integer tid,Integer mid){
        if(tid == null || mid == null){
            return Dto.getFailed("请选择年纪和专业");
        }
        int result = teacherAndMajorDao.deleteAllByTeacherIdAndMajorId(tid,mid);
        if(result>0){
            return Dto.getSuccess("删除关联成功");
        }
        return Dto.getFailed("删除关联失败");
    }
    //查看单条专业信息
    public Dto getMajorInfo(Integer mid){
        if(mid == null){
            return Dto.getFailed("请选择专业");
        }
        Major major = majorDao.findAllById(mid);
        if(major!=null){
            return Dto.getSuccess(major);
        }
        return Dto.getFailed("专业不存在");
    }
    //新增或修改年级专业信息
    public Dto addOrModifyMajor(Major major,Manager manager){
        if(major!=null){
            Major ma = majorDao.findAllByNameAndGrade(major.getName(),major.getGrade());
            if(ma!=null){
                return Dto.getFailed("操作失败:该专业已经存在");
            }
            major.setLastUpdatedBy(manager.getId());
            if(major.getId() != null){
                major = majorDao.save(major);
                if(major != null){
                    return Dto.getSuccess("修改专业成功");
                }
                return Dto.getFailed("修改专业失败");
            }else{
                major = majorDao.save(major);
                if(major != null){
                    return Dto.getSuccess("新增专业成功");
                }
                return Dto.getFailed("新增专业失败");
            }
        }
        return Dto.getFailed("参数不正确");
    }
    //删除年纪专业信息
    @Transactional
    public Dto delMajor(Integer mid){
        if(mid == null){
            return Dto.getFailed("参数错误");
        }
        int result = majorDao.deleteAllById(mid);
        if(result > 0){
            return Dto.getSuccess("删除成功");
        }
        return Dto.getFailed("删除失败");

    }
    //获取专业
    public Dto queryMajor(String query,int start,int size){
        Pageable pageable = PageRequest.of(start-1,size);
        Page<Major> page = majorDao.findAllByNameLike(pageable,"%"+query+"%");
        List<Major> list = page.getContent();
        int total = majorDao.countAllByNameLike("%"+query+"%");
        return Dto.getSuccess("查询成功",list,total);
    }
    //重置教师密码
    public Dto resetTeacherPwd(Integer tid){
        if(tid == null){
            return Dto.getFailed("参数错误");
        }
        String password = Md5Util.md5EncodeUtf8("123456");
        Teacher teacher = teacherDao.findAllById(tid);
        if(teacher == null){
            return Dto.getFailed("没有该名教师");
        }
        teacher.setPassword(password);
        teacher = teacherDao.save(teacher);
        if(teacher!=null){
            return Dto.getSuccess("重置密码成功");
        }
        return Dto.getFailed("重置密码失败");
    }
    //重置教师密码
    public Dto resetStudentPwd(Integer sid){
        if(sid == null){
            return Dto.getFailed("参数错误");
        }
        String password = Md5Util.md5EncodeUtf8("123456");
        Student student = studentDao.findAllById(sid);
        if(student == null){
            return Dto.getFailed("没有该名学生");
        }
        student.setPassword(password);
        student = studentDao.save(student);
        if(student!=null){
            return Dto.getSuccess("重置密码成功");
        }
        return Dto.getFailed("重置密码失败");
    }

    //获取年级
    public Dto getGrade(){
        List<Major> gradeList = majorDao.findAll();
        List<String> list = new ArrayList<>();
        for(Major major:gradeList){
            list.add(major.getGrade());
        }
        return Dto.getSuccess(list);
    }
    //获取指定年级的专业
    public Dto getMajorByGrade(String grade){
        List<Major> list = majorDao.findAllByGrade(grade);
        return Dto.getSuccess(list);
    }
    public Dto getMajor(){
        List<Major> list = majorDao.findAll();
        return Dto.getSuccess(list);
    }
    //获取教师关联的专业信息
    public Dto getTeacherAndMajor(Integer tid,int start,int size){
        Pageable pageable = PageRequest.of(start-1,size);
        Page<TeacherAndMajor> list = teacherAndMajorDao.findAllByTeacherId(pageable,tid);
        List<TeacherAndMajor> teacherAndMajorList = list.getContent();
        List<Major> majorList = majorDao.findAll();
        List<Major> result = new ArrayList<>();
        int total = teacherDao.countAllBy();
        for(TeacherAndMajor teacherAndMajor:teacherAndMajorList){
            for(Major major:majorList){
                if(major.getId().equals(teacherAndMajor.getMajorId())){
                    result.add(major);
                }
            }
        }
        return Dto.getSuccess("获取成功",result,total);
    }

}
