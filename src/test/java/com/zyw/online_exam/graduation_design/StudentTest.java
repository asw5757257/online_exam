package com.zyw.online_exam.graduation_design;

import com.zyw.online_exam.graduation_design.dao.StudentDao;
import com.zyw.online_exam.graduation_design.pojo.Student;
import com.zyw.online_exam.graduation_design.service.StudentService;
import com.zyw.online_exam.graduation_design.utils.Md5Util;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author cengyunwen
 * @version 1.0
 * @date 2020/4/12 10:08
 */
public class StudentTest extends GraduationDesignApplicationTests {
    @Autowired
    private StudentService studentService;
    @Autowired
    private StudentDao studentDao;
    @Test
    public void test1(){
        String s = "abc";
        String oldpwd = "123";
        String newpwd = "abc";
        Student student = new Student();
        student.setId(1);
        Dto dto = studentService.resetStudentPassword(newpwd,oldpwd,student);
        System.out.println(dto);

    }
    @Test
    public void test2(){
        Student student = new Student();
        student.setId(1);
        student.setMajorId(1);
        Dto dto = studentService.getPaper(3,student);
        System.out.println(dto);
    }
    @Test
    public void test3(){
        Student student = new Student();
        student.setName("系统测试");
        student.setUsername("test");
        student.setPassword("test");
        student.setMajorId(1);
        student.setStuNum("123456");
        student.setSex("男");
        studentDao.save(student);

    }
    @Test
    public void test4(){
        Student student = new Student();
        student.setId(3);
        Dto dto = studentService.queryThisScore(student,3);
        System.out.println(dto);
    }
    @Test
    public void test5(){
        Student student = studentDao.findAllById(6);
        student.setName("更改过后的名字");
        studentDao.save(student);
        Student stu = studentDao.findAllById(6);
        System.out.println(stu);
    }
}
