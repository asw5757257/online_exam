package com.zyw.online_exam.graduation_design;

import com.zyw.online_exam.graduation_design.dao.SubjectDao;
import com.zyw.online_exam.graduation_design.pojo.Subject;
import com.zyw.online_exam.graduation_design.service.DepositoryService;
import com.zyw.online_exam.graduation_design.service.SubjectService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author cengyunwen
 * @version 1.0
 * @date 2020/4/8 17:11
 */
public class SubjectTest extends GraduationDesignApplicationTests {
    @Autowired
    private SubjectService subjectService;

    @Test
    public void test1(){

    }
    @Test
    public void test2(){
        subjectService.modify("数学","数学1");

    }

    @Test
    public void test3(){
        Subject subject = subjectService.getByName("物理1");
        System.out.println(subject);


    }
}
