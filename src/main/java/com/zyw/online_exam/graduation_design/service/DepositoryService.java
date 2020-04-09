package com.zyw.online_exam.graduation_design.service;

import com.zyw.online_exam.graduation_design.dao.DepositoryDao;
import com.zyw.online_exam.graduation_design.dao.SubjectDao;
import com.zyw.online_exam.graduation_design.exception.MyException;
import com.zyw.online_exam.graduation_design.pojo.Depository;
import com.zyw.online_exam.graduation_design.pojo.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author cengyunwen
 * @version 1.0
 * @date 2020/4/9 11:19
 */
@Service
public class DepositoryService {
    @Autowired
    private DepositoryDao depositoryDao;
    @Autowired
    private SubjectDao subjectDao;

    public Depository getByName(String name){
        return depositoryDao.findAllByName(name);
    }

    public List<Depository> getAll(int start, int size)throws MyException{
        Pageable pageable = PageRequest.of(start,size);
        Page<Depository> page = depositoryDao.findAll(pageable);
        List<Depository> lists = page.getContent();
        if(lists.size() == 0){
            throw new MyException("当前页面为空");

        }
        return lists;
    }

    public void add(String name,String sname)throws MyException{
        Subject subject = subjectDao.findAllByName(sname);
        if(subject == null){
            throw new MyException("没有这个科目");
        }else{
            Depository depository = new Depository();
            depository.setSubjectId(subject.getId());
            depository.setName(name);
            depositoryDao.save(depository);
        }

    }

    public void modify(String beforeName,String name){
        Depository depository = depositoryDao.findAllByName(beforeName);
        depository.setName(name);
        depositoryDao.save(depository);
    }

    @Transactional
    public void delete(String name){
        depositoryDao.deleteByName(name);
    }

    public List<Depository> getAllBySubject(String sname,int start,int size){
        Subject subject = subjectDao.findAllByName(sname);
        Integer sid = subject.getId();
        Pageable pageable = PageRequest.of(start,size);
        Page<Depository> page = depositoryDao.findAllBySubjectId(pageable,sid);
        List<Depository> list = page.getContent();
        return list;
    }
}
