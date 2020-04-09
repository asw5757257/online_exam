package com.zyw.online_exam.graduation_design.service;

import com.zyw.online_exam.graduation_design.dao.SubjectDao;
import com.zyw.online_exam.graduation_design.exception.MyException;
import com.zyw.online_exam.graduation_design.pojo.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author cengyunwen
 * @version 1.0
 * @date 2020/4/8 17:00
 */
@Service
public class SubjectService {
    @Autowired
    private SubjectDao subjectDao;

    public Subject getByName(String name){
        return subjectDao.findAllByName(name);
    }

    /*public List<Subject> getAll(){
        return subjectDao.findAll();
    }*/
    public List<Subject> getAll(int start,int size){
        Pageable pageable = PageRequest.of(start,size);
        Page<Subject> page = subjectDao.findAll(pageable);
        List<Subject> lists = page.getContent();
        if(lists.size() == 0){
            try {
                throw new MyException("当前页面为空");
            } catch (MyException e) {
                e.printStackTrace();
            }
        }
        return lists;
    }

    public void add(String name){
        Subject subject = new Subject();
        subject.setName(name);
        subjectDao.save(subject);
    }
    @Transactional
    public void delete(String name){
        subjectDao.deleteByName(name);
    }

    public void modify(String beforeName,String name){
        Subject subject = subjectDao.findAllByName(beforeName);
        subject.setName(name);
        subjectDao.save(subject);
    }
}
