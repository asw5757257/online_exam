package com.zyw.online_exam.graduation_design;

import com.zyw.online_exam.graduation_design.pojo.Major;
import com.zyw.online_exam.graduation_design.pojo.Manager;
import com.zyw.online_exam.graduation_design.pojo.Teacher;
import com.zyw.online_exam.graduation_design.service.ManagerService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;

/**
 * @author cengyunwen
 * @version 1.0
 * @date 2020/4/13 11:20
 */
public class TeacherTest extends GraduationDesignApplicationTests{
    @Autowired
    private ManagerService managerService;
    @Test
    public void test1(){
        Teacher teacher = new Teacher();
        teacher.setName("zs");
        Dto dto = managerService.queryTeacher(teacher,0,5);
        System.out.println(dto);
    }
    @Test
    public void test2(){
        Manager manager = new Manager();
        manager.setId(1);
        Teacher teacher = new Teacher();
        //teacher.setId(1);
        teacher.setName("cywzzz");
        teacher.setUsername("cywzzz");
        teacher.setPassword("123456");
        teacher.setSex("女");
        teacher.setTel("1354444");
        teacher.setEmail("33333");
        //teacher.set
        Dto dto = managerService.addOrModifyTeacher(teacher,manager);
        System.out.println(dto);
    }
    @Test
    @Transactional
    public void test3(){
        Integer id = 6;
        Dto dto = managerService.delTeacher(id);
        System.out.println(dto);
    }
    @Test
    @Transactional
    public void test4(){
        Integer id = 6;
        Manager manager = new Manager();
        manager.setId(1);
        Dto dto = managerService.delTeacherAndMajor(1,2);
        System.out.println(dto);
    }

    @Test
    @Transactional
    public void test5(){
        Major major = new Major();
        Manager manager = new Manager();
        manager.setId(1);
        //major.setId(7);
        major.setName("测试2");
        //Dto dto = managerService.addOrModifyMajor(major,manager);
        Dto dto = managerService.delMajor(9);
        System.out.println(dto);
    }
    @Test
    public void test6(){
        Dto dto = managerService.getTeacherAndMajor(1,1,3);
        System.out.println(dto);
    }

}
