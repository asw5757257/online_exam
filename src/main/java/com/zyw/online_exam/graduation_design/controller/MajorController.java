package com.zyw.online_exam.graduation_design.controller;

import com.zyw.online_exam.graduation_design.Dto;
import com.zyw.online_exam.graduation_design.exception.MyException;
import com.zyw.online_exam.graduation_design.pojo.Major;
import com.zyw.online_exam.graduation_design.service.MajorService;
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
 * @date 2020/4/8 17:40
 */
@RestController
public class MajorController {
    private static final Logger logger = LoggerFactory.getLogger(MajorController.class);
    @Autowired
    private MajorService majorService;

    @RequestMapping("/allSubject")
    public Dto list(@RequestParam(value = "start",defaultValue = "0")int start,
                    @RequestParam(value = "size",defaultValue = "5")int size){
        start = start>0?start:0;
        List<Major> list;
        try {
             list = majorService.getAll(start,size);
            logger.info("当前数据{}",list);
            return Dto.getSuccess(list);

        }catch (MyException e){
            e.printStackTrace();
            logger.error("当前页面为空");
            return Dto.getFailed();
        }
        catch (Exception e){
            e.printStackTrace();
            logger.error("出现异常");
            return Dto.getFailed();
        }
    }
    @RequestMapping("/addSubject")
    public Dto add(@RequestParam(value = "name") String name){
        try {
            Major major = majorService.getByName(name);
            if(major != null){
                logger.warn("重复添加已有科目");
                return Dto.getFailed("重复添加已有科目");
            }else{
                majorService.add(name);
                logger.info("添加科目:{}",name);
                return Dto.getSuccess();
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error("出现异常");
            return Dto.getFailed();
        }
    }

    @RequestMapping("/updateSubject")
    public Dto update(@RequestParam(value = "beforeName") String beforeName,
                      @RequestParam(value = "afterName")String afterName){
        Major major = majorService.getByName(beforeName);
        try{
            if(major == null){
                logger.error("数据库里无{}科目",beforeName);
                return Dto.getFailed("数据库里无该科目");
            }else{
                majorService.modify(beforeName,afterName);
                logger.info("修改过后名字：{}",afterName);
                return Dto.getSuccess();
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error("出现异常");
            return Dto.getFailed();
        }
    }

    @RequestMapping("deleteSubject")
    public Dto delete(@RequestParam(value = "name")String name){
        Major major = majorService.getByName(name);
        try {
            if(major == null){
                logger.error("数据库里无{}科目",name);
                return Dto.getFailed("数据库里无该科目");
            }else{
                majorService.delete(name);
                logger.info("删除{}科目成功",name);
                return Dto.getSuccess("删除成功");
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error("出现异常");
            return Dto.getFailed();
        }
    }

}
