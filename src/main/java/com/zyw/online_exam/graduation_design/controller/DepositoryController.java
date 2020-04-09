package com.zyw.online_exam.graduation_design.controller;

import com.zyw.online_exam.graduation_design.Dto;
import com.zyw.online_exam.graduation_design.exception.MyException;
import com.zyw.online_exam.graduation_design.pojo.Depository;
import com.zyw.online_exam.graduation_design.pojo.Subject;
import com.zyw.online_exam.graduation_design.service.DepositoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author cengyunwen
 * @version 1.0
 * @date 2020/4/9 11:38
 */
@RestController
public class DepositoryController {
    private static final Logger logger = LoggerFactory.getLogger(SubjectController.class);
    @Autowired
    private DepositoryService depositoryService;
    @RequestMapping("/getDepository")
    public Dto getAll(@RequestParam(value = "start",defaultValue = "0")int start,
                      @RequestParam(value = "size",defaultValue = "5")int size){
        start = start>0?start:0;
        List<Depository> list;
        try {
            list = depositoryService.getAll(start,size);
            logger.info("当前数据{}",list);
            return Dto.getSuccess(list);

        }catch (Exception e){
            e.printStackTrace();
            logger.error("出现异常");
            return Dto.getFailed();
        }
    }

    @RequestMapping("/addDepository")
    public Dto add(@RequestParam(value = "name")String name,
                   @RequestParam(value = "sname")String sname){

        try{
            depositoryService.add(name,sname);
            logger.info("添加成功");
            return Dto.getSuccess();
        }catch (MyException e){
            e.printStackTrace();
            logger.error("没有这个学科");
            return Dto.getFailed("没有这门学科");
        }catch (Exception e){
            e.printStackTrace();
            logger.error("出现异常");
            return Dto.getFailed();
        }
    }

    @RequestMapping("/updateDepository")
    public Dto update(@RequestParam(value = "beforeName") String beforeName,
                      @RequestParam(value = "afterName")String afterName){
        Depository depository = depositoryService.getByName(beforeName);
        try{
            if(depository == null){
                logger.error("数据库里无{}题库",beforeName);
                return Dto.getFailed("数据库里无该题库");
            }else{
                depositoryService.modify(beforeName,afterName);
                logger.info("修改过后名字：{}",afterName);
                return Dto.getSuccess();
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error("出现异常");
            return Dto.getFailed();
        }
    }

    @RequestMapping("/getBySubject")
    public Dto getBySubject(@RequestParam(value = "sname")String sname,
                            @RequestParam(value = "start",defaultValue = "0")int start,
                            @RequestParam(value = "size",defaultValue = "5")int size){
        start = start>0?start:0;
        List<Depository> list;
        try {
            list = depositoryService.getAllBySubject(sname,start,size);
            logger.info("获取的题库为{}",list);
            return Dto.getSuccess(list);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("出现异常");
            return Dto.getFailed();
        }
    }
}
