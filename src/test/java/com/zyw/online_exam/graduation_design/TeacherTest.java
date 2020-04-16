package com.zyw.online_exam.graduation_design;

import com.zyw.online_exam.graduation_design.dao.ScoreDao;
import com.zyw.online_exam.graduation_design.pojo.Major;
import com.zyw.online_exam.graduation_design.pojo.Manager;
import com.zyw.online_exam.graduation_design.pojo.ScoreDetail;
import com.zyw.online_exam.graduation_design.pojo.Teacher;
import com.zyw.online_exam.graduation_design.service.ManagerService;
import com.zyw.online_exam.graduation_design.service.TeacherService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author cengyunwen
 * @version 1.0
 * @date 2020/4/13 11:20
 */
public class TeacherTest extends GraduationDesignApplicationTests{
    @Autowired
    private ManagerService managerService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private ScoreDao scoreDao;
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
        teacher.setId(1);
        teacher.setName("zcc");
        teacher.setUsername("zyw");
        teacher.setPassword("123");
        teacher.setSex("男");
        teacher.setTel("15603303765");
        teacher.setEmail("207656645@qq.com");
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
        Teacher teacher = new Teacher();
        teacher.setId(1);
        Dto dto = teacherService.getTeacherMajor(teacher);
        System.out.println(dto);
    }
    @Test
    public void test7(){
        List<ScoreDetail> list = scoreDao.findAllScore();
        for(ScoreDetail scoreDetail:list){
            System.out.println(scoreDetail);
        }
    }

}
