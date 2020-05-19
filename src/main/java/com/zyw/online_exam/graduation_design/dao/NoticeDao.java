package com.zyw.online_exam.graduation_design.dao;

import com.zyw.online_exam.graduation_design.pojo.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author cengyunwen
 * @version 1.0
 * @date 2020/4/14 14:59
 */
public interface NoticeDao extends JpaRepository<Notice,Integer> {
    Notice findAllById(Integer id);
    List<Notice> findAllByFlag(String flag);
    Page<Notice> findAllByNoticeContentLikeAndFlag(Pageable pageable,String content, String flag);
    Page<Notice> findAll(Pageable pageable);
    int countAllByFlag(String flag);

}
