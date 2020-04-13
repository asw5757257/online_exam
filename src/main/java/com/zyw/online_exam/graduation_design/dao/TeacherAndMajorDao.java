package com.zyw.online_exam.graduation_design.dao;

import com.zyw.online_exam.graduation_design.pojo.Teacher;
import com.zyw.online_exam.graduation_design.pojo.TeacherAndMajor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author cengyunwen
 * @version 1.0
 * @date 2020/4/10 12:03
 */
public interface TeacherAndMajorDao extends JpaRepository<TeacherAndMajor,Integer> {
    TeacherAndMajor findAllByTeacherIdAndMajorId(Integer tid,Integer mid);
    List<TeacherAndMajor> findAllByTeacherId(Integer tid);
    int deleteAllByTeacherIdAndMajorId(Integer tid,Integer mid);
    Page<TeacherAndMajor> findAllByTeacherId(Pageable pageable,Integer id);
}
