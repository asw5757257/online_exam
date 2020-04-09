package com.zyw.online_exam.graduation_design.service;

import com.zyw.online_exam.graduation_design.dao.DepositoryDao;
import com.zyw.online_exam.graduation_design.dao.QuestionDao;
import com.zyw.online_exam.graduation_design.dao.SubjectDao;
import com.zyw.online_exam.graduation_design.exception.MyException;
import com.zyw.online_exam.graduation_design.pojo.Depository;
import com.zyw.online_exam.graduation_design.pojo.Question;
import com.zyw.online_exam.graduation_design.pojo.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
    private SubjectDao subjectDao;
    @Autowired
    private DepositoryDao depositoryDao;

    public List<Question> getAll(int start, int size)throws MyException {
        Pageable pageable = PageRequest.of(start,size);
        Page<Question> page = questionDao.findAll(pageable);
        List<Question> lists = page.getContent();
        if(lists.size() == 0){
            throw new MyException("当前页面为空");

        }
        return lists;
    }

    public Question getById(int id)throws MyException{
        Question question = questionDao.findAllById(id);
        if(question == null){
            throw new MyException("没有这道题");
        }else{
            return question;
        }
    }

    public List<Question> getBySubject(String sname,int start,int size){
        Subject subject = subjectDao.findAllByName(sname);
        Integer sid = subject.getId();
        Pageable pageable = PageRequest.of(start,size);
        Page<Question> page = questionDao.findAllBySubjectId(pageable,sid);
        List<Question> list = page.getContent();
        return list;

    }

    public List<Question> getByDepository(String dname,int start,int size){
        Depository depository = depositoryDao.findAllByName(dname);
        Integer did = depository.getId();
        Pageable pageable = PageRequest.of(start,size);
        Page<Question> page = questionDao.findAllByDepositoryId(pageable,did);
        List<Question> list = page.getContent();
        return list;
    }

    public String getSubject(int id){
        Question question = questionDao.findAllById(id);
        Integer sid = question.getSubjectId();
        String sname = subjectDao.findAllById(sid).getName();
        return sname;
    }

    public String getDepository(int id){
        Question question = questionDao.findAllById(id);
        Integer did = question.getDepositoryId();
        String dname = depositoryDao.findAllById(did).getName();
        return dname;
    }

    public void add(Question question,String sname,String dname){
        Integer sid;
        Integer did;
        Subject subject = subjectDao.findAllByName(sname);
        Depository depository = depositoryDao.findAllByName(dname);
        sid = subject.getId();
        did = depository.getId();
        question.setSubjectId(sid);
        question.setDepositoryId(did);
        questionDao.save(question);
    }
    public void update(Question question){
        Question que = questionDao.findAllById(question.getId());
        que.setTitle(question.getTitle());
        que.setAnswer(question.getAnswer());//题目只能修改题干和答案
        que.setContent(question.getContent());
        questionDao.save(que);
    }

    public void delete(Integer id){
        questionDao.deleteById(id);
    }

}
