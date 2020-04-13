package com.zyw.online_exam.graduation_design;

import com.zyw.online_exam.graduation_design.pojo.Student;
import com.zyw.online_exam.graduation_design.service.StudentService;
import com.zyw.online_exam.graduation_design.utils.Md5Util;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author cengyunwen
 * @version 1.0
 * @date 2020/4/12 10:08
 */
public class StudentTest extends GraduationDesignApplicationTests {
    @Autowired
    private StudentService studentService;
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

    }
}
