package com.zyw.online_exam.graduation_design.controller;

import com.zyw.online_exam.graduation_design.Dto;
import com.zyw.online_exam.graduation_design.exception.MyException;
import com.zyw.online_exam.graduation_design.pojo.Subject;
import com.zyw.online_exam.graduation_design.service.SubjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
public class SubjectController {
    private static final Logger logger = LoggerFactory.getLogger(SubjectController.class);
    @Autowired
    private SubjectService subjectService;
    @RequestMapping("/subject")
    public Dto list(@RequestParam(value = "start",defaultValue = "0")int start,
                    @RequestParam(value = "size",defaultValue = "5")int size){
        start = start>0?start:0;
        List<Subject> list;
        try {
             list = subjectService.getAll(start,size);
            logger.info("当前数据",list);
            return Dto.getSuccess(list);

        }catch (Exception e){
            e.printStackTrace();
            logger.error("出现异常");
            return Dto.getFailed();
        }
    }
}
