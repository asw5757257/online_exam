package com.zyw.online_exam.graduation_design.service;
import com.zyw.online_exam.graduation_design.Dto;
import com.zyw.online_exam.graduation_design.common.Constant;
import com.zyw.online_exam.graduation_design.dao.MajorDao;
import com.zyw.online_exam.graduation_design.dao.QuestionDao;
import com.zyw.online_exam.graduation_design.dao.TeacherDao;
import com.zyw.online_exam.graduation_design.exception.MyException;
import com.zyw.online_exam.graduation_design.pojo.Major;
import com.zyw.online_exam.graduation_design.pojo.Question;
import com.zyw.online_exam.graduation_design.pojo.Teacher;
import com.zyw.online_exam.graduation_design.utils.TimeUtil;
import com.zyw.online_exam.graduation_design.vo.QuestionVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cengyunwen
 * @version 1.0
 * @date 2020/4/9 16:23
 */
@Service
public class QuestionService {
    @Autowired
    private QuestionDao questionDao;
    @Autowired
    private MajorDao majorDao;
    @Autowired
    private TeacherDao teacherDao;
    public List<QuestionVo> getAll(int start, int size)throws MyException {
        Pageable pageable = PageRequest.of(start,size);
        Page<Question> page = questionDao.findAll(pageable);
        List<Question> lists = page.getContent();
        List<QuestionVo> questionVoList = new ArrayList<>();
        for(Question question:lists){
            QuestionVo questionVo;
            questionVo = setQuestionVo(question);
            //System.out.println(questionVo);
            questionVoList.add(questionVo);

        }
        if(lists.size() == 0){
            throw new MyException("当前页面为空");

        }

        return questionVoList;
    }

    public QuestionVo getById(int id)throws MyException{
        Question question = questionDao.findAllById(id);
        if(question == null){
            throw new MyException("没有这道题");
        }else{
            QuestionVo questionVo = setQuestionVo(question);
            return questionVo;
        }
    }

    public Dto add(Question question, Teacher teacher){
        if(question != null){
            question.setCreatedBy(teacher.getId());
            question.setLastUpdatedBy(teacher.getId());
            question.setFlag("Y");
            questionDao.save(question);
            return Dto.getSuccess("新增成功");
        }
        return Dto.getFailed("参数不正确");
    }
    public Dto updateFlag(Integer qid,String flag,Teacher teacher){
        if (qid != null && StringUtils.isNotBlank(flag)){
            Question question = questionDao.findAllById(qid);
            if(question == null){
                return Dto.getFailed("没有这道题");
            }
            if(question.getCreatedBy().equals(teacher.getId())){
                question.setFlag(flag);
                question.setLastUpdatedBy(teacher.getId());
                questionDao.save(question);
                return Dto.getSuccess("修改成功");
            }else{
                return Dto.getFailed("没有权限修改这道题");
            }
        }
        return Dto.getFailed("参数不正确");

    }
    public Dto getSubject(){
        List<String> list = new ArrayList<>();
        List<Question> questionList = questionDao.findAll();
        for(Question question:questionList){
            list.add(question.getSubject());
        }
        return Dto.getSuccess(list);
    }
    private QuestionVo setQuestionVo(Question question){
        QuestionVo questionVo = new QuestionVo();
        questionVo.setId(question.getId());
        if(Constant.TestType.TEST_CHOICE.equals(question.getType())){
            questionVo.setType("选择题");
        }else if(Constant.TestType.TEST_JUDGE.equals(question.getType())){
            questionVo.setType("判断题");
        }else if(Constant.TestType.TEST_FILL.equals(question.getType())){
            questionVo.setType("填空题");
        }else{
            questionVo.setType("其他");
        }
        questionVo.setSubject(question.getSubject());
        questionVo.setTitle(question.getTitle());
        questionVo.setAnswer(question.getAnswer());
        questionVo.setContent(question.getContent());
        questionVo.setFlag(question.getFlag());
        if(Constant.FLAG_Y.equals(question.getFlag())){
            questionVo.setFlag("可以使用");
        }else{
            questionVo.setFlag("无法使用");
        }
        questionVo.setLastUpdatedTime(TimeUtil.dateToStr(question.getUpdateTime()));
        Integer QcreatedBy = question.getCreatedBy();
        String createdBy = teacherDao.findAllById(QcreatedBy).getName();
        questionVo.setCreatedBy(createdBy);
        return questionVo;
    }

}
