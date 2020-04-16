package com.zyw.online_exam.graduation_design;

import com.zyw.online_exam.graduation_design.exception.MyException;
import com.zyw.online_exam.graduation_design.pojo.Major;
import com.zyw.online_exam.graduation_design.pojo.Question;
import com.zyw.online_exam.graduation_design.pojo.StudentScore;
import com.zyw.online_exam.graduation_design.pojo.Teacher;
import com.zyw.online_exam.graduation_design.service.MajorService;
import com.zyw.online_exam.graduation_design.service.QuestionService;
import com.zyw.online_exam.graduation_design.service.TeacherService;
import com.zyw.online_exam.graduation_design.vo.QuestionVo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cengyunwen
 * @version 1.0
 * @date 2020/4/8 17:11
 */
public class MajorTest extends GraduationDesignApplicationTests {
    @Autowired
    private MajorService majorService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private QuestionService questionService;
    @Test
    public void test1(){


    }
    @Test
    public void test2() throws MyException {
        Teacher teacher = new Teacher();
        teacher.setId(2);
        Question question = new Question();
        question.setTitle("增加的题目");
        question.setContent("选项");
        question.setAnswer("答案");
        questionService.add(question,teacher);
    }

    @Test
    public void test3(){
        Integer qid = 1;
        String flag = "Y";
        Teacher teacher = new Teacher();
        teacher.setId(1);
        Dto dto = questionService.updateFlag(qid,flag,teacher);
        System.out.println(dto);

    }
    @Test
    public void test4(){
        Dto questionList = questionService.getSubject();
        System.out.println(questionList);


    }

}
