package com.zyw.online_exam.graduation_design;

import com.zyw.online_exam.graduation_design.exception.MyException;
import com.zyw.online_exam.graduation_design.pojo.Question;
import com.zyw.online_exam.graduation_design.service.DepositoryService;
import com.zyw.online_exam.graduation_design.service.QuestionService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author cengyunwen
 * @version 1.0
 * @date 2020/4/9 17:01
 */
public class QuestionTest extends GraduationDesignApplicationTests{
    @Autowired
    private QuestionService questionService;
    @Test
    public void test1() throws MyException {
        List<Question> list = questionService.getAll(1,2);
        for(Question question :list){
            System.out.println(question);
        }
    }
    @Test
    public void test2() throws MyException {
        Question question = new Question();
        question.setTitle("题干1");
        question.setAnswer("答案1");
        question.setType("1");
        String sname = "数学";
        String dname = "小学数学";
        questionService.add(question,sname,dname);
    }
    @Test
    public void test4() throws MyException {
        List<Question> list = questionService.getByDepository("初中数学",1,2);
        for(Question question:list){
            System.out.println(question);
        }

    }
    @Test
    public void test5(){
        Question question = new Question();
        question.setId(1);
        //question.setType("1");
        question.setTitle("题目1更新");
        question.setAnswer("答案1更新");
        questionService.update(question);

    }
    @Test
    public void test6(){
        questionService.delete(7);

    }
}
