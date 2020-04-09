package com.zyw.online_exam.graduation_design;

import com.zyw.online_exam.graduation_design.pojo.Depository;
import com.zyw.online_exam.graduation_design.pojo.Subject;
import com.zyw.online_exam.graduation_design.service.DepositoryService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author cengyunwen
 * @version 1.0
 * @date 2020/4/9 11:31
 */
public class DepositoryTest extends GraduationDesignApplicationTests{
    @Autowired
    private DepositoryService depositoryService;
    @Test
    public void test1(){
        List<Depository> lists = depositoryService.getAll(0,2);
        for(Depository depository:lists){
            System.out.println(depository);
        }
    }
    @Test
    public void test2(){
        Depository depository = depositoryService.getByName("小学数学");
        System.out.println(depository);
    }
    @Test
    public void test3(){
        List<Depository> list = depositoryService.getAllBySubject("数学",1,2);
        for(Depository depository:list){
            System.out.println(depository);
        }
    }
}
