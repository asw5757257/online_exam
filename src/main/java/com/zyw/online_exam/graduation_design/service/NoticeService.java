package com.zyw.online_exam.graduation_design.service;

import com.zyw.online_exam.graduation_design.Dto;
import com.zyw.online_exam.graduation_design.dao.NoticeDao;
import com.zyw.online_exam.graduation_design.dao.TeacherDao;
import com.zyw.online_exam.graduation_design.pojo.Manager;
import com.zyw.online_exam.graduation_design.pojo.Notice;
import com.zyw.online_exam.graduation_design.pojo.Teacher;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author cengyunwen
 * @version 1.0
 * @date 2020/4/14 15:13
 */
@Service
public class NoticeService {
    @Autowired
    private NoticeDao noticeDao;
    @Autowired
    private TeacherDao teacherDao;
    public List getNotice(){
        return noticeDao.findAllByFlag("Y");
    }
    public Dto queryNotice(Integer start,Integer size){
        Pageable pageable = PageRequest.of(start-1, size);
        Page<Notice> page = noticeDao.findAll(pageable);
        int total = noticeDao.countAllByFlag("Y");
        List<Notice> list = page.getContent();
        if(list.size() == 0){
            return Dto.getSuccess("当前没有信息");
        }
        return Dto.getSuccess("查询成功",list,total);
    }
    public Dto addOrModifyNotice(Notice notice, Teacher teacher){
        if(notice!=null){
            if (StringUtils.isNotBlank(notice.getNoticeContent())){
                notice.setCreatedBy(teacher.getId());
                notice.setLastUpdatedBy(teacher.getId());
                if(notice.getId()!=null){
                    notice = noticeDao.save(notice);
                    if(notice != null){
                        return Dto.getSuccess("修改成功");
                    }
                    return Dto.getFailed("修改失败");
                }else{
                    notice.setFlag("Y");
                    notice = noticeDao.save(notice);
                    if(notice != null){
                        return Dto.getSuccess("新增成功");
                    }
                    return Dto.getFailed("新增失败");
                }
            }
        }
        return Dto.getFailed("通知内容不能为空");
    }
    //获取单条内容
    public Dto getNotice(Integer nid){
        if(nid == null){
            return Dto.getFailed("参数不能为空");
        }
        Notice notice = noticeDao.findAllById(nid);
        if(notice!=null){
            return Dto.getSuccess(notice);
        }
        return Dto.getFailed("通知不存在或已删除");
    }
}
