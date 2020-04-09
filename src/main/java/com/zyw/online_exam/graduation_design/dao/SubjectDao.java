package com.zyw.online_exam.graduation_design.dao;

import com.zyw.online_exam.graduation_design.pojo.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

/**
 * @author cengyunwen
 * @version 1.0
 * @date 2020/4/8 16:57
 */
public interface SubjectDao extends JpaRepository<Subject,Integer> {
    Subject findAllByName(String name);

    Page<Subject> findAll(Pageable pageable);
    void deleteByName(String name);
    Subject findAllById(Integer id);

}
