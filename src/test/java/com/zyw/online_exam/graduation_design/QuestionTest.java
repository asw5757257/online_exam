package com.zyw.online_exam.graduation_design;

import com.zyw.online_exam.graduation_design.dao.MajorDao;
import com.zyw.online_exam.graduation_design.exception.MyException;
import com.zyw.online_exam.graduation_design.pojo.Major;
import com.zyw.online_exam.graduation_design.pojo.Question;
import com.zyw.online_exam.graduation_design.pojo.Student;
import com.zyw.online_exam.graduation_design.service.QuestionService;
import com.zyw.online_exam.graduation_design.utils.Md5Util;
import com.zyw.online_exam.graduation_design.vo.QuestionVo;
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
    @Autowired
    private MajorDao majorDao;
    @Test
    public void test1() throws MyException {

    }
    @Test
    public void test2() throws MyException {

        System.out.println(Md5Util.md5EncodeUtf8("123456"));
    }
    @Test
    public void test4()  {
        Major major = new Major();
        major.setName("测试");
        major = majorDao.save(major);
        System.out.println(major);




    }
    @Test
    public void test5(){
        List<QuestionVo> list = questionService.getMyQuestion(1,0,9);
        for(QuestionVo questionVo:list){
            System.out.println(questionVo);
        }

    }
    @Test
    public void test6(){


    }
}
