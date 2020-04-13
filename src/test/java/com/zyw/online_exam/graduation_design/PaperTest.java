package com.zyw.online_exam.graduation_design;

import com.zyw.online_exam.graduation_design.dao.PaperQuestionDao;
import com.zyw.online_exam.graduation_design.dao.QuestionDao;
import com.zyw.online_exam.graduation_design.exception.MyException;
import com.zyw.online_exam.graduation_design.pojo.Paper;
import com.zyw.online_exam.graduation_design.pojo.PaperQuestion;
import com.zyw.online_exam.graduation_design.pojo.Question;
import com.zyw.online_exam.graduation_design.pojo.Teacher;
import com.zyw.online_exam.graduation_design.service.PaperService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author cengyunwen
 * @version 1.0
 * @date 2020/4/10 16:19
 */
public class PaperTest extends GraduationDesignApplicationTests{
    @Autowired
    private PaperService paperService;
    @Autowired
    private PaperQuestionDao paperQuestionDao;
    @Autowired
    private QuestionDao questionDao;
    @Test
    public void test1() throws MyException {
        /*String name = "新增试卷";
        String flag = "Y";
        Teacher teacher = new Teacher();
        teacher.setId(1);
        paperService.addPaper(name,flag,teacher);*/
        Dto dto = paperService.queryPaper(0,2);
        System.out.println(dto);

    }
    @Test
    public void test2(){
        PaperQuestion paperQuestion = new PaperQuestion();
        paperQuestion.setQuestionId(7);
        paperQuestion.setPaperId(3);
        paperQuestion.setScore("3");
        Teacher teacher = new Teacher();
        teacher.setId(1);
        Dto dto = paperService.compositionPaper(paperQuestion,teacher);
        System.out.println(dto);
    }
    @Test
    @Transactional
    public void test4(){
        Teacher teacher = new Teacher();
        teacher.setId(1);
        //Dto dto = paperService.publishPaper(1,1,teacher);
        Dto dto = paperService.selectPaperQuestion(3);
        System.out.println(dto);
    }
    @Test
    public void test5()
    {   Teacher teacher = new Teacher();
        teacher.setId(1);
        Dto dto = paperService.autoBuildPaper("自动组装的试卷1","Y",6,"2",
                "java",teacher);
        System.out.println(dto);
    }
}
