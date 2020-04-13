package com.zyw.online_exam.graduation_design.service;

import com.zyw.online_exam.graduation_design.dao.MajorDao;
import com.zyw.online_exam.graduation_design.exception.MyException;
import com.zyw.online_exam.graduation_design.pojo.Major;
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
 * @date 2020/4/8 17:00
 */
@Service
public class MajorService {
    @Autowired
    private MajorDao majorDao;

    public Major getByName(String name){
        return majorDao.findAllByName(name);
    }

    /*public List<Major> getAll(){
        return majorDao.findAll();
    }*/
    public List<Major> getAll(int start, int size)throws MyException{
        Pageable pageable = PageRequest.of(start,size);
        Page<Major> page = majorDao.findAll(pageable);
        List<Major> lists = page.getContent();
        if(lists.size() == 0){
            throw new MyException("当前页面为空");
        }
        return lists;
    }

    public void add(String name){
        Major major = new Major();
        major.setName(name);
        majorDao.save(major);
    }
    @Transactional
    public void delete(String name){
        majorDao.deleteByName(name);
    }

    public void modify(String beforeName,String name){
        Major major = majorDao.findAllByName(beforeName);
        major.setName(name);
        majorDao.save(major);
    }
}
