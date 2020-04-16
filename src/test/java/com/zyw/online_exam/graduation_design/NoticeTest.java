package com.zyw.online_exam.graduation_design;

import com.zyw.online_exam.graduation_design.dao.NoticeDao;
import com.zyw.online_exam.graduation_design.pojo.Manager;
import com.zyw.online_exam.graduation_design.pojo.Notice;
import com.zyw.online_exam.graduation_design.service.NoticeService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author cengyunwen
 * @version 1.0
 * @date 2020/4/14 15:49
 */
public class NoticeTest extends GraduationDesignApplicationTests {
    @Autowired
    NoticeService noticeService;
    @Autowired
    NoticeDao noticeDao;
    @Test
    public void test1(){
        Notice notice = new Notice();
        notice.setId(7);
        notice.setNoticeContent("通知修改");
        notice.setFlag("Y");
        Manager manager = new Manager();
        manager.setId(1);
        Dto dto = noticeService.addOrModifyNotice(notice,manager);
        System.out.println(dto);


    }
    @Test
    public void test2(){
        Dto dto = noticeService.getNotice(0);
        System.out.println(dto);
    }
}
